package com.gint.app.bisis.editor.report;

import java.util.Vector;
import java.util.StringTokenizer;

import com.gint.app.bisis.editor.edit.*;
import com.gint.app.bisis.editor.*;
import com.gint.util.string.StringUtils;



/** Catalog -- klasa za generisanje razlicitih vrsta kataloskih listica.
    @author Tatjana Zubic tanja@uns.ns.ac.yu
    @version 1.0
  */
public class Catalog {

  public Catalog(int docID, String ulazniZapis, String code) {
    this.code = code;
    this.docID = docID;

  /** Prebacivanje zapisa u memorijsku strukturu (raspakivanje)
  */
    RezanceUtilities ru = new RezanceUtilities();
    fields = RezanceUtilities.unpackRezance(ulazniZapis);
  }

  public Catalog() {
  }

  /** Desno ravnanje stringa na stranici
      @param in ulazni string koji se poravnava
      @return desno poravnat string
  */
  private String rightAlign(String in) {

    if (!in.equals("")) {
     String blanko = new String("");
     for (int j=bk; j < (Report.bkmax-in.trim().length()); j++)
        blanko += "&nbsp;";
     in = blanko + in;
 //     in = blanko + in + "<BR>\n";
    }
    return in;
  }

  /** Vertikalno centriranje teksta na strani za uputne listice
      @param in ulazni string koji se poravnava
      @return vertikalno na stranici poravnat string
  */
  private String verticalAlign(String in) {
    String out = new String("");
    StringTokenizer st = new StringTokenizer(in,"\n");
    int i;

    for (i=0; i<(Report.brmax-br)/2; i++)  {
      out += "\n";
    }
    br = br + i + st.countTokens();
    while (st.hasMoreTokens()) {
      String sgPiece = st.nextToken();
      for (int j=0; j < (Report.bkmax-sgPiece.length())/2; j++)
        out += " ";
      out += sgPiece + "\n";
    }
    out = doKrajaStrane(out);
    return out;
  }


  /** Dodavanje praznih redova do kraja listica
      @param in ulazni string koji ne dopire do kraja listica
      @return  string koji popunjava ceo listic
  */
  public String doKrajaStrane(String in) {
    if (br > 1) {
      for (int i = br; i <= Report.brmax; i++) {
        in += "<BR>\n";
      }
      br = 1;
      bk = 1;
    }
    return in;
  }

  /** Provera da li tip UNIMARC zapisa odgovara tipu izvestaja
      @param typeRep tip izvestaja dobijen iz sifre
      @return boolean
  */
  public boolean checkType(int typeRep) {
    Concept concept = new Concept();
    ReportFields rf = new ReportFields();


    Vector v = concept.getField(fields, "000", " ", " ");
    if (v.size() > 0) {
      String tip = rf.getFieldContent010((Field)v.elementAt(0),"@");
      tip = tip.substring(0,3);
      if (tip.equals("001")|| tip.equals("002") || tip.equals("003")) { // monografske publikacije
        if (typeRep == 1 || typeRep == 2 || typeRep == 3 || typeRep == 4 || typeRep == 5 || typeRep == 6 || typeRep == 7 ||
            typeRep == 11 || typeRep == 13 || typeRep == 14 || typeRep == 15 || typeRep == 16 || typeRep == 17
            || typeRep == 99  )
//probno            || typeRep == 41 || typeRep == 42)
          return true;
        else
          return false;
      }
      else if (tip.equals("004") || tip.equals("005")) {    // serijske publikacije S1
        if (typeRep == 81 || typeRep == 82 || typeRep == 83 )
          return true;
        else
          return false;
      }
      else if (tip.equals("006")) { // clanci A1
        if (typeRep == 41 || typeRep == 42 )
          return true;
        else
          return false;
      }
      else if (tip.equals("007")) { // neknjizna gradja N0
        if (typeRep == 1 || typeRep == 2 || typeRep == 3 || typeRep == 4 || typeRep == 5 || typeRep == 6 || typeRep == 7 )
          return true;
        else
          return false;
      }
      else if (tip.equals("008")) { // C_ ?????
//        if (typeRep == 1 )
//          return true;
//        else
          return false;
      }
      else if (tip.equals("009")) { // doktorske disertacije DS
        if (typeRep == 21 || typeRep == 22 || typeRep == 23 || typeRep == 24 || typeRep == 25 || typeRep == 26 || typeRep == 27 ||
            typeRep == 31 || typeRep == 32 || typeRep == 33 || typeRep == 34 || typeRep == 35 || typeRep == 36 || typeRep == 37)
          return true;
        else
          return false;
      }
      else if (tip.equals("010")) { // R_ ??????
//        if (typeRep == 21 )
//          return true;
//        else
          return false;
      }
      else if (tip.equals("017")) { // kartografska gradja NK
//        if (typeRep == 21 )
//          return true;
//        else
          return false;
      }
      else if (tip.equals("027")) { // notna gradja NN
//        if (typeRep == 21 )
//          return true;
//        else
          return false;
      }
      else if (tip.equals("037")) { // racunarske datoteke RD
//        if (typeRep == 21 )
//          return true;
//        else
          return false;
      }
      else
        return false;
    }
    else
      return false;
  }

  public int getType() {
    Concept concept = new Concept();
    ReportFields rf = new ReportFields();

    Vector v = concept.getField(fields, "000", " ", " ");
    if (v.size() > 0) {
      String tip = rf.getFieldContent010((Field)v.elementAt(0),"a");
      tip = tip.substring(0,3);
      if (tip.equals("001")|| tip.equals("002") || tip.equals("003")) { // monografske publikacije
          return 1;
      }
      else if (tip.equals("004") || tip.equals("005")) {    // serijske publikacije S1
          return 4;
      }
      else if (tip.equals("006")) { // clanci A1
          return 6;
      }
      else if (tip.equals("007")) { // neknjizna gradja N0
          return 7;
      }
      else if (tip.equals("008")) { // C_ ?????
          return 8;
      }
      else if (tip.equals("009")) { // doktorske disertacije DS
          return 9;
      }
      else if (tip.equals("010")) { // R_ ?????? mozda rariteti
          return 10;
      }
      else if (tip.equals("017")) { // kartografska gradja NK
          return 17;
      }
      else if (tip.equals("027")) { // notna gradja NN
          return 27;
      }
      else if (tip.equals("037")) { // racunarske datoteke RD
          return 37;
      }
      else
        return 0;
    }
    else
      return 0;
  }

  public int getTypeInv(String inv) {
    
    
       
      inv = inv.substring(0,2);
      if (inv.equals("00")){
          return 0;
      }
      else if (inv.equals("01")){
          return 1;
      }
      else if (inv.equals("02")) { 
          return 2;
      }
      else if (inv.equals("03")) { 
          return 3;
      }
      else if (inv.equals("04")) { 
          return 4;
      }
      else if (inv.equals("05")) { 
          return 5;
      }
      else if (inv.equals("06")) { 
          return 6;
      }
      else if (inv.equals("07")) { 
          return 7;
      }
      else if (inv.equals("08")) { 
          return 8;
      }
      else if (inv.equals("09")) { 
          return 9;
      }
      else if (inv.equals("10"))
        return 10;
  
    else if (inv.equals("11")) { 
        return 11;
    }
    else if (inv.equals("12")) { 
        return 12;
    }
    else if (inv.equals("13")) { 
        return 13;
    }
    else if (inv.equals("14")) { 
        return 14;
    }
    else if (inv.equals("15")) { 
        return 15;
    }
    else if (inv.equals("16")) { 
        return 16;
    }
    else if (inv.equals("17")) { 
        return 17;
    }
    else if (inv.equals("18")) { 
        return 18;
    }
    else if (inv.equals("19"))
        return 19;
  
    else if (inv.equals("20")) { 
        return 20;
    }
    else if (inv.equals("21")) { 
        return 21;
    }
    else if (inv.equals("22")) { 
        return 22;
    }
    else if (inv.equals("23")) { 
        return 23;
    }
    else if (inv.equals("24")) { 
        return 24;
    }
    else if (inv.equals("25")) { 
        return 25;
    }
    else if (inv.equals("26")) { 
        return 26;
    }
    else if (inv.equals("27")) { 
        return 27;
    }
    else if (inv.equals("28"))
        return 28;
  
    else if (inv.equals("29")) { 
        return 29;
    }
    else{
        return 30;
    }
   
  }


