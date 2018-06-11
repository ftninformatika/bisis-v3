package com.gint.app.bisis.sharedsrv;

import com.gint.app.bisis.textsrv.*;
import com.gint.app.bisis.editor.LELibrarian.*;
import com.gint.app.bisis.editor.UFieldSet.*;
import com.gint.app.bisis.editor.edit.Field;
import com.gint.app.bisis.editor.edit.Subfield;
import com.gint.app.bisis.editor.edit.validation.*;

/** Klasa namenjena za cuvanje svih bitnih podataka iz
    okruzenja bibliotekara (konfiguracija UNIMARC-a,
    prava pristupa itd).
  */
public class Environment {

  /** Inicijalizacija okruzenja bibliotekara. Poziva se iskljucivo
      u toku prijavljivanja bibliotekara kada se inicijalizuju sve
      potrebne strukture
    */
  static public void init(LELibrarian lib, UFieldSet fs, TextServer ts, String address) {
    Environment.lib = lib;
    Environment.fs = fs;
    Environment.ts = ts;
    Environment.address = address;
  }

  static public LELibrarian getLib() {
    return lib;
  }

  static public UFieldSet getFs() {
    return fs;
  }

  static public TextServer getTs() {
    return ts;
  }

  public static Field getUndoField() {
    return undoField;
  }

  public static Subfield getUndoSubfield() {
    return undoSubfield;
  }

  public static Field getUndoSubfieldOwner() {
    return undoSubfieldOwner;
  }

  public static Field getUndoSecField() {
    return undoSecField;
  }

  public static String getAddress() {
    return address;
  }

  public static void setUndoField(Field f) {
    undoField = f;
  }

  public static void setUndoSubfield(Subfield sf) {
    undoSubfield = sf;
  }

  public static void setUndoSubfieldOwner(Field f) {
    undoSubfieldOwner = f;
  }

  public static void setUndoSecField(Field f) {
    undoSecField = f;
  }

  public static void setAddress(String a) {
    address = a;
  }

  static private LELibrarian lib = null;
  static private UFieldSet fs = null;
  static private TextServer ts = null;
  static private Field undoField = null;
  static private Subfield undoSubfield = null;
  static private Field undoSubfieldOwner = null;
  static private Field undoSecField = null;
  static private String address = null;


  static private String dbType;
  public static String getDBType() {
    return dbType;
  }
  public static void setDBType(String a) {
    dbType = a;
  }

  static private String driver;
  public static String getDriver() {
    return driver;
  }
  public static void setDriver(String a) {
    driver = a;
  }

  static private String url;
  public static String getURL() {
    return url;
  }
  public static void setURL(String a) {
    url = a;
  }

  static private String username;
  public static String getUsername() {
    return username;
  }
  public static void setUsername(String a) {
    username = a;
  }


  static private String password;
  public static String getPassword() {
    return password;
  }
  public static void setPassword(String a) {
    password = a;
  }


  public static Validator getValidator() {
    return validator;
  }

  public static void setValidator(Validator v) {
    validator = v;
  }

  static private Validator validator;

  public static boolean isNewRezance() {
    return newRezance;
  }

  public static void setNewRezance(boolean b) {
    newRezance = b;
  }

  static private boolean newRezance;

  public static int getDocID() {
    return docID;
  }

  public static void setDocID(int d) {
    docID = d;
  }

  static private int docID;


  static private Task mt = null;

  public static Task getTask() {
    return mt;
  }

  public static void setTask(Task mainTask) {
    mt = mainTask;
  }

  public static void log(String s) {
    if (mt != null)
      mt.log(s);
    else
      System.out.println(s);
  }
  
  static private String locale;
  public static String getLocale() {
    return locale;
  }
  public static void setLocale(String a) {
    locale = a;
  }
  
  private static boolean gUI;
  public static boolean isGUI() {
  	return gUI;
  }
  public static void setGUI(boolean g) {
  	gUI = g;
  }
  
}
