package com.gint.app.bisis.editor.comlin;

import java.rmi.*;
import java.awt.Cursor;

import javax.swing.*;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.textsrv.*;

public class ConnectCommand extends AbstractCommand {

  public ConnectCommand(MainFrame mf) {
    this.mf = mf;
  }


  public void execute() {
    int hit;
    if (params.size() > 1) {
      JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDPRMPT_TOOMANYPARAMS"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
      return;
    } else if (params.size() == 0) {
      if (Environment.isInternal() == false)
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDCONNECT_CONNECTEDTO") + " " + Environment.getExternalAddress(), com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDCONNECT_REMOTESRV"), JOptionPane.INFORMATION_MESSAGE);
      else
        JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDCONNECT_CONNECTEDTOINTERNAL"), com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDCONNECT_REMOTESRV"), JOptionPane.INFORMATION_MESSAGE);
      return;
    } else {  // jedan parametar
      mf.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      String s = (String)params.elementAt(0);
      if (s.equals("internal")) {
        if (Environment.getAppServer() == Environment.RMI_SERVER) {
          // ako je bio spojen na spoljasnji server, treba vratiti TextServer u Pool
          if (Environment.isInternal() == false)
            try { Environment.getExternalTextServerManager().checkIn(
                    ((RemoteTextServer)(Environment.getReadTs())).getTextServer().getServerUID()
                                                                    );
            } catch (Exception ex) {
              ex.printStackTrace();
            }
        }
        Environment.setReadTs(Environment.getInternalTs());
        setLabel();
        mf.tfComLin.setText("");
        Environment.setInternal(true);
        Environment.setExternalAddress("interni server");
      } else { // spajanje na udaljeni server
        mf.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
          DistributedTextServerManager t = null;
          if (Environment.getAppServer() == Environment.RMI_SERVER) {
            // ako je bio spojen na spoljasnji server, treba vratiti TextServer u Pool
            if (Environment.isInternal() == false)
              Environment.getExternalTextServerManager().checkIn(
                    ((RemoteTextServer)(Environment.getReadTs())).getTextServer().getServerUID()
                                                                 );

            t = (DistributedTextServerManager) Naming.lookup("//" + s + "/RMITextServerPool");

            Environment.setExternalTextServerManager(t);
            RemoteTextServer ts2 = new RemoteTextServer(t.checkOut());
            Environment.setReadTs(ts2);
            Environment.setExternalAddress(s);

          }
          setLabel();
          mf.tfComLin.setText("");
          Environment.setInternal(false);
        } catch (Exception ex) {

          // konektuj se na interni tekst server
          Environment.setReadTs(Environment.getInternalTs());
          Environment.setInternal(true);
          setLabel();

          JOptionPane.showMessageDialog(null, com.gint.app.bisis.editor.Messages.get("BISISAPP_CMDCONNECT_ERROR"), com.gint.app.bisis.editor.Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.ERROR_MESSAGE);
          ex.printStackTrace();
        }
      }
    }
    mf.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }

  private void setLabel() {
    int hits = -1;
    try { hits = Environment.getReadTs().getHitCount(); } catch (Exception ex) {}
    if (Environment.getLocale().equals("sr"))
      mf.lComLin.setText(mf.oblik_lokalni(hits)+" "+hits+" "+mf.oblik_pogodak(hits));
    else
      mf.lComLin.setText(hits+" "+mf.oblik_pogodak(hits));
  }

}