  /** Formatiranje listica za odredjenu velicinu stranice za slucaj kad je jedna signatura na jednom listicu.
      @param in ulazni string koji se formatira
      @param signatura signatura konkretnog listica ukoliko se listic prostire na vise stranica
      @param sOdrednica sporedna odrednica konkretnog listica ukoliko se listic prostire na vise stranica
      @return formatiran listic koji odgovara zadatoj velicini strane
  */
  public String formatIzlaz(String in, String signatura, String sOdrednica) {
    Concept concept = new Concept();
    String retVal = new String("");
    String out = new String("");

    for (int i=0; i < in.length(); i++)  {
      if (in.substring(i,i+1).equals("\n")) {
        out += "<BR>\n";
        br++; bk = 1;
//        if (br == Report.brmax && in.substring(i+1).length() > 1) {
        if (br == Report.brmax) {
          String in1 = StringUtils.replace(in.substring(i+1), "&nbsp;", " ");
          if (in1.length() > Report.bkmax) {
            out += rightAlign("%") + "<BR>\n";
            br = 1; bk = 1;
            strana++;
            out += rightAlign(String.valueOf(strana)) + "<BR>\n"; br++;
            out += rightAlign(signatura) + "<BR>\n"; br++;
            if (!sOdrednica.equals("")) {
              sOdrednica = formatIzlaz(sOdrednica,"","");
              out += sOdrednica + "<BR>\n";
              br = br + (int)sOdrednica.length()/Report.bkmax;  //nije precizno zbog preloma reci
              bk = 1;
            }
            String zag = concept.odrednica(fields);
            if (zag.equals(""))
              zag = concept.zaglavlje(fields);
            zag = formatIzlaz(zag,"","");
            bk=1;
            out += zag + "<BR>\n"; br++;
          }
        }
      }
      else
        if (in.substring(i,i+1).equals(" ")) {
          if (bk == Report.bkmax) {
           out += "<BR>\n";
           br++; bk = 1;
          }
          else {
            int n = i+1;
            while (n < in.length() && !in.substring(n,n+1).equals(" ") && !in.substring(n,n+1).equals("\n"))
              n++;
            if ((n-i-1) + bk > Report.bkmax) {  // rec je duza od duzine reda
              out += "<BR>\n";
              br++; bk = 1;
            }
            else {
              out += in.substring(i,i+1);
              bk++;
            }
          }
          if (br == Report.brmax) {
            String in1 = StringUtils.replace(in.substring(i+1), "&nbsp;", " ");
            if (in1.length() > Report.bkmax) {
              out += rightAlign("%") + "<BR>\n";
              br = 1; bk = 1;
              strana++;
              out += rightAlign(String.valueOf(strana)) + "<BR>\n"; br++;
              out += rightAlign(signatura) + "<BR>\n"; br++;
              if (!sOdrednica.equals("")) {
                sOdrednica = formatIzlaz(sOdrednica,"","");
                out += sOdrednica + "<BR>\n";
                br = br + (int)sOdrednica.length()/Report.bkmax;  //nije precizno zbog preloma reci
                bk = 1;
              }

              String zag = concept.odrednica(fields);
              if (zag.equals(""))
                zag = concept.zaglavlje(fields);
              zag = formatIzlaz(zag,"","");
              bk=1;
              out += zag + "<BR>\n"; br++;
            }
          }
        }
        else  {
          if (in.substring(i,i+1).equals("<")) {  //treba preskociti pri brojanju
            if (in.substring(i,i+3).equals("<B>")) {
               out += in.substring(i,i+3);
               i = i+2;
            }
            else if (in.substring(i,i+4).equals("</B>"))  {
              out += in.substring(i,i+4);
              i = i+3;
            }
          }
          else
            if (in.substring(i,i+1).equals("&")) {
              int pos = in.indexOf(';', i);
              out += in.substring(i,pos+1);
              i = pos;
              bk++;
            }
            else {
              bk++;
              out += in.substring(i,i+1);
            }
        }
    }
    return out;
 }

  
  
  

  
  /** Formatiranje listica za odredjenu velicinu stranice za slucaj
      kad je vise (od 3) signatura na jednom listicu.
      Specijalno napravljano za one biblioteke koje nece samo jednu signaturu na jednom listicu
  */
  public String formatIzlazFF(String in, String signatura, String sOdrednica) {
    Concept concept = new Concept();
    String retVal = new String("");
    String out = new String("");

    for (int i=0; i < in.length(); i++)  {
      if (in.substring(i,i+1).equals("\n")) {
        out += "<BR>\n";
        br++; bk = 1;
        if (br == Report.brmax) {
          String in1 = StringUtils.replace(in.substring(i+1), "&nbsp;", " ");
          if (in1.length() > Report.bkmax) {
          //ako je duze od jednog reda nastaviti na sledecoj strani
            out += /*rightAlign("%")*/"Jo..." + "<BR>\n";
            br = 1; bk = 1;
            strana++;
            out += rightAlign(String.valueOf(strana)) + "<BR>\n";
            br++;
            if (signatura.indexOf("\n") != -1) {
              StringTokenizer st = new StringTokenizer(signatura, "\n");
              while (st.hasMoreTokens()) {
                String sgPiece = st.nextToken();
                out += /*rightAlign(sgPiece)*/ sgPiece + "<BR>\n";
                br++;
              }
            }
            else  {
              out += signatura /*rightAlign(signatura)*/ + "<BR>\n";
              br++;
            }
            if (!sOdrednica.equals("")) {
              sOdrednica = formatIzlaz(sOdrednica,"","");
              out += sOdrednica + "<BR>\n";
              br = br + (int)sOdrednica.length()/Report.bkmax;  //nije precizno zbog preloma reci
              bk = 1;
            }
            String zag = concept.odrednica(fields);
            if (zag.equals(""))
              zag = concept.zaglavlje(fields);
            zag = formatIzlaz(zag,"","");
            bk=1;
            out += zag + "<BR>\n";
            br++;
          }
        }
      }
      else if (in.substring(i,i+1).equals(" ")) {
        if (bk == Report.bkmax) {
          out += "<BR>\n";
          br++; bk = 1;
        }
        else {
          int n = i+1;
          while (n < in.length() && !in.substring(n,n+1).equals(" ") && !in.substring(n,n+1).equals("\n"))
            n++;
          if ((n-i-1) + bk > Report.bkmax) {  // rec je duza od duzine reda
            out += "<BR>\n";
            br++; bk = 1;
          }
          else {
            out += in.substring(i,i+1);
            bk++;
          }
        }

        if (br == Report.brmax) {
          String in1 = StringUtils.replace(in.substring(i+1), "&nbsp;", " ");
          if (in1.length() > Report.bkmax) { //umesto 1
            out += /*rightAlign("%")*/ "Jo..." + "<BR>\n";
            br = 1; bk = 1;
            strana++;
            out += rightAlign(String.valueOf(strana)) + "<BR>\n";
            br++;
            if (signatura.indexOf("\n") != -1) {
              StringTokenizer st = new StringTokenizer(signatura, "\n");
              while (st.hasMoreTokens()) {
                String sgPiece = st.nextToken();
                out += /*rightAlign(sgPiece)*/ sgPiece  + "<BR>\n";
                br++;
              }
            }
            else {
              out += /*rightAlign(signatura)*/ signatura + "<BR>\n";
              br++;
            }
            if (!sOdrednica.equals("")) {
              sOdrednica = formatIzlaz(sOdrednica,"","");
              out += sOdrednica + "<BR>\n";
              br = br + (int)sOdrednica.length()/Report.bkmax;  //nije precizno zbog preloma reci
              bk = 1;
            }
            String zag = concept.odrednica(fields);
            if (zag.equals(""))
              zag = concept.zaglavlje(fields);
            zag = formatIzlaz(zag,"","");
            bk=1;
            out += zag + "<BR>\n";
            br++;
          }
        }
      }
      else  {
         if (in.substring(i,i+1).equals("<")) {  //<B> i </B> treba preskociti pri brojanju
           if (in.substring(i,i+3).equals("<B>")) {
              out += in.substring(i,i+3);
              i = i+2;
           }
           else if (in.substring(i,i+4).equals("</B>"))  {
             out += in.substring(i,i+4);
             i = i+3;
           }
         }
         else if (in.substring(i,i+1).equals("&")) {
           int pos = in.indexOf(';', i);
           out += in.substring(i,pos+1);
           i = pos;
           bk++;
         }
         else {
           out += in.substring(i,i+1);
           bk++;
         }
       }
    }
    return out;
  }
/**  Formatira listic tako da svaki red sadrzi 14 slova koristi se u metodi zaStampu */
 
  
  public String formatRed(String in) {
    Concept concept = new Concept();
    String retVal = new String("");
    String out = new String("");
   

    for (int i=0; i < in.length(); i++)  {
      //if ((br==1)&&(strana==1)){
        //if(!stampa)
            //out+=docID + "<BR>\n" ; br++;
        //  } 
             
        if (in.substring(i,i+1).equals("\n")) {
          
          out += "<BR>\n";
          br++; bk = 1;
          
//          if (br == Report.brmax && in.substring(i+1).length() > 1) {
          
        }
        else
          if (in.substring(i,i+1).equals(" ")) {
            if (bk == Report.bkmax) {
             out += "<BR>\n";
             br++;
             bk = 1;out +="&nbsp;";
            }
            else {
              int n = i+1;
              while (n < in.length() && !in.substring(n,n+1).equals(" ") && !in.substring(n,n+1).equals("\n"))
                n++;
              if ((n-i-1) + bk > Report.bkmax) {  // rec je duza od duzine reda
                out += "<BR>\n";
                br++; bk = 1;out +="&nbsp;";
              }
              else {
                out += in.substring(i,i+1);
                bk++;
              }
            }
            
          }
          
          else  {
            
            if (in.substring(i,i+1).equals("<")) {  //treba preskociti pri brojanju
              if (in.substring(i,i+3).equals("<B>")) {
                 out += in.substring(i,i+3);
                 i = i+2;
              }
              else if (in.substring(i,i+4).equals("</B>"))  {
                out += in.substring(i,i+4);
                i = i+3;
              }
            }
            else
              if (in.substring(i,i+1).equals("&")) {
                int pos = in.indexOf(';', i);
                out += in.substring(i,pos+1);
                i = pos;
                bk++;
              }
              else {
                bk++;
                out += in.substring(i,i+1);
              }
          }
      }
    
      return out;
   }
  
