package com.gint.app.bisis.unimarc.dbmodel;

import java.util.*;

public class Position {

  public static int IndexToInsert1(Object value, int dg, int gg, Vector v) {
   int val = Integer.parseInt((String)value);
   int i = dg;
   for(i=dg;i<v.size();i++) {
    if(val == Integer.parseInt((String)v.elementAt(i)))
      return -1;
    if(val < Integer.parseInt((String)v.elementAt(i)))
      return i;
   }
   if(i == v.size()-1)
    return i;
   else
    return i++;
  }

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
    int intValue = Integer.parseInt((String)value);
    int vIntValue = -1;
    for (j = dg; j < gg; j++) {
      vIntValue = Integer.parseInt((String)v.elementAt(j));
      switch(type) {
        case 0: if(intValue == vIntValue)
                  return -1;
                if(intValue < vIntValue)
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