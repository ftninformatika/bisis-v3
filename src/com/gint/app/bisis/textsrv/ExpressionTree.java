package com.gint.app.bisis.textsrv;

import java.util.*;

/**  Klasa ExpressionTree. Parsira upit u DIALOG jeziku i pravi SQL upit.
  *
  */
public class ExpressionTree {

  public ExpressionTree(DocumentManager docManager) {
    root = new TreeNode(ElemTypes.TERM, 4, "", "", null);
    this.docManager = docManager;
    subqueries = new Vector();
  }

  public void execute(String query) throws ExpressionException {
    setCancel(false);
    this.query = query;
    subqueries.removeAllElements();
    createInfixList();
    if (isCancel())
      throw new ExpressionException(Messages.get("QUERY_INTERRUPTED"));
    createPostfixList();
    if (isCancel())
      throw new ExpressionException(Messages.get("QUERY_INTERRUPTED"));
    createTree();
    if (isCancel())
      throw new ExpressionException(Messages.get("QUERY_INTERRUPTED"));
    traverse(root);
    // eliminisi duplikate iz konacnog rezultata
    // (isti dokument ne treba da se pojavljuje vise puta u rezultatu)
    // ovo treba raditi ako je u korenu stabla term ili blizinski operator
    // inace garantovano nema duplikata
    if ((root.getType() == ElemTypes.TERM) ||
        ((root.getType() == ElemTypes.OPERATOR) && (root.getCategory() > 1))) {
      com.objectspace.jgl.HashSet newSet = new com.objectspace.jgl.HashSet(new BooleanComparator());
      Enumeration e = root.getHits().elements();
      while (e.hasMoreElements())
        newSet.add(e.nextElement());
      root.setHits(newSet);
    }
  }

  public TreeNode getRoot() {
    return root;
  }

  /**  Kreiranje infiksne liste parsiranjem datog upita. Nakon sto se
    *  upit podeli na tokene oko simbola (, ), = i <razmak> za svaki
    *  token se radi sledece:<BR>
    *  <UL>
    *  <LI>Ako je token <razmak>, ignorise se;
    *  <LI>Ako je token (, dodaje se u infiksnu listu;
    *  <LI>Ako je token ), dodaje se u infiksnu listu;
    *  <LI>Ako je token =, proverava se da li je prethodno definisan prefiks.
    *  Ako nije, baca izuzetak, inace ignorise token;
    *  <LI>Ako je token naziv prefiksa, pamti se;
    *  <LI>Ako je token operator, dodaje se u infiksnu listu;
    *  <LI>Jedina preostala mogucnost je da je token term za pretragu pa
    *  se dodaje u listu, zajedno sa svojim prefiksom ako je on definisan.
    *  @throws ExpressionException
    */
  private void createInfixList() throws ExpressionException {
    infixList.removeAllElements();
    String prefix = null;
    String aToken = null;
    boolean operatorActive = true;
    IDused = false;
    StringTokenizer st = new StringTokenizer(query, "()= ", true);
    while (st.hasMoreTokens()) {
      aToken = st.nextToken().toUpperCase();
      // preskoci razmake
      if (aToken.charAt(0) == ' ')
        continue;
      // naisli smo na otvorenu zagradu
      if (aToken.charAt(0) == '(') {
        infixList.addElement(new ListElem(ElemTypes.OPEN_BRACKET, null, null, 0));
        continue;
      }
      // naisli smo na zatvorenu zagradu
      if (aToken.charAt(0) == ')') {
        infixList.addElement(new ListElem(ElemTypes.CLOSED_BRACKET, null, null, 0));
        operatorActive = false;
        continue;
      }
      // naisli smo na znak =
      if (aToken.charAt(0) == '=') {
        if (prefix == null)
          throw new ExpressionException(Messages.get("NO_PREFIX_SPECIFIED"));
        continue;
      }
      // da li je u pitanju naziv prefiksa
      if ((docManager.getAllPrefixes().count(aToken) > 0) && prefix == null) {
        prefix = aToken;
        if (prefix.equals("ID"))
          IDused = true;
        continue;
      }
      // da li je u pitanju operator
      if (aToken.equals("AND") || aToken.equals("OR")  || aToken.equals("NOT") ||
          aToken.equals("[F]") || aToken.equals("[S]") ||
          aToken.substring(0, aToken.length() > 1 ? 2 : aToken.length()).equals("[W")) {
        if (prefix != null) {
          //throw new ExpressionException("Operator "+aToken+" prona\u0111en, a o\u010dekuje se term.");
          infixList.addElement(new ListElem(ElemTypes.TERM, aToken, prefix, 0));
          prefix = null;
          operatorActive = false;
          continue;
        } else {
          operatorActive = true;
          infixList.addElement(new ListElem(ElemTypes.OPERATOR, aToken, null, getPriority(aToken)));
          continue;
        }
      }
      // ako nije nista od prethodnog onda mora biti rec
      if (!operatorActive)
        throw new ExpressionException(
          Messages.get("OPERATOR_OR_CLOSED_BRACKET_EXPECTED"));
      infixList.addElement(new ListElem(ElemTypes.TERM, aToken, prefix, 0));
      if (IDused)
        if (prefix.equals("ID"))
          try {
            int docID = Integer.valueOf(aToken).intValue();
          } catch (Exception ex) {
            throw new ExpressionException(
              Messages.get("ID_IS_NOT_POSITIVE_INTEGER"));
          }
      prefix = null;
      operatorActive = false;
    }
    if (operatorActive)
      throw new ExpressionException(
        Messages.get("OPERATOR")+ " " + aToken + " " + 
        Messages.get("HAS_TWO_OPERANDS"));
  }