  /** koristi se u metodi osnovniListic, koji je samo za gledanje na ekranu   */
  
  public String formatRed1(String in) {
    Concept concept = new Concept();
    String retVal = new String("");
    String out = new String("");
   

    for (int i=0; i < in.length(); i++)  {
      if ((br==1)&&(strana==1)){
        //if (!stampa)
            out+=docID + "<BR>\n" ; br++;
          } 
             
        if (in.substring(i,i+1).equals("\n")) {
          
          out += "<BR>\n";
          br++; bk = 1;
          
//          if (br == Report.brmax && in.substring(i+1).length() > 1) {
          
        }
        else
          if (in.substring(i,i+1).equals(" ")) {
            if (bk == Report.maxKol) {
             out += "<BR>\n";
             br++; bk = 1;out +="&nbsp;";
            }
            else {
              int n = i+1;
              while (n < in.length() && !in.substring(n,n+1).equals(" ") && !in.substring(n,n+1).equals("\n"))
                n++;
              if ((n-i-1) + bk > Report.maxKol) {  // rec je duza od duzine reda
                out += "<BR>\n";
                br++; bk = 1;out +="&nbsp;";
              }
              else {
                out += in.substring(i,i+1);
                bk++;
              }
            }
            
          }
          
          else  {
            
            if (in.substring(i,i+1).equals("<")) {  //treba preskociti pri brojanju
              if (in.substring(i,i+3).equals("<B>")) {
                 out += in.substring(i,i+3);
                 i = i+2;
              }
              else if (in.substring(i,i+4).equals("</B>"))  {
                out += in.substring(i,i+4);
                i = i+3;
              }
            }
            else
              if (in.substring(i,i+1).equals("&")) {
                int pos = in.indexOf(';', i);
                out += in.substring(i,pos+1);
                i = pos;
                bk++;
              }
              else {
                bk++;
                out += in.substring(i,i+1);
              }
          }
      }
    
      return out;
   }
  /**  broji redove u listicu ako listic ima vise redova nego sto staje ne jednu stranu pravi novu stranu  
   *   koristi se u metodi zaStampu
   * @param in predstavlja sadrzaj listica
   * @param sign signatura
   
   */
  public String formatStrana(String in,String sign){
    String out=new String();
    String stariRed=new String();
    int br=0;
    int i=0;
    while(i<in.length()){
      
      String red=new String();      
      
      
      boolean jos=true;
      while((i< in.length())&& jos){
        if( in.substring(i,i+1).equals("\n"))
          jos=false;
        red+=in.substring(i,i+1);
        i++;
      }
      br++; 
      if (br==Report.brmax){
        strana++;
        out+="Jos..."+"<BR>\n"+rightAlign(String.valueOf(strana))+"<BR>\n";
      
      //out+=docID+"<BR>\n";
         out+=sign+"<BR>\n";
           br=3;
        
      }
      out+=red;
          
    } 
    
      for(int k=br;br<Report.brmax;br++ )
        out+="<BR>\n";
    
    return out;
  }
  

  /** Kreira osnovni skup koncepata za listice koji se uvek javljaju
      @return string sa neformatiranim osnovnim skupom koncepata
  */
  private String base() {
    Concept concept = new Concept();

    String out = new String("");
    out += concept.odrednica(fields);
    if (doktorska)
      out += concept.dsTitula(fields);
    out += concept.zaglavlje(fields);
    out += concept.alone532(fields);
    out += concept.glavniOpis(fields);
    if (doktorska) {
      String o = concept.dsOdbrana(fields);
      String p = concept.dsPromocija(fields);
      out += o;
      if (!p.equals("")) {
        for (int i=0; i < Report.bkmax - o.length()- p.length(); i++) {
          out += "&nbsp;";
        }
      }
      out += p;
      out += ((o.equals("") && p.equals("")) ? "\n" : "") + concept.dsKomisija(fields);
    }
    out += concept.napomene(fields);
    out += concept.prilozi(fields);
    out += concept.brojISBN(fields);
//    if (glavni) {
    if (!noTes) {
      out += concept.arapTrejsing(fields);
      out += concept.rimTrejsing(fields);
      out += concept.predmTrejsing(fields);
//    }
    }
    if (!UDC) {    //nije udc listic
      String u = concept.brojUDC(fields);
      out += /*(u.equals("") ? "" : "\n\n") + */u;
    }
    out += (out.equals("") ? "ID=" : (concept.brojID() ? "\nID=" : "\n\nID=" ) + docID);
    return out;
  }
  
  private String base1() {
    Concept concept = new Concept();

    String out = new String("");
    out += concept.odrednica(fields)+"\n";// dodato /n za prazan red izmedju odrednice iglavnog opisa tako hoce u NS
    
    out += concept.zaglavlje(fields);
    //out += concept.alone532(fields);NS nece da se vidi ovo pravilo
    out += concept.glavniOpis(fields);
    
    out += concept.napomene(fields);
    out += concept.prilozi(fields);
    if (!stampa)
      out+="\n";  
    String isbn=new String();
    isbn=concept.brojISBNStampa(fields);
    out +=isbn +"\n";
    
    if (!stampa){      
      if (!isbn.equals(""))
        out+="\n";  
      out+="P.O.:" + "&nbsp;" + concept.po(fields);      
      out +="\n"+"UDK="+"&nbsp;"+ concept.brojUDCOsnovniListic(fields);
    }
    return out;
  }
  

