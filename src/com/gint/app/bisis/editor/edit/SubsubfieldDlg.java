package com.gint.app.bisis.editor.edit;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import com.borland.jbcl.layout.*;

import com.gint.util.string.UnimarcConverter;

import com.gint.app.bisis.editor.*;

public class SubsubfieldDlg extends JDialog {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JList liSubsubfields = new JList();
  JButton bEdit = new JButton();
  JButton bClose = new JButton();
  private UnimarcConverter conv = new UnimarcConverter();

  public SubsubfieldDlg(EditDlg frame, String title, boolean modal) {
    super(frame, title, modal);
    this.editDlg = frame;
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public SubsubfieldDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    bEdit.setText(Messages.get("BISISAPP_SUBSUBFIELDDLG_BUTTONSET"));
    bEdit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bEdit_actionPerformed(e);
      }
    });
    bClose.setText(Messages.get("BISISAPP_SUBSUBFIELDDLG_BUTTONEXIT"));
    bClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bClose_actionPerformed(e);
      }
    });
    xYLayout1.setHeight(300);
    xYLayout1.setWidth(400);
    panel1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        panel1_mouseClicked(e);
      }
    });
    liSubsubfields.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        liSubsubfields_keyPressed(e);
      }
      public void keyReleased(KeyEvent e) {
        liSubsubfields_keyReleased(e);
      }
    });
    liSubsubfields.setFont(new java.awt.Font("Monospaced", 0, 12));
    getContentPane().add(panel1);
    panel1.add(jScrollPane1, new XYConstraints(21, 19, 356, 218));
    panel1.add(bClose, new XYConstraints(217, 256, 95, -1));
    panel1.add(bEdit, new XYConstraints(86, 256, 95, -1));
    jScrollPane1.getViewport().add(liSubsubfields, null);
    getRootPane().setDefaultButton(bEdit);
    setSize(400, 300);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
  }

  public void setVisible(boolean v) {
    if (v) {
      editDlg.tfContents.setText("");
      editDlg.tfContents.setVisible(false);
      editDlg.taContents.setText("");
      editDlg.jScrollPane2.setVisible(false);
      liSubsubfields.requestFocus();
    }
    super.setVisible(v);
  }

  public void setData(Field f, Subfield sf) {
    this.field = f;
    this.subfield = sf;
    Vector data = new Vector();
    Vector ssfList = Environment.getFs().getSubSubfieldList(f.getName(), sf.getName(), " - ");
    for (int i = 0; i < ssfList.size(); i++) {
      String s = ((String)ssfList.elementAt(i)).trim();
      String ssfName = s.substring(0, s.indexOf(" - ")).trim();
      Subfield ssf = sf.getSubSubfieldByName(ssfName);
      if (ssf != null) {
        if (!ssf.getContent().equals(""))
          s = "* " + s;
        else
          s = "  " + s;
        data.addElement(s);
      }
    }
    liSubsubfields.setListData(data);
//    liSubsubfields.setSelectedIndex(0);
    liSubsubfields.setSelectedValue(liSubsubfields.getModel().getElementAt(0), true);
  }

  public String getSsfName() {
    return ssfName;
  }

  private EditDlg editDlg;
  private Field field;
  private Subfield subfield;
  private String ssfName;

  void panel1_mouseClicked(MouseEvent e) {
    if (e.getClickCount() == 2)
      bEdit.doClick();
  }

  void liSubsubfields_keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_ENTER:
        bEdit.doClick();
        break;
      default:
        break;
    }
  }

  void liSubsubfields_keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_ESCAPE:
        bClose.doClick();
        break;
      default:
        break;
    }
  }

  void bEdit_actionPerformed(ActionEvent e) {
    // some serious stuff here
    editDlg.cameFromSubsubfieldList = true;
    setVisible(false);
    String s = (String)liSubsubfields.getSelectedValue();
    ssfName = s.substring(2, 3);
    String content = subfield.getSubSubfieldByName(ssfName).getContent();
    content = conv.Unimarc2Unicode(content);
    if (Environment.getFs().getSubSubfieldLength(field.getName(), subfield.getName(), ssfName) == 0) {
      editDlg.setMultiLine(true);
      editDlg.taContents.setRows(10);
      editDlg.taContents.setText(content);
      editDlg.taContents.requestFocus();
    } else {
      editDlg.setMultiLine(false);
      // AJOJ
      // AJOJ
      /*String desc = field.getName()+subfield.getName()+ssfName;*/
      //content = Environment.getValidator().preAdjust(desc, field, content, editDlg.utm.getFields());
      editDlg.tfContents.setText(content);
      editDlg.tfContents.requestFocus();
    }
  }

  void bClose_actionPerformed(ActionEvent e) {
    editDlg.cameFromSubsubfieldList = false;
    editDlg.tEdit.requestFocus();
    setVisible(false);
  }
}

