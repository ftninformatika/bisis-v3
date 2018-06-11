package com.gint.app.bisis.circ.reports;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.html.*;
import com.borland.jbcl.layout.*;
import com.gint.app.bisis.circ.Messages;

public class ReportDlg extends JDialog implements Printable {
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JButton bClose = new JButton();
  JButton bPrint = new JButton();
  JScrollPane spReport = new JScrollPane();
  JEditorPane epReport = new JEditorPane();
  PaperA4 paper = new PaperA4();

  public ReportDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try  {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ReportDlg() {
    this(null, "", false);
  }

  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    bClose.setText(Messages.get("REPORTDLG_BCLOSE_TEXT"));
    bClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bClose_actionPerformed(e);
      }
    });
    bPrint.setText(Messages.get("REPORTDLG_BPRINT_TEXT"));
    bPrint.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        bPrint_actionPerformed(e);
      }
    });
    epReport.setEditorKit(new HTMLEditorKit());
    epReport.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        epReport_keyReleased(e);
      }
    });
    spReport.getViewport().setView(epReport);
    getRootPane().setDefaultButton(bClose);
    xYLayout1.setHeight(350);
    xYLayout1.setWidth(500);
    getContentPane().add(panel1);
    panel1.add(spReport, new XYConstraints(14, 14, 467, 281));
    panel1.add(bClose, new XYConstraints(15, 305, 95, -1));
    panel1.add(bPrint, new XYConstraints(386, 305, -1, -1));
    setSize(500, 350);
    Dimension screen = getToolkit().getScreenSize();
    setLocation((screen.width - getSize().width) / 2,
      (screen.height - getSize().height) / 2);
  }

  public int print(Graphics g, PageFormat pf, int index) {
    if (index >= pages.length)
      return Printable.NO_SUCH_PAGE;

    epReport.setText(htmlPrintHeader + pages[index] + htmlPrintFooter);
    g.translate(30, 30);
    epReport.paint(g);
    return Printable.PAGE_EXISTS;
  }

  public void setHTML(String html) {
    text = html;
    epReport.setText(htmlScreenHeader + text + htmlScreenFooter);
    epReport.setCaretPosition(1);
  }

  public void printing() {
    printerJob = PrinterJob.getPrinterJob();
    PageFormat pageFormat = printerJob.defaultPage(paper);
    pageFormat = printerJob.validatePage(pageFormat);
    printerJob.setCopies(1);
    printerJob.setPrintable(this, pageFormat);
    if (printerJob.printDialog()) {
      pages = paginateReport();
      try { printerJob.print(); } catch (Exception ex) { ex.printStackTrace(); }
    }
    epReport.setText(htmlScreenHeader + text + htmlScreenFooter);
  }

  private String[] paginateReport() {
    Vector tempRetVal = new Vector();
    int totalLength = text.length();
    int startPos = 0;
    while (startPos != -1) {
      int pageEnd = findNextPageBreakPos(startPos, text);
      if (pageEnd != -1)
        tempRetVal.addElement(text.substring(startPos, pageEnd));
      else
        tempRetVal.addElement(text.substring(startPos));
      startPos = pageEnd;
    }
    String[] retVal = new String[tempRetVal.size()];
    tempRetVal.copyInto(retVal);
    return retVal;
  }

  private int findNextPageBreakPos(int startPos, String text) {
    int curr = startPos;
    int count = 0;
    while (count < ROWS_PER_PAGE) {
      curr = text.indexOf("<BR>", curr+1);
      if (curr != -1)
        count++;
      else
        break;
    }
    if (count == ROWS_PER_PAGE)
      return curr + 4;
    else
      return -1;
  }
/*
  private int findNextPageBreakPos(int startPos, String text) {
// ubacena promenljiva curr1 koja broji vrste tabele, ako imamo html-tabelu u dokumentu
    int curr = startPos;
    int curr1 = startPos;
    int tekcurr = startPos;
    int count = 0;
    boolean dodaj=false;

    while (count < ROWS_PER_PAGE) {
      curr = text.indexOf("<BR>", tekcurr+1);
      curr1 = text.indexOf("<TR>", tekcurr+1);
      if ((curr != -1) || (curr1 != -1)) {
        if (curr != -1)
          count++;
        if (curr1 != -1)
          count+=4;
        if (curr > curr1) {
          dodaj=true;
          tekcurr = curr;
        }
        else {
          dodaj=false;
          tekcurr = curr1;
        }
      }
      else
        break;
    }
    if (count >= ROWS_PER_PAGE)
      if (dodaj)
        return tekcurr + 4;
      else
        return tekcurr;
    else
      return -1;
  }
*/
  void bClose_actionPerformed(ActionEvent e) {
    setVisible(false);
  }

  void bPrint_actionPerformed(ActionEvent e) {
    printing();
  }

  void epReport_keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
      bClose.doClick();
  }

  String text;
  String[] pages;
  PrinterJob printerJob;
  private static final String htmlScreenHeader = "<HTML><HEAD></HEAD><BODY BGCOLOR=white TEXT=black><FONT FACE=\"Courier New\">\n";
  private static final String htmlScreenFooter = "</FONT></BODY></HTML>";
  private static final String htmlPrintHeader = "<HTML><BODY BGCOLOR=white TEXT=black><FONT FACE=\"Courier New\" SIZE=\"-2\">\n";
  private static final String htmlPrintFooter = "</FONT></BODY></HTML>";
  private static final int ROWS_PER_PAGE = 48; // 4*18;
}

