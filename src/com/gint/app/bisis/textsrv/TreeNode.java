package com.gint.app.bisis.textsrv;

/** Klasa TreeNode
  *
  */
public class TreeNode {

  public TreeNode(int type, int category, String prefix, String content, TreeNode parent) {
    this.type = type;
    this.category = category;
    this.content = content;
    this.parent = parent;
    this.prefix = prefix;
    hits = null;
    leftChild = null;
    rightChild = null;
  }

  public int getType() {
    return type;
  }

  public int getCategory() {
    return category;
  }

  public String getContent() {
    return content;
  }

  public String getPrefix() {
    return prefix;
  }

  public TreeNode getParent() {
    return parent;
  }

  public TreeNode getLeftChild() {
    return leftChild;
  }

  public TreeNode getRightChild() {
    return rightChild;
  }

  public com.objectspace.jgl.HashSet getHits() {
    return hits;
  }

  public void setType(int type) {
    this.type = type;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public void setParent(TreeNode parent) {
    this.parent = parent;
  }

  public void setLeftChild(TreeNode leftChild) {
    this.leftChild = leftChild;
  }

  public void setRightChild(TreeNode rightChild) {
    this.rightChild = rightChild;
  }

  public void setHits(com.objectspace.jgl.HashSet hits) {
    this.hits = hits;
  }

  public String toString() {
    String retVal = new String("");
    if (leftChild != null)
      retVal += "{L:"+leftChild.toString()+"}";
    if (rightChild != null)
      retVal += "{R:"+rightChild.toString()+"}";
    retVal += "type:"+type+" category:"+category+" content:"+content;
    return retVal;
  }

  /*
  public static void main(String[] args) {
    HashSet hs1 = new HashSet();
    HashSet hs2 = new HashSet();
    HashSet hs3 = new HashSet(new BooleanComparator());
    HashSet hs4 = new HashSet();
    hs1.add(new SearchHit(1,1,1,1));
    hs1.add(new SearchHit(1,9,1,1));
    hs1.add(new SearchHit(5,3,1,1));
    hs4.add(new SearchHit(1,2,1,1));
    hs4.add(new SearchHit(5,22,1,1));
    hs3.add(new SearchHit(1,21,1,1));
    hs3.add(new SearchHit(5,21,1,1));
    System.out.println("hs1: " + hs1);
    System.out.println("hs4: " + hs4);
    System.out.println("hs3: " + hs3);
    hs3 = hs3.intersection2(hs1);
    System.out.println("hs3: " + hs3);
    hs3 = hs3.union(hs4);
    System.out.println("hs3: " + hs3);
  }
  */
  
  private int type;
  private int category;
  private String content;
  private String prefix;
  private TreeNode parent;
  private TreeNode leftChild;
  private TreeNode rightChild;
  private com.objectspace.jgl.HashSet hits;
}