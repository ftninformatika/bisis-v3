package com.gint.app.bisis.circ;

import java.util.*;

public class CoolDate extends Date{
  public CoolDate(long time){
    super(time);
  }

  public CoolDate plus(int field, int units){
    Calendar now = Calendar.getInstance(Locale.GERMAN);
    now.setTime(new java.util.Date(getTime()));
    now.add(field, units);
    return new CoolDate(now.getTime().getTime());
  }

  public CoolDate plus(int units){
    return plus(CoolDate.DAY, units);
  }

  public int minus(CoolDate nextDate) {
    int noDays=0;
    CoolDate sada = new CoolDate(this.getTime());

    while (sada.before(nextDate)) {
      sada = sada.plus(1);
      noDays++;
    }
    return noDays;
  }

  public int get(int field) {
     Calendar now = Calendar.getInstance(Locale.GERMAN);
     now.setTime(new java.util.Date(getTime()));
     return now.get(field);
  }

  public static int DAY = Calendar.DATE;
  public static int MONTH = Calendar.MONTH;
}
