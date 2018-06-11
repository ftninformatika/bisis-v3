package com.gint.app.bisis.xmlmessaging.client;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.gint.app.bisis.xmlmessaging.communication.LibraryServerDesc;

public class LibNameCellRenderer extends JLabel implements TableCellRenderer {

  public LibNameCellRenderer() {
  }
  
  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    
    if (value instanceof LibraryServerDesc) {
      LibraryServerDesc lsd = (LibraryServerDesc)value;
      setText(lsd.getLibName());
    } else {
      setText(value.toString());
    }
    return this;
  }

}