  /**  Kreiranje postfiksne liste na osnovu infiksne (standardan algoritam).<BR>
    *  1. Uzima se element iz infiksne liste<BR>
    *  2.1. Ako je tekuci element term, dodaje se u postfiksnu listu
    *  2.2. Ako je tekuci element otvorena zagrada, stavlja se na stek
    *  2.3. Ako je tekuci element zatvorena zagrada, sa steka se skidaju svi
    *  elementi do prve otvorene zagrade i dodaju u postfisknu listu u
    *  redosledu u kom su skidani sa steka. Tekuci element se ne dodaje u listu.
    *  2.4. Ako je tekuci element operator, sa steka se skidaju operatori koji
    *  imaju veci prioritet do prvog elementa koji ima manji prioritet ili do
    *  prve otvorene zagrade i dodaju se u postfiksnu listu po redosledu skidanja.
    *  Tekuci element se nakon toga stavlja na stek.
    *  3. Postupak se ponavlja sve dok se infiksna lista ne isprazni
    *  4. Ako je na steku ostalo elemenata, oni se dodaju u postfiksnu listu u
    *  redosledu skidanja.
    */
  private void createPostfixList() {
    postfixList.removeAllElements();
    java.util.Stack stack = new java.util.Stack();
    ListElem listElem;
    Enumeration e = infixList.elements();
    while (e.hasMoreElements()) {
      listElem = (ListElem)e.nextElement();
      switch (listElem.getType()) {
        case ElemTypes.TERM:
          postfixList.addElement(listElem);
          break;
        case ElemTypes.OPEN_BRACKET:
          stack.push(listElem);
          break;
        case ElemTypes.CLOSED_BRACKET:
          while (!stack.empty() && ((ListElem)stack.peek()).getType() != ElemTypes.OPEN_BRACKET)
            postfixList.addElement(stack.pop());
          if (!stack.empty() && ((ListElem)stack.peek()).getType() == ElemTypes.OPEN_BRACKET)
            stack.pop();
          break;
        case ElemTypes.OPERATOR:
          while (!stack.empty() && ((ListElem)stack.peek()).getType() != ElemTypes.OPEN_BRACKET && ((ListElem)stack.peek()).getPriority() > listElem.getPriority())
            postfixList.addElement(stack.pop());
          stack.push(listElem);
          break;
      }
    }
    while (!stack.empty()) {
      listElem = (ListElem)stack.pop();
      if (listElem.getType() != ElemTypes.OPEN_BRACKET)
        postfixList.addElement(listElem);
    }
  }

  /**  Kreira stablo upita na osnovu postfiksne liste. Sledi opis algoritma:<BR>
    *  1. Iz liste se vadi poslednji element.
    *  2.1. Ako je stablo prazno element postaje koren stabla; tekuci element
    *  stabla postaje koren.
    *  2.2. Ako je tekuci element stabla operator, pogleda se da li njegov desni
    *  podredjeni. Ako ne postoji, element iz liste postaje njegov desni podredjeni
    *  i postaje tekuci element stabla. Ako desni podredjeni postoji, zbog prirode
    *  algoritma levi podredjeni sigurno ne postoji i element iz liste postaje
    *  levi podredjeni i postaje tekuci element stabla.
    *  2.3. Ako je tekuci element stabla term, trazi se prvi njegov predak
    *  koji nema levog podredjenog, a operator je. Kada se takav element nadje,
    *  element iz liste postaje njegov levi podredjeni i posle toga postaje
    *  tekuci element stabla.
    *  3. Postupak se ponavlja dok se ne isprazni lista.
    *  @throws ExpressionException
    */
  private void createTree() throws ExpressionException {
    ListElem listElem = (ListElem)postfixList.elementAt(postfixList.size()-1);
    root = new TreeNode(listElem.getType(), getCategory(listElem), listElem.getPrefix(), listElem.getContent(), null);
    TreeNode currNode = root;
    if (postfixList.size() == 1)
      return;
    for (int i = postfixList.size()-2; i >=0; i--) {
      listElem = (ListElem)postfixList.elementAt(i);
      TreeNode newNode = new TreeNode(listElem.getType(), getCategory(listElem), listElem.getPrefix(), listElem.getContent(), currNode);
      if (currNode.getType() == ElemTypes.OPERATOR) {
        if (currNode.getRightChild() == null)
          currNode.setRightChild(newNode);
        else
          currNode.setLeftChild(newNode);
      } else {
        TreeNode searchNode = currNode;
        while ((searchNode != null) &&
              ((searchNode.getType() != ElemTypes.OPERATOR) ||
               (searchNode.getLeftChild() != null)))
          searchNode = searchNode.getParent();
        if (searchNode == null)
          throw new ExpressionException(Messages.get("ILLEGAL_EXPRESSION"));
        if ((newNode.getType() == ElemTypes.OPERATOR) &&
            (newNode.getCategory() < searchNode.getCategory()))
          throw new ExpressionException(Messages.get("ILLEGAL_CATEGORY"));
        searchNode.setLeftChild(newNode);
      }
      currNode = newNode;
    }
  }

