package com.gint.app.bisis.okruzenje.dbmodel;

import java.util.Vector;

public class Sorter {

  /** Sortira niz integera po qsort algoritmu. */
  public static void qsort(int[] niz) {
    qsort(0, niz.length - 1, niz);
  }

  /** Sortira niz stringova po qsort algoritmu. */
  public static void qsort(String[] niz) {
    qsort(0, niz.length - 1, niz);
  }

  /** Zapravo ovo sortira, tako sto podeli niz na dva podniza, a onda ih rekurzivno sortira. */
  public static void qsort(int d, int g, String[] a) {
    int i, j;
    String k;
    // sort jednoelementnog niza
    if (d >= g)
      return;
    // sort dvoelementnog niza
    if (d + 1 == g) {
      if (a[d].compareTo(a[g]) > 0) {
        //zameni(d, g, a);
        k    = a[d];
        a[d] = a[g];
        a[g] = k;
      }
      return;
    }
    // svi ostali slucajevi
    //j = podeli(d, g, a);
    j = d;
    for (i = d + 1; i <= g; i++)
      if (a[i].compareTo(a[d]) <= 0) {
        //zameni(++j, i, a);
        j++;
        k    = a[j];
        a[j] = a[i];
        a[i] = k;
      }
    //zameni(d, j, a);
    k    = a[d];
    a[d] = a[j];
    a[j] = k;

    qsort(d, j - 1, a);
    qsort(j + 1, g, a);
  }

  /** Zapravo ovo sortira, tako sto podeli niz na dva podniza, a onda ih rekurzivno sortira. */
  public static void qsort(int d, int g, int[] a) {
    int i, j, k;
    // sort jednoelementnog niza
    if (d >= g)
      return;
    // sort dvoelementnog niza
    if (d + 1 == g) {
      if (a[d] > a[g]) {
        //zameni(d, g, a);
        k    = a[d];
        a[d] = a[g];
        a[g] = k;
      }
      return;
    }
    // svi ostali slucajevi
    //j = podeli(d, g, a);
    j = d;
    for (i = d + 1; i <= g; i++)
      if (a[i] <= a[d]) {
        //zameni(++j, i, a);
        j++;
        k    = a[j];
        a[j] = a[i];
        a[i] = k;
      }
    //zameni(d, j, a);
    k    = a[d];
    a[d] = a[j];
    a[j] = k;

    qsort(d, j - 1, a);
    qsort(j + 1, g, a);
  }

 /** Sortira vektor.
   @param v Vektor koji treba sortirati.
  */
  public static Vector sortVector(Vector v) {
    Vector retVal = new Vector();
    String niz[] = null;
    String sniz[] = null;
    int i, j;
    niz = new String[v.size()];
    v.copyInto(niz);
    Sorter.qsort(niz);
    for (i = 0; i < niz.length; i++)
      retVal.addElement(niz[i]);
    return retVal;
  }

}
