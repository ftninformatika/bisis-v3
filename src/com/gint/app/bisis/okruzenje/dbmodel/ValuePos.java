package com.gint.app.bisis.okruzenje.dbmodel;

import java.util.*;

public class ValuePos {

  public static int IndexToInsert(Object value, int dg, int gg, Vector v) {
    int i = dg;
    int j;
    for (j = dg; j < gg; j++) {
      if(((String)value).trim().compareTo(((String)v.elementAt(j)).trim()) == 0) {
        return -1;
      }
      if(((String)value).trim().compareTo(((String)v.elementAt(j)).trim()) < 0) {
        return j;
      }
    }
    if(j == gg-1) {
      return j;
    }
    else {
      return j++;
    }
  }

  public static int IndexToInsert(Object value, int dg, int gg,Vector v, int type) {
    int i = dg;
    int j;
    for (j = dg; j < gg; j++) {
      switch(type) {
        case 0:if(new Integer(((String)value).trim()).compareTo(new Integer(((String)v.elementAt(j)).trim())) == 0)
                  return -1;
                if(new Integer(((String)value).trim()).compareTo(new Integer(((String)v.elementAt(j)).trim())) < 0)
                  return j;
        case 1: if(((String)value).trim().compareTo(((String)v.elementAt(j)).trim()) == 0)
                  return -1;
                if(((String)value).trim().compareTo(((String)v.elementAt(j)).trim()) < 0)
                  return j;
      }
    }
    if(j == gg-1)
      return j;
    else
      return j++;
  }

  public static int myLastIndexOf(Object value, int first, int last, Vector v) {
    int i = last;
    int j;
    for(j=i; j >= first; j--) {
      if(value.equals(v.elementAt(j)))
        return j;
    }
    return -1;
  }
}