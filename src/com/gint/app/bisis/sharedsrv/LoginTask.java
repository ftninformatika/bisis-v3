package com.gint.app.bisis.sharedsrv;

import java.sql.*;
import java.net.*;
import javax.swing.*;

import com.gint.app.bisis.textsrv.*;
import com.gint.app.bisis.editor.LELibrarian.*;
import com.gint.app.bisis.editor.UFieldSet.*;

public class LoginTask {
  private boolean successful = false;
  public boolean isSuccessful() {
    return successful;
  }

  public LoginTask(Connection conn, String username, String password) {
    try {
      LELibrarian lib = new LELibrarian(conn, username, password);
      UFieldSet fs = new UFieldSet(conn);
      TextServer ts = new TextServer(conn);
      String address = "";
      try { address = InetAddress.getAllByName(InetAddress.getLocalHost().getHostAddress())[0].getHostName(); } catch (UnknownHostException ex) { }
      Environment.init(lib, fs, ts, address);
      successful = true;
    } catch (final Exception ex) {
      String msg = ex.toString().substring(ex.toString().indexOf(':')+1);
      if (Environment.isGUI())
        JOptionPane.showMessageDialog(null, msg, Messages.get("SHAREDSRVAPP_SERVERAPP_ERROR"), JOptionPane.ERROR_MESSAGE);
      else
        Environment.log(msg);
      ex.printStackTrace();
    }
  }
}
