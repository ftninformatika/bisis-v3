package com.gint.app.bisis.circ;

// Objekat za opisivanje master-detail odnosa
/* Nastao je zbog potrebe da se opise sledeci slucaj:
   Tabela USERS cuva zajednicke podatke za sve tipove korisnika SINGLE, GROUP i INSTITUTIONS;
   U svakoj od ovih tabela se nalaze podaci karakteristicni za odgovarajuce korisnike. Tabela
   USERS i ostale tabele su povezane preko kolona ID(USERS tabela) i
   USER_ID(SINGLE, GROUP i INSTITUTIONS tabele) koje su i kljucevi. Navigaciju
   kroz tabele SINGLE, GROUP i INSTITUTIONS prati navigacija kroz tabelu USERS.
*/
public class MasterLinkDescriptor {
  private boolean cascadeDeletes = false;
  private boolean cascadeUpdates = false;
  private boolean cascadeInserts = false;
  private boolean cascadeSaves = false;
  private String[] detailLinkColumns;
  private String[] masterLinkColumns;
  private DataSet masterDataSet;

/* Za obe tabele u master - detail odnosu se kreira odgovarajuci DataSet. Zatim se kreira
  objekat MasterLinkDescriptor sa parametrima
  masterSet - DataSet master tabele,
  masterColumns i detailColumns - nazivi kolona koje povezuju ove dve tabele (npr. ID i USER_ID) 
*/

/*
  DELETE i INSERT unutar master-detail odnosa se vrsi samo na master DataSet-u; programski se to
  odradjuje i za detail DataSet. INSERT kreira dva prazna DataRow objekta, jedan za master i
  jedan za detail; Kolone koje predstavljaju vezu izmedju ove dve tabele dovoljno je napuniti samo
  u master DataSet-u, dok se ostale kolone moraju rucno popuniti u kodu. 
*/
  public MasterLinkDescriptor(DataSet masterSet, String[] masterColumns, String[] detailColumns){
    masterDataSet = masterSet;
    masterDataSet.setMaster(true);
    masterLinkColumns = masterColumns;
    detailLinkColumns = detailColumns;
  }

  public String[] getMasterLink(){
    return masterLinkColumns;
  }

  public String[] getDetailLink(){
    return detailLinkColumns;
  }

  public DataSet getMasterDataSet(){
    return masterDataSet;
  }

  public boolean isCascadeDeletes(){
    return cascadeDeletes;
  }

  public boolean isCascadeUpdates(){
    return cascadeUpdates;
  }

  public boolean isCascadeInserts(){
    return cascadeInserts;
  }

  public boolean isCascadeSaves(){
    return cascadeSaves;
  }

  public void setCascadeDeletes(boolean delete){
    cascadeDeletes = delete;
  }

  public void setCascadeUpdates(boolean update){
    cascadeUpdates = update;
  }

  public void setCascadeInserts(boolean insert){
    cascadeInserts = insert;
  }

  public void setCascadeSaves(boolean save){
    cascadeSaves = save;
  }


}
