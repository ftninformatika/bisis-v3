package com.gint.app.bisis.editor.login;

import java.sql.*;
import java.net.*;
import javax.swing.*;

import com.gint.app.bisis.textsrv.*;
import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.LELibrarian.*;
import com.gint.app.bisis.editor.UFieldSet.*;

public class LoginTask {
  private LoginDlg ld;
  private boolean successful = false;
  public boolean isSuccessful() {
    return successful;
  }

  public LoginTask(LoginDlg ld, Connection conn, String username, String password) {
    this.ld = ld;
    try {
      if (ld != null)
        ld.setMessage(com.gint.app.bisis.editor.Messages.get("BISISAPP_LOGINTASK_LABELLOADENV"));
      LELibrarian lib = new LELibrarian(conn, username, password);
      if (ld != null)
        ld.setMessage(com.gint.app.bisis.editor.Messages.get("BISISAPP_LOGINTASK_LABELLOADUNIMARC"));
      UFieldSet fs = new UFieldSet(conn);
      if (ld != null)
        ld.setMessage(com.gint.app.bisis.editor.Messages.get("BISISAPP_LOGINTASK_LABELINITTEXTSRV"));
      TextServer ts = new TextServer(Environment.getDBType(), conn, Environment.getURL(), Environment.getUsername(), Environment.getPassword());
      Environment.setInternalTs(ts);
      if (ld != null)
        ld.setMessage(com.gint.app.bisis.editor.Messages.get("BISISAPP_LOGINTASK_LABELAQUIREADDRESS"));
      String address = "";
      try { address = InetAddress.getAllByName(InetAddress.getLocalHost().getHostAddress())[0].getHostName(); } catch (UnknownHostException ex) { }
      Environment.init(lib, fs, ts, ts, address);
      successful = true;
      if (ld != null) {
        ld.setSuccessful(true);
        ld.setVisible(false);
      }
    } catch (final Exception ex) {
      ex.printStackTrace();
      if (ld != null)
        ld.setSuccessful(false);
      String msg = ex.toString().substring(ex.toString().indexOf(':')+1);
      JOptionPane.showMessageDialog(null, msg, com.gint.app.bisis.editor.Messages.get("BISISAPP_LOGINTASK_LOGINTASKFAILED"), JOptionPane.ERROR_MESSAGE);
    }
    if (ld != null)
      ld.setMessage(" ");
  }
}