  /** Metoda getGjklm formira skup glavnih listica gjklm ne osnovu jednog zapisa
      Jedna signatura formira jedan listic.  996f bez d (inventarni broj bez signature)
      pise se na kraju listica sa prethodnim 996d. Ako takvog nema - ispisuje se greska na mestu signature.
      one - jedan listic sa jednom signaturom
      @return skup glavnih listica gjklm ne osnovu jednog zapisa
  */
  public String getGjklm() {
    String gjklm = new String("");
    String one = new String("");
    Concept concept = new Concept();
    ReportFields rf = new ReportFields();
    Vector v = new Vector();
    String signatura = new String("");
    String inventar = new String("");
    String sign="";
    v = concept.getField(fields, "996", " ", " ");
    for(int i = 0; i < v.size(); i++) {
      sign = rf.getFieldContent996((Field)v.elementAt(i),"d");
      String inv = rf.getFieldContent996((Field)v.elementAt(i),"f");
      if (!sign.equals("")) {
        if (signatura.equals("")) {  //prvi ili odstampan
          signatura = rightAlign(sign) + "\n";
          inventar += (i == 0 ? "\n" : "") + rightAlign(inv) + "\n";
       }
       else  { // postoji stara ali i nova
          one = signatura + base() +/* "\n" + */inventar; //generisemo listic za staru signaturu
          strana = 1;
          gjklm += formatIzlaz(one, signatura, "");
          gjklm = doKrajaStrane(gjklm);
          inventar = (i == 0 ? "\n" : "") + rightAlign(inv) + "\n";  //pamtimo novo stanje
          signatura = rightAlign(sign) + "\n";
        }
        if (i == v.size()-1) { // zadnji
         one = signatura + base() + /*"\n" + */inventar;
         strana = 1;
         gjklm += formatIzlaz(one, signatura, "");
         gjklm = doKrajaStrane(gjklm);
         inventar = "";
         signatura = "";
       }
      }
      else {
        inventar += (i == 0 ? "\n" : "") + rightAlign(inv) + "\n";
        if (i == v.size()-1) {
          one = (signatura.equals("")? com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOCALLNUMBER") : signatura) + base() +/* "\n" +*/ inventar;
          strana = 1;
          gjklm += formatIzlaz(one, signatura, "");
          gjklm = doKrajaStrane(gjklm);
          inventar = "";
          signatura = "";
        }
      }
    }
    if (v.size() == 0) {
      String baza = base();
      if (!baza.equals(""))  {
        one = rightAlign(com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOCALLNUMBER")) + base() + "\n" + rightAlign(com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOINVNUM"));
        strana = 1;
        gjklm += formatIzlaz(one, rightAlign(com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOCALLNUMBER")), "");
        gjklm = doKrajaStrane(gjklm);
      }
    }
    return gjklm;
  }

  
  
  

  
  
  public String zaStampu() {
    String zaStamp = new String("");
    String one = new String("");
    Concept concept = new Concept();
    ReportFields rf = new ReportFields();
    Vector v = new Vector();
    String signatura = new String("");
    String sign="";
    stampa=true;
    UDC=true;// posto UDC broj nije potreban ako stavimo da je true base() ga nece odstampati 
    
    v = concept.getField(fields, "996", " ", " ");
    for(int i = 0; i < v.size(); i++) {
      String stara=sign;
      sign = rf.getFieldContent996I((Field)v.elementAt(i),"d");
      
      if (!sign.equals("")) {
        //if (signatura.equals("")&& !(stara==sign)) {  //prvi ili odstampan
          signatura =   sign + "\n";
          
       //}
       //else if(!(stara==sign)) { // postoji stara ali i nova
         // one = signatura + base1() /* "\n" + */; //generisemo listic za staru signaturu
         // strana = 1;
         // one=formatRed(one);
         // zaStamp+= formatStrana(one, signatura);
          
         // zaStamp = doKrajaStrane(zaStamp);
        
        //  signatura =  sign + "\n";
       // }
        if (i == v.size()-1) { // zadnji
         one = signatura + base1();// + /*"\n" + */inventar;
         strana = 1;
         one=formatRed(one);
         zaStamp+= formatStrana(one, signatura);
         zaStamp = doKrajaStrane(zaStamp);
        
         signatura = "";
       }
      }
      else {
       
        if (i == v.size()-1) {
          one = (signatura.equals("")? com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOCALLNUMBER") : signatura) + base1() /* "\n" +*/ ;
          strana = 1;
          one=formatRed(one);
          zaStamp+= formatStrana(one, signatura);
          //zaStamp = doKrajaStrane(zaStamp);
         
          signatura = "";
        }
      }
    }
    if (v.size() == 0) {
      String baza = base1();
      if (!baza.equals(""))  {
        one = rightAlign(com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOCALLNUMBER")) + base1() + "\n" + rightAlign(com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOINVNUM"));
        strana = 1;
        one=formatRed(one);
        zaStamp += formatStrana(one, rightAlign(com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOCALLNUMBER")));
       // zaStamp = doKrajaStrane(zaStamp);
      }
    }
    //zaStamp = doKrajaStrane(zaStamp);
    return zaStamp;
  } 
  
  
  public String osnovniListic() {
    String osnovni = new String("");
    String one = new String("");
    Concept concept = new Concept();
    ReportFields rf = new ReportFields();
    Vector v = new Vector();
    String signatura = new String("");
    String inventar = new String("");
    Signature s= new Signature();
    int pom=0;
    boolean prvi=true;
    boolean prviSigla=true;
    int [] nizSigli=new int[31];
    boolean prazan=false;
    v = concept.getField(fields, "996", " ", " ");
    for(int i = 0; i < v.size(); i++) {
      String sign = rf.getFieldContent996I((Field)v.elementAt(i),"d");
       
       String inv = rf.getFieldContent996I((Field)v.elementAt(i),"f");
       if (inv.equals("")){prazan=true;}
       if(!prazan){
       pom=getTypeInv(inv);//odredjuje se sigla
       nizSigli[pom]++;// povecavamo broj za odgovarajucu siglu
       }
      if (!sign.equals("")) {
      
       // if (signatura.equals("")) {  //prvi ili odstampan
          signatura =  sign + "\n";
          if(!prazan){
         if (prvi){
             prvi=false;
             inventar =inv ;
          }
          else{
            inventar += "," +" " +inv ;
          }
          }
      // }
       //else  { // postoji stara ali i nova
       //   one = signatura + base1() + "\n"+/* "\n" + */inventar; //generisemo listic za staru signaturu
          
       //   for(int j=0; j<31;j++){
        //  if (nizSigli[j]!=0){
        //     if (prviSigla){
        //      prviSigla=false;
        //      one+="\n"+ String.valueOf(j)+ "-"+String.valueOf(nizSigli[j]);
        //    }
        //    else{
        //      one+=";"+ " "+String.valueOf(j)+ "-"+String.valueOf(nizSigli[j]); 
        //    }
        //  }   
    //    }
    //      prviSigla=true;
    //      one+="\n";
    //      strana = 1;
         
    //      osnovni= formatRed1(one);
          
     //       prvi=false;
      //      inventar = inv ;
          
     //     for(int j=0; j<31;j++)
     //     nizSigli[j]=0;
         
     //     signatura =  sign + "\n";
       // }
        if (i == v.size()-1) { // zadnji
         one = signatura + base1() + /*"\n" + */"\n"+inventar;
         if(!prazan){
         for(int j=0; j<31;j++){
          if (nizSigli[j]!=0){
             if (prviSigla){
              prviSigla=false;
              one+="\n"+ String.valueOf(j)+ "-"+String.valueOf(nizSigli[j]);
          }
          else{
            one+=";"+ " "+String.valueOf(j)+ "-"+String.valueOf(nizSigli[j]); 
          }
        }   
       }
         prviSigla=true;
         }
         one+="\n";
         strana = 1;
         
         osnovni= formatRed1(one);
         
         inventar = "";
         for(int j=0; j<31;j++)
          nizSigli[j]=0;
         signatura = "";
       }
      }
      else {
        if(!prazan){
        if (prvi)
            inventar =  inv ;
         else{
          inventar +=","+ " "+ inv;
          prvi=false;
         }
        }
        if (i == v.size()-1) {
          one = (signatura.equals("")? com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOCALLNUMBER") : signatura) + base1() + "\n" + inventar;
          if(!prazan){
          for(int j=0; j<31;j++){
          if (nizSigli[j]!=0){
             if (prviSigla){
              prviSigla=false;
              one+="\n"+ String.valueOf(j)+ "-"+String.valueOf(nizSigli[j]);
            }
            else{
              one+=";"+" "+ String.valueOf(j)+ "-"+String.valueOf(nizSigli[j]); 
            }
          }   
        }
          prviSigla=true;
          }
          one+="\n";
          strana = 1;
         
          osnovni= formatRed1(one);
         
          inventar = "";
          for(int j=0; j<31;j++)
          nizSigli[j]=0;
          signatura = "";
        }
      }
    }
    osnovni+="\n";
    if (v.size() == 0) {
      String baza = base1();
      if (!baza.equals(""))  {
        one = rightAlign(com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOCALLNUMBER")) + base1() + "\n" + rightAlign(com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOINVNUM"));
        
        strana = 1;
        osnovni += formatRed1(one+ rightAlign(com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOCALLNUMBER")));
        osnovni+="<BR>\n";
        
      }
    }
    
    osnovni+="<BR>\n<BR>\n";
    return osnovni;
  } 

  
  
  /** Metoda getGjklmFF formira skup glavnih listica gjklm ne osnovu jednog zapisa
      Ako ima vise signatura od 3 na svakom listicu se ispisuju najvise tri signature (odnosno 996)
      ZA FILOZOFSKI FAKULTET  I PMF
      one - jedan listic sa jednom signaturom
      @return skup glavnih listica gjklm ne osnovu jednog zapisa
  */
  public String getGjklmFF() {
    String gjklm = new String("");
    String one = new String("");
    String sign = new String("");
    Concept concept = new Concept();
    ReportFields rf = new ReportFields();
    Vector v = new Vector();


    v = concept.getField(fields, "996", " ", " ");
    int numL = v.size() % 3;
    int num = (numL == 0) ? (int)v.size()/3 : (int)v.size()/3 + 1;
    int k = 0;
    for (int i = 0; i < num; i++ ){
       int range = (i == num - 1 && numL != 0) ? numL : 3;
       for (int ii = 0; ii < range; ii++) {
         String signatura = rf.getFieldContent996((Field)v.elementAt(ii+k),"d");
         if (!signatura.equals("")) {
            one += rightAlign(signatura) + "\n";
            sign += signatura + "\n";
         }
       }
       one += base();
       for (int ii = 0; ii < range; ii++) {
          String inventar = rf.getFieldContent996((Field)v.elementAt(ii+k),"f");
          if (!inventar.equals(""))
            one += (ii == 0 ? "\n" : "") + rightAlign(inventar) + (ii == range-1 ? "" : "\n");
       }
       strana = 1;
       gjklm += formatIzlazFF(one, sign, "");
       gjklm = doKrajaStrane(gjklm);
       one = "";
       sign = "";
       k = k + 3;
    }
    return gjklm;
  }



 /** Dodaje sporednu odrednicu sporednom listicu posle signature
 */
  private String getSporedniListic(String sListic, String sOdrednica/*, boolean signSet*/) {
    Concept concept = new Concept();
    String listic = new String("");
    ReportFields rf = new ReportFields();
    String signatura = new String("");
    String inventar = new String("");


    Vector v = concept.getField(fields, "996", " ", " ");
    if (!signSet)  {
       for (int i = 0; i < v.size(); i++) {
         String sign = rf.getFieldContent996((Field)v.elementAt(i),"d");
         String inv = rf.getFieldContent996((Field)v.elementAt(i),"f");
         if (!sign.equals("")) {
           if (signatura.equals("")) {  //prvi ili odstampan
             signatura = rightAlign(sign) + "\n";
             inventar += (i == 0 ? "\n" : "") + rightAlign(inv) + "\n";
           }
           else  { // postoji stara ali i nova
            sListic = signatura; //generisemo listic za staru signaturu
            sListic += "\n" + sOdrednica + "\n";
            sListic += base()+ "\n";
            sListic += inventar;
            strana = 1;
            listic += formatIzlaz(sListic, signatura, sOdrednica);
            listic = doKrajaStrane(listic);
            inventar = (i == 0 ? "\n" : "") + rightAlign(inv) + "\n";  //pamtimo novo stanje
            signatura = rightAlign(sign) + "\n";
          }
          if (i == v.size()-1) { // zadnji
            sListic = signatura;
            sListic += "\n" + sOdrednica + "\n";
            sListic += base()+ "\n";
            sListic += inventar;
            strana = 1;
            listic += formatIzlaz(sListic, signatura, sOdrednica);
            listic = doKrajaStrane(listic);
            inventar = "";
            signatura = "";
          }
        }
        else {
          inventar += (i == 0 ? "\n" : "") + rightAlign(inv) + "\n";
          if (i == v.size()-1 || i == 0) {
            sListic = (signatura.equals("")? "GRE\u0160KA: Nije uneta signatura\n" : signatura);
            sListic += "\n" + sOdrednica + "\n";
            sListic += base()+ "\n";
            sListic += inventar;
            strana = 1;
            listic += formatIzlaz(sListic, signatura, sOdrednica);
            listic = doKrajaStrane(listic);
            inventar = "";
            signatura = "";
          }
        }
      }
      if (v.size() == 0) {
        sListic = rightAlign(com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOCALLNUMBER")) + "\n" + sOdrednica + "\n" + base() + "\n" + rightAlign(com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOINVNUM"));
        strana = 1;
        listic += formatIzlaz(sListic, rightAlign(com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_NOCALLNUMBER")), sOdrednica);
        listic = doKrajaStrane(listic);
      }
    }
    else {
      String sign = new String("");
      int numL = v.size() % 3;
      int num = (numL == 0) ? (int)v.size()/3 : (int)v.size()/3 + 1;
      int k = 0;
      for (int i = 0; i < num; i++ ){
        int range = (i == num - 1 && numL != 0) ? numL : 3;
        for (int ii = 0; ii < range; ii++) {
          signatura = rf.getFieldContent996((Field)v.elementAt(ii+k),"d");
          if (!signatura.equals("")) {
            sListic += rightAlign(signatura) + "\n";
            sign += signatura + "\n";
          }
        }
        sListic += "\n" + sOdrednica + "\n";
        sListic += base();
        for (int ii = 0; ii < range; ii++) {
           inventar = rf.getFieldContent996((Field)v.elementAt(ii+k),"f");
           if (!inventar.equals(""))
             sListic += (ii == 0 ? "\n" : "") + rightAlign(inventar) + "\n";
        }
        strana = 1;
        listic += formatIzlazFF(sListic, sign, sOdrednica );
        listic = doKrajaStrane(listic);
        sListic = "";
        sign = "";
        k = k + 3;
     }
    }
    return listic;
  }



  /** Formira UDK listic
  */
  public String getUDCListic() {
    String UDClist = new String("");
    Concept concept = new Concept();
    ReportFields rf = new ReportFields();

    Vector v = concept.getField(fields, "675", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      UDC = true;
      String UDCbroj = rf.getFieldContent010((Field)v.elementAt(i),"a");
      if (!UDCbroj.equals("")) {
        UDClist += getSporedniListic("", ReportUtilities.toBold(ReportUtilities.toUpper(UDCbroj,1), -1));//, signSet);
      }
    }
    UDC = false;
    return UDClist;
  }


  /** Formira predmetni listic
  */
  public String getPredmListic() {
    ReportFields rf = new ReportFields();
    String predmList = new String("");
    String po = new String(""); //predmetna odrednica
    Vector v = new Vector();
    Concept concept = new Concept();


    v = concept.getFieldMore(fields, "600", "13", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(po = rf.getFieldContent600((Field)v.elementAt(i),"@")).equals(""))
          predmList += getSporedniListic("",ReportUtilities.toBold(ReportUtilities.toUpper(po,1), -1));//, signSet);
    }

    v = concept.getField(fields, "601", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(po = rf.getFieldContent601((Field)v.elementAt(i),"@")).equals(""))
          predmList += getSporedniListic("",ReportUtilities.toBold(ReportUtilities.toUpper(po,1), -1));//, signSet);
    }

    v = concept.getFieldMore(fields, "602", "13", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(po = rf.getFieldContent602((Field)v.elementAt(i),"@")).equals(""))
          predmList += getSporedniListic("",ReportUtilities.toBold(ReportUtilities.toUpper(po,1), -1));//, signSet);
    }

    v = concept.getFieldMore(fields, "605", "13", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(po = rf.getFieldContent605((Field)v.elementAt(i),"@")).equals(""))
          predmList += getSporedniListic("",ReportUtilities.toBold(ReportUtilities.toUpper(po,1), -1));//, signSet);
    }

    v = concept.getFieldMore(fields, "606", "13", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(po = rf.getFieldContent606((Field)v.elementAt(i),"@")).equals(""))
          predmList += getSporedniListic("",ReportUtilities.toBold(ReportUtilities.toUpper(po,1), -1));//, signSet);
    }

    v = concept.getFieldMore(fields, "607", "13", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(po = rf.getFieldContent606((Field)v.elementAt(i),"@")).equals(""))
          predmList += getSporedniListic("",ReportUtilities.toBold(ReportUtilities.toUpper(po,1), -1));//, signSet);
    }

    v = concept.getFieldMore(fields, "608", "13", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(po = rf.getFieldContent606((Field)v.elementAt(i),"@")).equals(""))
          predmList += getSporedniListic("",ReportUtilities.toBold(ReportUtilities.toUpper(po,1), -1));//, signSet);
    }

    v = concept.getFieldMore(fields, "609", "13", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(po = rf.getFieldContent606((Field)v.elementAt(i),"@")).equals(""))
          predmList += getSporedniListic("",ReportUtilities.toBold(ReportUtilities.toUpper(po,1), -1));//, signSet);
    }
    return predmList;
  }


  /** Formira autorski listic
  */
  public String getAutorListic() {
    String autorList = new String("");
    ReportFields rf = new ReportFields();
    String val = new String("");
    Vector v = new Vector();
    Concept concept = new Concept();
    int br = 1;

    v = concept.getField(fields, "701", "1", " ");
    for (int i=0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent700((Field)v.elementAt(i),"abcdf")).equals("")) {
        autorList += getSporedniListic("", ReportUtilities.toBold(ReportUtilities.toUpper(val,1), -1));//, signSet);
      }
    }

    v = concept.getField(fields, "702", "1", " ");
    for (int i=0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent700((Field)v.elementAt(i),"abcdf")).equals("")) {
        autorList += getSporedniListic("", ReportUtilities.toBold(ReportUtilities.toUpper(val,1), -1));//, signSet);
      }
    }

    v = concept.getField(fields, "711", " ", " ");
    for (int i=0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent710((Field)v.elementAt(i),"abcdefgh")).equals("")) {
        autorList += getSporedniListic("", ReportUtilities.toBold(ReportUtilities.toUpper(val,1), -1));//, signSet);
      }
    }

    v = concept.getField(fields, "712", " ", " ");
    for (int i=0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent710((Field)v.elementAt(i),"abcdefgh")).equals("")) {
        autorList += getSporedniListic("", ReportUtilities.toBold(ReportUtilities.toUpper(val,1), -1));//, signSet);
      }
    }

    v = concept.getField(fields, "721", " ", " ");
    for (int i=0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent720((Field)v.elementAt(i),"af")).equals("")) {
        autorList += getSporedniListic("", ReportUtilities.toBold(ReportUtilities.toUpper(val,1), -1));//, signSet);
      }
    }

    v = concept.getField(fields, "722", " ", " ");
    for (int i=0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent720((Field)v.elementAt(i),"af")).equals("")) {
        autorList += getSporedniListic("", ReportUtilities.toBold(ReportUtilities.toUpper(val,1), -1));//, signSet);
      }
    }

    v = concept.getField(fields, "423", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      Vector v1 = rf.getFieldContent423Analitika((Field)v.elementAt(i),"@");
      for (int ii = 0; ii < v1.size(); ii++) {
        autorList += getSporedniListic("", ReportUtilities.toBold(ReportUtilities.toUpper((String)v1.elementAt(ii),1), -1));//, signSet);
      }
    }

    return autorList;
  }


  /** Formira uputni listic
  */
  public String getUputniListic() {
    String uputniL = new String("");
    String one = new String("");
    ReportFields rf = new ReportFields();
    Concept concept = new Concept();
    Vector v = new Vector();
    Vector v1 = new Vector();
    String val = new String("");
    String val1 = new String("");
    boolean first = true;

    br = 1; bk = 1;

    v = concept.getField(fields, "900", " ", " ");
    v1 = concept.getField(fields, "700", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent700((Field)v.elementAt(i),"@")).equals("") && !(val1 = rf.getFieldContent700((Field)v1.elementAt(i),"@")).equals("")) {
        one = formatIzlaz(val + "\n" + "VIDI" + "\n" + val1,"","");
        uputniL += verticalAlign(one);
      }
    }

    v = concept.getField(fields, "901", "1", " ");
    v1 = concept.getField(fields, "701", "1", " ");
    for (int i = 0; i < v.size(); i++) {
      for (int j = 0;j < v1.size(); j++)  {
        if (!(val = rf.getFieldContent700((Field)v.elementAt(i),"@")).equals("") && !(val1 = rf.getFieldContent700((Field)v1.elementAt(j),"@")).equals("")) {
          String val9016 = concept.getSFContent((Field)v.elementAt(i),"6");
          String val7016 = concept.getSFContent((Field)v1.elementAt(j),"6");
          if ((!val9016.equals("") && val9016.equals(val7016)) || (v.size() == 1 && v1.size() == 1)) {
            one = formatIzlaz(val + "\n" + "VIDI" + "\n" + val1,"","");
            uputniL += verticalAlign(one);
          }
        }
      }
    }


    v = concept.getField(fields, "902", "1", " ");
    v1 = concept.getField(fields, "702", "1", " ");
    for (int i = 0; i < v.size(); i++) {
      for (int j = 0;j < v1.size(); j++)  {
        if (!(val = rf.getFieldContent700((Field)v.elementAt(i),"@")).equals("") && !(val1 = rf.getFieldContent700((Field)v1.elementAt(j),"@")).equals("")) {
          String val9026 = concept.getSFContent((Field)v.elementAt(i),"6");
          String val7026 = concept.getSFContent((Field)v1.elementAt(j),"6");
          if ((!val9026.equals("") && val9026.equals(val7026)) || (v.size() == 1 && v1.size() == 1)) {
            one = formatIzlaz(val + "\n" + "VIDI" + "\n" + val1,"","");
            uputniL += verticalAlign(one);
          }
        }
      }
    }


    // po savetu FF samo a
    v = concept.getField(fields, "910", " ", " ");
    v1 = concept.getField(fields, "710", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      if (!(val = rf.getFieldContent710((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent710((Field)v1.elementAt(i),"a")).equals("")) {
        one = formatIzlaz(val + "\n" + "VIDI" + "\n" + val1,"","");
        uputniL += verticalAlign(one);
      }
    }

    // po savetu FF samo a
    v = concept.getField(fields, "911", "1", " ");
    v1 = concept.getField(fields, "711", "1", " ");
    for (int i = 0; i < v.size(); i++) {
      for (int j = 0;j < v1.size(); j++)  {
        if (!(val = rf.getFieldContent710((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent710((Field)v1.elementAt(j),"a")).equals("")) {
          String val9116 = concept.getSFContent((Field)v.elementAt(i),"6");
          String val7116 = concept.getSFContent((Field)v1.elementAt(j),"6");
          if ((!val9116.equals("") && val9116.equals(val7116)) || (v.size() == 1 && v1.size() == 1)) {
            one = formatIzlaz(val + "\n" + "VIDI" + "\n" + val1,"","");
            uputniL += verticalAlign(one);
          }
        }
      }
    }

    // po savetu FF samo a
    v = concept.getField(fields, "912", "1", " ");
    v1 = concept.getField(fields, "712", "1", " ");
    for (int i = 0; i < v.size(); i++) {
      for (int j = 0;j < v1.size(); j++)  {
        if (!(val = rf.getFieldContent710((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent710((Field)v1.elementAt(j),"a")).equals("")) {
          String val9126 = concept.getSFContent((Field)v.elementAt(i),"6");
          String val7126 = concept.getSFContent((Field)v1.elementAt(j),"6");
          if ((!val9126.equals("") && val9126.equals(val7126)) || (v.size() == 1 && v1.size() == 1)) {
            one = formatIzlaz(val + "\n" + "VIDI" + "\n" + val1,"","");
            uputniL += verticalAlign(one);
          }
        }
      }
    }

    v = concept.getFieldMore(fields, "960", "13", " ");
    v1 = concept.getFieldMore(fields, "600", "13", " ");
    for (int i = 0; i < v.size(); i++) {
      for (int j = 0;j < v1.size(); j++)  {
        if (!(val = rf.getFieldContent600((Field)v.elementAt(i),"@")).equals("") && !(val1 = rf.getFieldContent600((Field)v1.elementAt(j),"@")).equals("")) {
          String val9606 = concept.getSFContent((Field)v.elementAt(i),"6");
          String val6006 = concept.getSFContent((Field)v1.elementAt(j),"6");
          if ((!val9606.equals("") && val9606.equals(val6006)) || (v.size() == 1 && v1.size() == 1)) {
            one = formatIzlaz(val + "\n" + "VIDI" + "\n" + val1,"","");
            uputniL += verticalAlign(one);
          }
        }
      }
    }

    // po savetu FF samo a
    v = concept.getField(fields, "961", " ", " ");
    v1 = concept.getField(fields, "601", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      for (int j = 0;j < v1.size(); j++)  {
        if (!(val = rf.getFieldContent601((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent601((Field)v1.elementAt(j),"a")).equals("")) {
          String val9616 = concept.getSFContent((Field)v.elementAt(i),"6");
          String val6016 = concept.getSFContent((Field)v1.elementAt(j),"6");
          if ((!val9616.equals("") && val9616.equals(val6016)) || (v.size() == 1 && v1.size() == 1)) {
            one = formatIzlaz(val + "\n" + "VIDI" + "\n" + val1,"","");
            uputniL += verticalAlign(one);
          }
        }
      }
    }

    // po savetu FF samo a
    v = concept.getFieldMore(fields, "962", "13", " ");
    v1 = concept.getFieldMore(fields, "602", "13", " ");
    for (int i = 0; i < v.size(); i++) {
      for (int j = 0;j < v1.size(); j++)  {
        if (!(val = rf.getFieldContent602((Field)v.elementAt(i),"af")).equals("") && !(val1 = rf.getFieldContent602((Field)v1.elementAt(j),"af")).equals("")) {
          String val9626 = concept.getSFContent((Field)v.elementAt(i),"6");
          String val6026 = concept.getSFContent((Field)v1.elementAt(j),"6");
          if ((!val9626.equals("") && val9626.equals(val6026)) || (v.size() == 1 && v1.size() == 1)) {
            one = formatIzlaz(val + "\n" + "VIDI" + "\n" + val1,"","");
            uputniL += verticalAlign(one);
          }
        }
      }
    }

    //??
    v = concept.getFieldMore(fields, "965", "13", " ");
    v1 = concept.getFieldMore(fields, "605", "13", " ");
    for (int i = 0; i < v.size(); i++) {
      for (int j = 0;j < v1.size(); j++)  {
        if (!(val = rf.getFieldContent605((Field)v.elementAt(i),"@")).equals("") && !(val1 = rf.getFieldContent605((Field)v1.elementAt(j),"@")).equals("")) {
          String val9656 = concept.getSFContent((Field)v.elementAt(i),"6");
          String val6056 = concept.getSFContent((Field)v1.elementAt(j),"6");
          if ((!val9656.equals("") && val9656.equals(val6056)) || (v.size() == 1 && v1.size() == 1)) {
            one = formatIzlaz(val + "\n" + "VIDI" + "\n" + val1,"","");
            uputniL += verticalAlign(one);
          }
        }
      }
    }

    // po savetu FF samo a
    v = concept.getFieldMore(fields, "966", "13", " ");
    v1 = concept.getFieldMore(fields, "606", "13", " ");
    for (int i = 0; i < v.size(); i++) {
      for (int j = 0;j < v1.size(); j++)  {
        if (!(val = rf.getFieldContent606((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent606((Field)v1.elementAt(j),"a")).equals("")) {
          String val9666 = concept.getSFContent((Field)v.elementAt(i),"6");
          String val6066 = concept.getSFContent((Field)v1.elementAt(j),"6");
          if ((!val9666.equals("") && val9666.equals(val6066)) || (v.size() == 1 && v1.size() == 1)) {
            one = formatIzlaz(val + "\n" + "VIDI" + "\n" + val1,"","");
            uputniL += verticalAlign(one);
          }
        }
      }
    }

    // po savetu FF samo a
    v = concept.getFieldMore(fields, "967", "13", " ");
    v1 = concept.getFieldMore(fields, "607", "13", " ");
    for (int i = 0; i < v.size(); i++) {
      for (int j = 0;j < v1.size(); j++)  {
        if (!(val = rf.getFieldContent606((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent606((Field)v1.elementAt(j),"a")).equals("")) {
          String val9676 = concept.getSFContent((Field)v.elementAt(i),"6");
          String val6076 = concept.getSFContent((Field)v1.elementAt(j),"6");
          if ((!val9676.equals("") && val9676.equals(val6076)) || (v.size() == 1 && v1.size() == 1)) {
            one = formatIzlaz(val + "\n" + "VIDI" + "\n" + val1,"","");
            uputniL += verticalAlign(one);
          }
        }
      }
    }

    // po savetu FF samo a
    v = concept.getFieldMore(fields, "968", "13", " ");
    v1 = concept.getFieldMore(fields, "608", "13", " ");
    for (int i = 0; i < v.size(); i++) {
      for (int j = 0;j < v1.size(); j++)  {
        if (!(val = rf.getFieldContent606((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent606((Field)v1.elementAt(j),"a")).equals("")) {
          String val9686 = concept.getSFContent((Field)v.elementAt(i),"6");
          String val6086 = concept.getSFContent((Field)v1.elementAt(j),"6");
          if ((!val9686.equals("") && val9686.equals(val6086)) || (v.size() == 1 && v1.size() == 1)) {
            one = formatIzlaz(val + "\n" + "VIDI" + "\n" + val1,"","");
            uputniL += verticalAlign(one);
          }
        }
      }
    }

    // po savetu FF samo a
    v = concept.getFieldMore(fields, "969", "13", " ");
    v1 = concept.getFieldMore(fields, "609", "13", " ");
    for (int i = 0; i < v.size(); i++) {
      for (int j = 0;j < v1.size(); j++)  {
        if (!(val = rf.getFieldContent606((Field)v.elementAt(i),"a")).equals("") && !(val1 = rf.getFieldContent606((Field)v1.elementAt(j),"a")).equals("")) {
          String val9696 = concept.getSFContent((Field)v.elementAt(i),"6");
          String val6096 = concept.getSFContent((Field)v1.elementAt(j),"6");
          if ((!val9696.equals("") && val9696.equals(val6096)) || (v.size() == 1 && v1.size() == 1)) {
            one = formatIzlaz(val + "\n" + "VIDI" + "\n" + val1,"","");
            uputniL += verticalAlign(one);
          }
        }
      }
    }
    return uputniL;
  }


  /** Formira skup listica za monografije - glavni, UDK, predmetni, autorski
  */
  public String getSkupListicaM() {
    String skup = new String("");

    if (!signSet)
      skup += getGjklm();
    else
      skup += getGjklmFF();
    skup += getUDCListic();
    skup += getPredmListic();
    skup += getAutorListic();

    return skup;
  }


  /* Formira glavni listic za serijske publikacije
  */
  public String getGjkls() {
    String gjkls = new String("");
    String one = new String("");
    Concept concept = new Concept();
    ReportFields rf = new ReportFields();

    Vector v = concept.getField(fields, "997", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      String signatura = rf.getFieldContent996((Field)v.elementAt(i),"d");
      if (!signatura.equals("")) {
        one = rightAlign(signatura) + "\n";
        one += concept.odrednica(fields);
        one += concept.glavniOpis(fields);
        one += concept.napomeneSer(fields);
        one += concept.brojISSN(fields);
        if (serijskaF)
          one += "\n" + concept.brojUDC(fields);
        if (!serijskaF)
          one += concept.specGodista(fields)+"\n";
      }
      String inventar = rf.getFieldContent996((Field)v.elementAt(i),"f");
      if (!inventar.equals(""))
        one += (i == 0 ? "\n" : "") + rightAlign(inventar) + "\n";
      strana = 1;  // dodala TanjaT, 06.02.01.
      gjkls += formatIzlaz(one, signatura, "");
      gjkls = doKrajaStrane(gjkls);
    }
    return gjkls;
  }


  /* Formira glavni listic za serijske publikacije za FF
  */

  public String getGjklsFF() {
    String gjkls = new String("");
    String one = new String("");
    String sign = new String("");
    Concept concept = new Concept();
    ReportFields rf = new ReportFields();

    Vector v = concept.getField(fields, "997", " ", " ");
    int numL = v.size() % 3;
    int num = (numL == 0) ? (int)v.size()/3 : (int)v.size()/3 + 1;
    int k = 0;
    for (int i = 0; i < num; i++ ){
       int range = (i == num - 1 && numL != 0) ? numL : 3;
       for (int ii = 0; ii < range; ii++) {
         String signatura = rf.getFieldContent996((Field)v.elementAt(ii+k),"d");
         if (!signatura.equals("")) {
            one += rightAlign(signatura) + "\n";
            sign += signatura + "\n";
         }
       }
       one += concept.odrednica(fields);
       one += concept.glavniOpis(fields);
       one += concept.napomeneSer(fields);
       one += concept.brojISSN(fields);
       if (serijskaF)
          one += "\n" + concept.brojUDC(fields);
       if (!serijskaF)
           one += concept.specGodista(fields)+"\n";
       for (int ii = 0; ii < range; ii++) {
          String inventar = rf.getFieldContent996((Field)v.elementAt(ii+k),"f");
          if (!inventar.equals(""))
            one += (ii == 0 ? "\n" : "") + rightAlign(inventar) + "\n";
       }
       strana = 1;
       gjkls += formatIzlazFF(one, sign, "");
       gjkls = doKrajaStrane(gjkls);
       one = "";
       sign = "";
       k = k + 3;
    }
    return gjkls;
  }


  /* Formira predmetni listic za serijske publikacije
  */
  public String getPredmListicS() {
    String ps = new String("");
    String one = new String("");
    Concept concept = new Concept();
    ReportFields rf = new ReportFields();
    Vector v = new Vector();

    v = concept.getField(fields, "997", " ", " ");
    for (int i = 0; i < v.size(); i++) {
      String signatura = rf.getFieldContent996((Field)v.elementAt(i),"d");
      if (!signatura.equals("")) {
        one = rightAlign(signatura) + "\n";
        String u = concept.brojUDC(fields);
        one += (u.equals("") ? "" : "\n" ) + u;
        one += concept.glavniOpis(fields);
        one += concept.brojISSN(fields);
      }
      String inventar = rf.getFieldContent996((Field)v.elementAt(i),"f");
      if (!inventar.equals(""))
        one += (i == 0 ? "\n" : "") + rightAlign(inventar) + "\n";
      ps += formatIzlaz(one, signatura, "");
      ps = doKrajaStrane(ps);
    }
    return ps;
  }



  public String getArticle() {
    String art = new String("");
    String val = new String("");
    Concept concept = new Concept();
    ReportFields rf = new ReportFields();
    boolean tip = true;
    Vector v = new Vector();


    art += concept.odrednica(fields);
    v = concept.getField(fields, "200", " ", " ");
    if (v.size() > 0) {
      String opis = rf.getFieldContent200((Field)v.elementAt(0),"abcdefghi");
      if (!opis.equals("")) {
        art += "\n&nbsp;&nbsp;&nbsp;" + opis;
      }
    }

    art+="\n";
    //veza sa nadredjenom publikacijom preko ISSN tj. 011a
    if ((v = concept.getField(fields, "011", " ", " ")).size() > 0) {
      String issn = concept.getSFContent((Field)v.elementAt(0),"a");
      // izvrsiti pretazivanje baze da bi se pronasao zapis sa odgovarajucim issn-om
      // staviti taj zapis u rez; greska ako ima vise od jednog takvog zapisa
      if (!issn.equals("")) {
        String rez = Report.getDoc(issn);

        if (rez.equals(""))
          return com.gint.app.bisis.editor.Messages.get("BISISAPP_CONCEPT_SAMEISSN");
        RezanceUtilities ru = new RezanceUtilities();
        fields1 = RezanceUtilities.unpackRezance(rez);

        art += "U: " + concept.opisSerijske(fields1);
      }
      else if ((v = concept.getField(fields, "464", " ", " ")).size() > 0) {
        String id = concept.getSFContent((Field)v.elementAt(0),"1");
        if (!id.equals("")) {
          //izvadi iz baze taj zapis raspakuj ga u fields1 i pokupi opis nadredjene publ
          String rez = "";
          //try {
            rez = Environment.getReadTs().getDoc(Integer.parseInt(id));
          //} catch (java.rmi.RemoteException ex) {
          //}
          RezanceUtilities ru = new RezanceUtilities();
          fields1 = RezanceUtilities.unpackRezance(rez);
          art += "U: " + concept.opisMonografske(fields1);
        }
      }
    }
    else if ((v = concept.getField(fields, "464", " ", " ")).size() > 0) {
      String id = concept.getSFContent((Field)v.elementAt(0),"1");
      if (!id.equals("")) {
        //izvadi iz baze taj zapis raspakuj ga u fields1 i pokupi opis nadredjene publ
        String rez = "";
        //try {
          rez = Environment.getReadTs().getDoc(Integer.parseInt(id));
        //} catch (java.rmi.RemoteException ex) {
        //}
        RezanceUtilities ru = new RezanceUtilities();
        fields1 = RezanceUtilities.unpackRezance(rez);
        art += "U: " + concept.opisMonografske(fields1);
      }
    }

    if ((v = concept.getField(fields1, "000", " ", " ")).size() > 0) {
      val = rf.getFieldContent010((Field)v.elementAt(0),"a");
      val = val.substring(0,3);
      if (val.equals("001") || val.equals("002") || val.equals("003")) {
         tip = true; // monografske publikacije
      }
      else if (val.equals("004") || val.equals("005")) {
         tip = false; // serijske publikacije
      }
      else {
         tip = true; // inace mada se ovo mozda moze tretirati kao greska
      }
    }


    String godina = "";
    if (!tip) {
      if ((v = concept.getField(fields, "210", " ", " ")).size() > 0) {
        if (!(val = rf.getFieldContent210((Field)v.elementAt(0),"d")).equals("")){
          godina = " (" + val + ")";
        }
      }
    }
    if ((v = concept.getField(fields, "215", " ", " ")).size() > 0) {
      if (!tip)  { // serijska

        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"i")).equals("")){
          art += (art.endsWith(" ")? "" : " ") + val;
        }
        art += godina;

        if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"h")).equals("")){
          art += val;
        }
      }
      if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"g")).equals("")){
        art += (art.endsWith(" ")? "" : " ") + val;
      }
      if (!(val = rf.getFieldContent215((Field)v.elementAt(0),"ac")).equals("")){
        art = concept.addIntField(art) + val;
      }
    }

    art += concept.napomene(fields);
    art += concept.brojUDC(fields);
    art += (art.equals("") ? "ID=" : (concept.brojID() ? "\nID=" : "\n\nID=" ) + docID);

    art = formatIzlaz(art, "", "");
    art = doKrajaStrane(art);

    return art;


  }



  public void setSignSet(boolean val) {
    signSet = val;
    return;
  }

  public void setDoktorska(boolean val) {
    doktorska = val;
    return;
  }

  public void setSerijskaF(boolean val) {
    serijskaF = val;
    return;
  }


  public void setGlavni(boolean val) {
    glavni = val;
    return;
  }

  public void setNoTes(boolean val) {
    noTes = val;
    return;
  }

/**dodaje prazno iza stringa*/
  public String dodajPrazno(String in, int duz){
    String out=in;
    for (int i=in.length();i<duz;i++){
      out+="&nbsp;";
    }
        
    return out;
  }
  /**dodaje br znakova prazno*/
  public String prazno(int br){
    String out=new String();
    for (int i=0;i<br;i++)
      out+="&nbsp;";
    return out;
  }
  /** vrsi formatiranje reda za getInventarni, ako sve ne moze da stane u jedan red prebacuje u naredni i 
   * upisuje na pocetak narednog reda znakove prazno tako da bude poravnat sa pocetkom prethodnog reda */
  public String formatirajRed(String in){
    String out=new String();
    
    int bk=Report.ravnanje;
    
      
      if (!(in.length()+Report.ravnanje<Report.maxKol))
      for (int i=0; i < in.length(); i++)  {
        
         
          if (in.substring(i,i+1).equals(" ")) {
            if (bk == Report.maxKol) {
             out += "<BR>\n";
             br++; bk = Report.ravnanje;
             for(int p=0;p<Report.ravnanje;p++){
              out +="&nbsp;";
             }
            }
            else {
              int n = i+1;
             
              while (n < in.length() && !in.substring(n,n+1).equals(" ") && !in.substring(n,n+1).equals("\n"))
                n++;
              if ((n-i-1) + bk > Report.maxKol-1) {  // rec je duza od duzine reda
                
                out += "<BR>\n"; 
                br++; bk = Report.ravnanje;
                for(int p=0;p<Report.ravnanje;p++){
                  out +="&nbsp;";
                 }
                
              }
              
              else {
                out += in.substring(i,i+1);
                bk++;
              }
            }
            
          }
          else  {
            if (in.substring(i,i+1).equals("<")) {  //treba preskociti pri brojanju
              if (in.substring(i,i+3).equals("<B>")) {
                 out += in.substring(i,i+3);
                 i = i+2;
              }
              else if (in.substring(i,i+4).equals("</B>"))  {
                out += in.substring(i,i+4);
                i = i+3;
              }
            }
            else
              if (in.substring(i,i+1).equals("&")) {
                int pos = in.indexOf(';', i);
                out += in.substring(i,pos+1);
                i = pos;
                bk++;
              }
              else {
                bk++;
                out += in.substring(i,i+1);
              }
          }
      }
      else
        out=in;
           
    
    
    return out;
  }

  public String getInventarni(){
    String listic="";
    String one = new String("");
    String sign = new String("");
    String signatura="";
    Concept concept = new Concept();
    ReportFields rf = new ReportFields();
    Vector v = new Vector();
    String  ime=new String("");
    String to=new String("");
    String  odg=new String("");
    String inv=new String("");
    String dat=new String("");
    String vr=new String("");
    String nab=new String("");
    String cena=new String("");
    String napomena=new String("");
    String status=new String("");
    String lok=new String();
 
    
    listic+= "Broj zapisa (MFN)=" + docID +"<BR>\n"+"<BR>\n";
    
    listic+="&nbsp;" + "Obra\u0111iva\u010D i datum:" + prazno(5);
     to=concept.toNS(v);
  String datum=concept.datumNS(v);
   listic+=to + (to.equals("")&& datum.equals("") ? "" : "-")+ datum  + "<BR>\n";
      
    listic+="&nbsp;" +"Autori:"+prazno(17);
    
    if ((v = concept.getField(fields, "700", " ", " ")).size() > 0) 
        ime= rf.getFieldContent700((Field)v.elementAt(0),"ab");
      listic+= formatirajRed(ime) + "<BR>\n";
    
    v = concept.getField(fields, "200", " ", " ");
     
    if ((v = concept.getField(fields, "200", " ", " ")).size() > 0) 
    odg= rf.getFieldContent200((Field)v.elementAt(0),"af");
    listic+= "&nbsp;" + "Naslov i odgovornost:"+prazno(3)+ formatirajRed(odg) + "<BR>\n";
    listic+="&nbsp;"+ "UDK:"+prazno(20)+ concept.brojUDC1(fields)+ "<BR>\n";
    listic+="&nbsp;"+ "Inventar:"+"<BR>\n";
    listic+="Signatura"+prazno(21)+"Inv."+"&nbsp;" +"br."+prazno(5)+"Dat.inv."+"&nbsp;"+"Vrsta" +"&nbsp;"+"pov."+"&nbsp;";
    listic+="Na\u010Din"+"&nbsp;"+ "nab."+"&nbsp;"+"Cena"+prazno(3)+
  "Napomena"+prazno(11)+"Status"+"<BR>\n";
    for(int i=0;i<109;i++){
      listic+="-";
    }
    listic+="<BR>\n";
    v = concept.getField(fields, "996", " ", " ");
    if ((v = concept.getField(fields, "996", " ", " ")).size() > 0) {
     for (int i = 0; i < v.size(); i++) {
      
       sign=rf.getFieldContent996I((Field)v.elementAt(i),"d");
      if (!sign.equals(""))
        signatura=sign;
      
       inv=rf.getFieldContent996II((Field)v.elementAt(i),"f");
       dat=rf.getFieldContent996II((Field)v.elementAt(i),"o");
       vr=rf.getFieldContent996II((Field)v.elementAt(i),"s");
       nab=rf.getFieldContent996II((Field)v.elementAt(i),"v");
       cena=rf.getFieldContent996II((Field)v.elementAt(i),"3");
       napomena=rf.getFieldContent996II((Field)v.elementAt(i),"r");
       status=rf.getFieldContent996II((Field)v.elementAt(i),"q");
      listic+=dodajPrazno(signatura,30)+dodajPrazno(inv,13)+dodajPrazno(dat,9)+dodajPrazno(vr,11)+
      dodajPrazno(nab,11)+dodajPrazno(cena,7)+dodajPrazno(napomena,19)+status+"<BR>\n";
      
     }  
    }
    listic+="<BR>\n";
    listic+="&nbsp;"+"Lokacijski podaci:"+prazno(7);
    v = concept.getField(fields, "998", " ", " ");
    
    if ((v = concept.getField(fields, "998", " ", " ")).size() > 0) 
       lok=rf.getFieldContent998((Field)v.elementAt(0),"a");
    listic+=lok+"<BR>\n<BR>\n";  
    
    return listic;
    
  }


  public boolean glavni = false;
  public boolean UDC = false;
  public boolean doktorska = false;
  public boolean signSet = false;  // na vrh strane skup signatura (FF, PMF)
  public boolean serijskaF = false;
  public boolean noTes = false; // bez sporednih odrednica (tezaurusa) - SMIP
  public String code = new String("");
  public Vector fields = new Vector();
  public Vector fields1 = new Vector();
  public int br = 1;
  public int bk = 1;
  public int strana = 1; 
  public boolean stampa=false;
  private int docID;
}
