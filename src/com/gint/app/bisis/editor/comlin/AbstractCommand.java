package com.gint.app.bisis.editor.comlin;

import java.util.Vector;

import com.gint.app.bisis.editor.MainFrame;

/** Apstraktna klasa koja odredjuje nacin definisanja svih komandi.
  */
abstract public class AbstractCommand {
  abstract public void execute();

  public void setParams(Vector params) {
    this.params = params;
  }

  protected MainFrame mf;
  protected Vector params;
}