  /**  Obilazi stablo po dubini i odredjuje pogotke; za listove to je SQL upit, a
    *  za operatore to se izracunava.
    *  @param node TreeNode od kojeg pocinje obilazak stabla
    */
  private void traverse(TreeNode node) throws ExpressionException {
    if (isCancel())
      throw new ExpressionException(Messages.get("QUERY_INTERRUPTED"));
    if (node.getLeftChild() != null)
      traverse(node.getLeftChild());
    if (node.getRightChild() != null)
      traverse(node.getRightChild());
    if (node.getType() == ElemTypes.OPERATOR) {
      if (node.getContent().equals("AND")) {
        node.setHits(new com.objectspace.jgl.HashSet(new BooleanComparator()));
        node.getHits().copy2(node.getLeftChild().getHits());
        node.setHits(node.getHits().intersection2(node.getRightChild().getHits()));
      } else if (node.getContent().equals("OR")) {
        node.setHits(new com.objectspace.jgl.HashSet(new BooleanComparator()));
        node.getHits().copy2(node.getLeftChild().getHits());
        node.setHits(node.getHits().union(node.getRightChild().getHits()));
      } else if (node.getContent().equals("NOT")) {
        node.setHits(new com.objectspace.jgl.HashSet(new BooleanComparator()));
        node.getHits().copy2(node.getLeftChild().getHits());
        node.setHits(node.getHits().difference2(node.getRightChild().getHits()));
      } else if (node.getContent().equals("[F]")) {
        node.setHits(new com.objectspace.jgl.HashSet(new FieldComparator()));
        node.getHits().copy2(node.getLeftChild().getHits());
        node.setHits(node.getHits().intersection2(node.getRightChild().getHits()));
      } else if (node.getContent().equals("[S]")) {
        node.setHits(new com.objectspace.jgl.HashSet(new SentenceComparator()));
        node.getHits().copy2(node.getLeftChild().getHits());
        node.setHits(node.getHits().intersection2(node.getRightChild().getHits()));
      } else {
        int distance;
        int length = node.getContent().length();
        if (length > 3)
          distance = Integer.valueOf(node.getContent().substring(2, length-1)).intValue();
        else
          distance = 1;
        node.setHits(new com.objectspace.jgl.HashSet(new DistanceComparator(distance)));
        node.getHits().copy2(node.getRightChild().getHits());
        node.setHits(node.getHits().intersection2(node.getLeftChild().getHits()));
      }
    } else { // sigurno je list stabla; postavljamo upit
      node.setHits(docManager.executeSelect(node.getPrefix(), node.getContent()));
      if (isCancel())
        throw new ExpressionException(Messages.get("QUERY_INTERRUPTED"));
      subqueries.addElement(new SubqueryHit(node.getPrefix(), node.getContent(), node.getHits().size()));
    }
  }

  public Vector getSubqueries() {
    return subqueries;
  }

  /** Prekida SELECT upit koji se izvrsava u drugom threadu.
    */
  public void cancelSelect() {
    docManager.cancelSelect();
    setCancel(true);
  }

  /**  Vraca prioritet datog operatora u obliku celog broja. Veci
    *  broj oznacava visi prioritet. Prioritet operatora je definisan
    *  u DIALOG specifikaciji.
    *  @param oper String koji sadrzi naziv operatora
    *  @return Prioritet operatora
    */
  private int getPriority(String oper) {
    if (oper.equals("NOT"))
      return 2;
    if (oper.equals("AND"))
      return 1;
    if (oper.equals("OR"))
      return 0;
    return 3;
  }

  /**  Vraca kategoriju datog operatora. Kategorija se koristi za
    *  kontrolu ispravnosti stabla pretrage.
    *  @param oper String koji sadrzi naziv operatora
    *  @return Kategorija operatora
    */
  private int getCategory(ListElem elem) {
    if (elem.getType() == ElemTypes.TERM)
      return 4;
    if (elem.getContent().equals("AND") || elem.getContent().equals("OR") || elem.getContent().equals("NOT"))
      return 1;
    if (elem.getContent().equals("[F]"))
      return 2;
    if (elem.getContent().equals("[S]"))
      return 3;
    return 4;
  }

  private synchronized void setCancel(boolean cancel) {
    this.cancel = cancel;
  }

  private synchronized boolean isCancel() {
    return cancel;
  }

  private TreeNode root;
  private Vector infixList = new Vector();
  private Vector postfixList = new Vector();
  private String query;
  private DocumentManager docManager;
  private Vector subqueries;
  private boolean IDused;
  private boolean cancel;
}
