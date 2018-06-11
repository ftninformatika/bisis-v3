package com.gint.app.bisis.editor.registries;

import java.text.Collator;
import java.util.Comparator;

public class ItemComparator implements Comparator {

  public ItemComparator(Collator collator) {
    this.collator = collator;
  }
  
  public int compare(Object o1, Object o2) {
    RegistryItem i1 = (RegistryItem)o1;
    RegistryItem i2 = (RegistryItem)o2;
    return collator.compare(i1.getText1(), i2.getText1());
  }
  
  public boolean equals(Object o) {
    ItemComparator i = (ItemComparator)o;
    return collator == i.collator;
  }
  
  public static void main(String[] args) {
    Collator c = RegistryUtils.getLatCollator();
    System.out.println(c.compare("Aca", "\"Aca"));
  }
  
  private Collator collator;
}
