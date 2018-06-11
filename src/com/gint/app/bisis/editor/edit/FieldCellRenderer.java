package com.gint.app.bisis.editor.edit;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class FieldCellRenderer implements TableCellRenderer {
  public Component getTableCellRendererComponent(
      JTable table,
      Object value,
      boolean isSelected,
      boolean hasFocus,
      int row,
      int col) {
    Field myValue = (Field)value;
    // ako je u pitanju celija u poslednjem redu koja ne
    // sadrzi nista
    if (myValue == null)
      return new JLabel();

    // inace vrati komponentu
    myValue.setFieldActive(hasFocus);
    return myValue;
  }
}
