// pomeriti ovo u com.gint.util.gui
// i iskomentarisati
package com.gint.app.bisis.editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class CharacterLookup extends JDialog {

  public CharacterLookup(JFrame parent) {
    super(parent, Messages.get("BISISAPP_CHARLOOKUPDLG_CHOOSE"), true);

    this.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent ev) {
        table.requestFocus();
      }
    });

    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        selected = true;
        selectedChar = ((String)ctm.getValueAt(table.getSelectedRow(), table.getSelectedColumn())).charAt(0);
        dispose();
      }
    });
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        selected = false;
        dispose();
      }
    });

    ctm = new CharacterTableModel();
    table = new JTable(ctm);
    table.setColumnSelectionAllowed(true);
    table.setRowSelectionAllowed(true);
    table.setRowHeight(20);
    dtcr = new CharacterTableCellRenderer();
    for (int i = 0; i < 10; i++)
      table.getColumnModel().getColumn(i).setCellRenderer(dtcr);
    table.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ev) {
        if (ev.getClickCount() == 2)
          btnOK.doClick();
      }
    });
    table.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent ev) {
        //if ((ev.getKeyChar() == KeyEvent.VK_ENTER) || (ev.getKeyChar() == KeyEvent.VK_ESCAPE))
        if (ev.getKeyChar() == KeyEvent.VK_ENTER)
          ev.consume();
      }
      public void keyReleased(KeyEvent ev) {
        if (ev.getKeyChar() == KeyEvent.VK_ENTER)
          btnOK.doClick();
        else if (ev.getKeyChar() == KeyEvent.VK_ESCAPE)
          btnCancel.doClick();
      }
    });

    scrollPane = new JScrollPane();
    scrollPane.getViewport().setView(table);
    scrollPane.setPreferredSize(new Dimension(380, 250));

    getRootPane().setDefaultButton(btnOK);
    getContentPane().setLayout(new FlowLayout());
    getContentPane().add(scrollPane);
    getContentPane().add(btnOK);
    getContentPane().add(btnCancel);

    // velicina i polozaj dijaloga
    setSize(400, 320);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((d.width - getSize().width) / 2 , (d.height - getSize().height) / 2);
  }

  public boolean isSelected() {
    return selected;
  }

  public char getSelectedChar() {
    return selectedChar;
  }

  private JTable table;
  private CharacterTableModel ctm;
  private CharacterTableCellRenderer dtcr;
  private JScrollPane scrollPane;
  private JButton btnOK = new JButton(" " + Messages.get("BISISAPP_CHARLOOKUPDLG_BUTTONOK") + " ");
  private JButton btnCancel = new JButton(Messages.get("BISISAPP_CHARLOOKUPDLG_BUTTONCANCEL"));
  private char selectedChar = ' ';
  private boolean selected = false;

  public class CharacterTableModel extends AbstractTableModel {
    public CharacterTableModel() {
      super();
      generateData();
    }

    public String getColumnName(int col) {
      return "";
    }
    public int getRowCount() {
      return 128;
    }
    public int getColumnCount() {
      return 10;
    }
    public Object getValueAt(int row, int col) {
      return charTable[row][col];
    }
    public Class getColumnClass(int col) {
      return String.class;
    }

    private void generateData() {
      charTable = new String[128][];
      for (int i = 0; i < 128; i++)
        charTable[i] = new String [10];

      int count = 0;
      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 256; j++) {
          charTable[(int)count/10][count%10] = ""+((char)(i*256+j));
          ++count;
        }
      }
    }

    private String[][] charTable;
  }

  public class CharacterTableCellRenderer implements TableCellRenderer {
    public CharacterTableCellRenderer() {
    }
    public Component getTableCellRendererComponent(
        JTable table,
        Object value,
        boolean isSelected,
        boolean hasFocus,
        int row,
        int col) {
      JLabel retVal = new JLabel(value.toString());
      retVal.setHorizontalAlignment(JLabel.CENTER);
      retVal.setFont(new Font("Courier", Font.PLAIN, 16));
      retVal.setText(value.toString());
      if (hasFocus) {
        retVal.setForeground(Color.red);
        retVal.setBackground(Color.yellow);
        retVal.setOpaque(true);
      } else {
        retVal.setForeground(Color.black);
      }
      return retVal;
    }
    private Font font;
  }
}