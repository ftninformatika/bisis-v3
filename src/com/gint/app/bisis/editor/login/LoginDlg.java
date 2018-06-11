package com.gint.app.bisis.editor.login;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

import com.gint.util.gui.SwingWorker;

public class LoginDlg extends JDialog {
  JPanel panel1 = new JPanel();
  JLabel lUsername = new JLabel();
  XYLayout xYLayout1 = new XYLayout();
  JLabel lPassword = new JLabel();
  JTextField tfUsername = new JTextField();
  JPasswordField pfPassword = new JPasswordField();
  JButton bOK = new JButton();
  JButton bCancel = new JButton();
  JLabel lMessage = new JLabel();
  Timer timer;
  public LoginDlg ld;

  public LoginDlg(Frame frame, String title, boolean modal, Connection conn, String bibid, String bibtajozn) {
    super(frame, title, modal);
    this.conn = conn;
    this.bibid = bibid;
    this.bibtajozn = bibtajozn;
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
  }

  public LoginDlg(Connection conn) {
    this(null, "", false, conn, null, null);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    lUsername.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_LOGINDLG_LABELUSER"));
    lPassword.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_LOGINDLG_LABELPASS"));
    bOK.setText("OK");
    bOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bOK_actionPerformed(e);
      }
    });
    getRootPane().setDefaultButton(bOK);
    bCancel.setText(com.gint.app.bisis.editor.Messages.get("BISISAPP_LOGINDLG_BUTTONCANCEL"));
    bCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bCancel_actionPerformed(e);
      }
    });
    xYLayout1.setHeight(153);
    xYLayout1.setWidth(227);
    tfUsername.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        tfUsername_keyReleased(e);
      }
    });
    tfUsername.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tfUsername_actionPerformed(e);
      }
    });
    pfPassword.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        pfPassword_actionPerformed(e);
      }
    });
    pfPassword.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        pfPassword_keyReleased(e);
      }
    });
    lMessage.setForeground(Color.red);
    lMessage.setHorizontalAlignment(SwingConstants.CENTER);
    lMessage.setText(" ");
    getContentPane().add(panel1);
    panel1.add(lUsername, new XYConstraints(15, 18, 78, -1));
    panel1.add(lPassword, new XYConstraints(15, 50, -1, -1));
    panel1.add(tfUsername, new XYConstraints(93, 16, 114, -1));
    panel1.add(pfPassword, new XYConstraints(93, 48, 114, -1));
    panel1.add(bCancel, new XYConstraints(117, 114, 89, -1));
    panel1.add(bOK, new XYConstraints(16, 114, 83, -1));
    panel1.add(lMessage, new XYConstraints(0, 85, 227, -1));
    timer = new Timer(100, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        lMessage.setText(getMessage());
      }
    });
  }

  public boolean getCanceled() {
    return canceled;
  }

  public void setCanceled(boolean canceled) {
    this.canceled = canceled;
  }

  synchronized public boolean getSuccessful() {
    return successful;
  }

  synchronized public void setSuccessful(boolean successful) {
    this.successful = successful;
  }

  synchronized public String getMessage() {
    return message;
  }

  synchronized public void setMessage(String message) {
    this.message = message;
  }

  private boolean canceled = false;
  private boolean successful = false;
  Connection conn;
  String username;
  String password;
  private String bibid;
  private String bibtajozn;
  private String message = " ";

  void bOK_actionPerformed(ActionEvent e) {
    if (bibid != null && bibtajozn != null) {
      username = bibid;
      password = bibtajozn;
    } else {
      username = tfUsername.getText().trim();
      password = new String(pfPassword.getPassword()).trim();
    }
    ld = this;
    final SwingWorker worker = new SwingWorker() {
      public Object construct() {
        return new LoginTask(ld, conn, username, password);
      }
    };
    worker.start();
    timer.start();
  }


  void bCancel_actionPerformed(ActionEvent e) {
    successful = false;
    canceled = true;
    setVisible(false);
  }

  void tfUsername_keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      /*
      case KeyEvent.VK_ENTER:
        bOK.doClick();
        break;
      */
      case KeyEvent.VK_ESCAPE:
        bCancel.doClick();
        break;
      default:
        break;
    }
  }

  void pfPassword_keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      /*
      case KeyEvent.VK_ENTER:
        bOK.doClick();
        break;
      */
      case KeyEvent.VK_ESCAPE:
        bCancel.doClick();
        break;
      default:
        break;
    }
  }

  void tfUsername_actionPerformed(ActionEvent e) {
    bOK.doClick();
  }

  void pfPassword_actionPerformed(ActionEvent e) {
    bOK.doClick();
  }
}

