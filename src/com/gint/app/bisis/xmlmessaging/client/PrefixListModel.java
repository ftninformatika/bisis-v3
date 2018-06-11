package com.gint.app.bisis.xmlmessaging.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.AbstractListModel;

/**
 *
 * @author branko
 */
public class PrefixListModel extends AbstractListModel {

  public PrefixListModel() {
    this((Locale)null);
  }
  
  public PrefixListModel(String locale) {
    this(new Locale(locale));
  }

  public PrefixListModel(Locale locale) {
    displayed = new ArrayList();
    initList();
  }
  
  public int getSize() {
    return displayed.size();
  }

  public Object getElementAt(int index) {
    return displayed.get(index);
  }
  
  public String getPrefixName(int index) {
    return ((String)displayed.get(index)).substring(0, 2);
  }
  
  public int getSelection(char c) {
    int n = displayed.size();
    for (int i = 0; i < n; i++) {
      if (Character.toUpperCase(((String)displayed.get(i)).charAt(0)) == 
          Character.toUpperCase(c))
        return i;
    }
    return -1;
  }

  public int getSelection(String s) {
    int n = displayed.size();
    for (int i = 0; i < n; i++) {
      String disp = (String)displayed.get(i);
      if (disp.startsWith(s))
        return i;
    }
    return -1;
  }
  
  private void initList() {
    displayed.add("AB - Kratak sadr\u017eaj ili sinopsis");
    displayed.add("AM - Na\u010din nabavke");
    displayed.add("AP - Namena nabavke");
    displayed.add("AT - Skra\u0107eni klju\u010dni naslov");
    displayed.add("AU - Autor");
    displayed.add("BI - Broj bibliografije / kod statistike");
    displayed.add("BN - Standardni broj knjige");
    displayed.add("CB - Corporate body name used as subject");
    displayed.add("CC - Kod za vrstu sadr\u017eaja");
    displayed.add("CD - Coden");
    displayed.add("CH - Vremenska predmetna odrednica");
    displayed.add("CL - Knji\u017ena zbirka");
    displayed.add("CN - Napomena o sadr\u017eaju");
    displayed.add("CO - Dr\u017eava izdavanja");
    displayed.add("CP - Sedi\u0161te korporativnog tela");
    displayed.add("CR - Kreator zapisa");
    displayed.add("CS - Korporativno telo");
    displayed.add("CY - Kreator kopije zapisa");
    displayed.add("DA - Datum inventarisanja");
    displayed.add("DC - Univerzalna decimalna klasifikacija");
    displayed.add("DE - Korespondent");
    displayed.add("DR - Doktor nauka");
    displayed.add("DT - Bibliografski nivo (tip dok.)");
    displayed.add("ES - Edition statement");
    displayed.add("FC - \u0160ifra fakulteta");
    displayed.add("FD - VTO \u0161ifra");
    displayed.add("FI - Finansijer");
    displayed.add("FN - Family name used as subject");
    displayed.add("FQ - U\u010destalost izla\u017eenja");
    displayed.add("FS - Formalna predmetna odrednica");
    displayed.add("GE - Klju\u010dna re\u010d");
    displayed.add("GM - Op\u0161ta oznaka gra\u0111e");
    displayed.add("GN - Geografska predmetna odrednica");
    displayed.add("GS - General notes");
    displayed.add("HI - Hijeararhija");
    displayed.add("IC - Kodovi za ilustracije");
    displayed.add("ID - Identifikator zapisa");
    displayed.add("II - Internal bibliographies");
    displayed.add("IN - Inventarski broj");
    displayed.add("IR - Inventarske napomene");
    displayed.add("KT - Klju\u010dni naslov");
    displayed.add("KW - Klju\u010dne re\u010di");
    displayed.add("LA - Jezik");
    displayed.add("LC - Kod za oblik knji\u017evnog dela");
    displayed.add("LI - Stepen dostupnosti");
    displayed.add("LN - Lokalni broj");
    displayed.add("LO - Jezik");
    displayed.add("ND - Broj i datum");
    displayed.add("NM - Name of manufacturer");
    displayed.add("NT - Volume designation");
    displayed.add("P2 - Godina izdavanja 2");
    displayed.add("PA - Publisher address");
    displayed.add("PM - Place of manufacture");
    displayed.add("PN - Personal name used as subj.");
    displayed.add("PP - Mesto izdavanja");
    displayed.add("PR - Price");
    displayed.add("PU - Izdava\010d, distributer itd.");
    displayed.add("PY - Godina izdavanja 1");
    displayed.add("RE - Republika");
    displayed.add("RJ - Programski deo");
    displayed.add("RS - Stanje zapisa");
    displayed.add("RT - Kod za vrstu zapisa");
    displayed.add("SC - Cancelled ISSN");
    displayed.add("SG - Signatura");
    displayed.add("SI - Oznaka ustanove (sigla)");
    displayed.add("SK - Kod za statistiku");
    displayed.add("SN - ISSN broj une\u0161en u \u010dlancima");
    displayed.add("SP - ISSN broj");
    displayed.add("SR - Dobavlja\u010d");
    displayed.add("SS - Status serijske publikacije");
    displayed.add("ST - Status");
    displayed.add("SU - Podnaslov");
    displayed.add("TI - Naslov");
    displayed.add("TN - Tematska predmetna odrednica");
    displayed.add("TP - Vrsta tela");
    displayed.add("TS - Title used as subject");
    displayed.add("TY - Vrsta serijske publikacije");
    displayed.add("UG - UDK grupa");
    displayed.add("US - UDK statistika");
    displayed.add("UT - Slobodno oblikovane predmetne odrednice");
    displayed.add("Y1 - Godina odbrane");
    displayed.add("Y2 - Godina promocije");
  }
  
  List displayed;
}
