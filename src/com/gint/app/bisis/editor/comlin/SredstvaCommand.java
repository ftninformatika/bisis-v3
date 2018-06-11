package com.gint.app.bisis.editor.comlin;
import javax.swing.*;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.report.izvrsnoVece.SredstvaDlg;


public class SredstvaCommand extends AbstractCommand{
	   public SredstvaCommand(MainFrame mf)  {
		    this.mf = mf; 
        }

		  public void execute() {
		    if (params.size() != 0) {
		      JOptionPane.showMessageDialog(null, Messages.get("BISISAPP_CMDPRINT_WRONGNUMBEROFPARAMS"), Messages.get("BISISAPP_ERRORDLG_FRAMETITLE"), JOptionPane.INFORMATION_MESSAGE);
		      return;
		    }
		    mf.tfComLin.setText("");
		    SredstvaDlg sdlg=new SredstvaDlg((mf));
		    sdlg.setVisible(true);
		   }
		   
}
		


