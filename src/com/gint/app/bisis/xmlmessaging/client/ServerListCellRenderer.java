package com.gint.app.bisis.xmlmessaging.client;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ServerListCellRenderer extends JLabel implements ListCellRenderer {

  public ServerListCellRenderer() {
    blank = new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis/xmlmessaging/client/images/blank.gif"));
    check = new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis/xmlmessaging/client/images/check.gif"));
  }
  
  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
    
    setText(value.toString());
    if (isSelected)
      setIcon(check);
    else
      setIcon(blank);
    return this;
  }
  
  private ImageIcon blank;
  private ImageIcon check;
}
