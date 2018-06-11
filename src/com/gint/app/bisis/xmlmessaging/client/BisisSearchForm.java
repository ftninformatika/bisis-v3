package com.gint.app.bisis.xmlmessaging.client;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

import org.jdom.Document;

import com.gint.app.bisis.editor.comlin.NetSearchCommand;
import com.gint.app.bisis.xmlmessaging.MessagingEnvironment;
import com.gint.app.bisis.xmlmessaging.actions.AbstractRemoteCall;
import com.gint.app.bisis.xmlmessaging.communication.LibraryServerDesc;
import com.gint.app.bisis.xmlmessaging.communication.ThreadDispatcher;
import com.gint.app.bisis.xmlmessaging.util.SOAPUtilClient;
import com.gint.app.bisis4.common.records.Field;
import com.gint.app.bisis4.common.records.Record;
import com.gint.app.bisis4.common.records.RecordFactory;

public class BisisSearchForm extends javax.swing.JFrame {

  private JPanel contentPane;
  private JLabel jPrefix1;
  private JLabel jPrefix2;
  private JLabel jPrefix4;
  private JButton jButton5;
  private JLabel jResultLabel;
  private JButton jPrevButton;
  private JButton jNextButton;
  private JButton jSearchButton;
  private JButton jViewDetailButton;
  private JComboBox jOperator4;
  private JComboBox jOperator3;
  private JComboBox jOperator2;
  private JComboBox jOperator1;
  private JTextField jTextField5;
  private JTextField jTextField4;
  private JTextField jTextField3;
  private JTextField jTextField2;
  private JTextField jTextField1;
  private JLabel jPrefix5;
  private JLabel jPrefix3;
  private JScrollPane scrollPane;
  private JScrollPane scrollPane1;
  private String[] operators = new String[] { "AND", "OR", "NOT" };
  private Object[] res;
  private LinkedHashMap selectedHits = new LinkedHashMap();
  private JTextPane jTextPane1;
  private Point viewPortStart = new Point(0, 0);
  private int state = 0;
  private int resultCounter = 0;
  private int recordCounter = 0;
  private int recordIndex = 0;
  private JCheckBox jCompressData;
  private boolean errors = false;

  // variables for table data handling
  private Vector tContent = null;
  private Vector fullContent = null;
  private Vector records = null;
  private BriefInfoTableModel tModel = null;
  private JButton jViewResults;
  private JLabel jServersLabel;
  private JList jServersComboBox;
  private JButton jShowErrors;
  private JTable resTable = null;
  private ThreadDispatcher td = null;
  private LinkedHashMap libServers = null;
  private int maxHits = 30;
  private final int DOWN = 0;
  private final int UP = 1;
  private String errorCodes = "";
  private boolean isErrorView = false;
  private String convID = "";
  private NetSearchCommand cmd;
  private PrefixListDlg prefixListDlg = new PrefixListDlg(this);

  /**
   * Auto-generated main method to display this JPanel inside a new JFrame.
   */
  public static void main(String[] args) {
    JFrame frame = new BisisSearchForm();
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  public BisisSearchForm() {
    super();
    setTitle("Pretra\u017eivanje bibliote\u010dke mre\u017ee");
    td = new ThreadDispatcher();
    libServers = new LinkedHashMap();
    tContent = new Vector();
    fullContent = new Vector();
    records = new Vector();
    libServers = new LinkedHashMap();
    initGUI();
  }

  public BisisSearchForm(NetSearchCommand cmd) {
    this();
    this.cmd = cmd;
    pack();
  }

  private void initGUI() {
    try {
      this.setSize(655, 458);
      this.setResizable(false);
      contentPane = new JPanel();
      contentPane.setPreferredSize(new java.awt.Dimension(645, 458));
      contentPane.setLayout(null);
      this.setContentPane(contentPane);
      scrollPane = new JScrollPane();
      scrollPane.setBounds(14, 178, 615, 238);
      jTextPane1 = new JTextPane();
      // jTextPane1.setBounds(13, 155, 518, 205);
      scrollPane.setViewportView(jTextPane1);
      jTextPane1.setPreferredSize(new java.awt.Dimension(612, 235));
      jTextPane1.setSize(600, 202);
      contentPane.add(scrollPane);
      jPrefix1 = new JLabel();
      contentPane.add(jPrefix1);
      jPrefix1.setText("AU:");
      jPrefix1.setBounds(9, 9, 41, 16);
      jPrefix1.setName("");
      jPrefix2 = new JLabel();
      contentPane.add(jPrefix2);
      jPrefix2.setText("TI:");
      jPrefix2.setBounds(9, 34, 41, 16);
      jPrefix3 = new JLabel();
      contentPane.add(jPrefix3);
      jPrefix3.setText("PY:");
      jPrefix3.setBounds(9, 59, 41, 16);
      jPrefix4 = new JLabel();
      contentPane.add(jPrefix4);
      jPrefix4.setText("PU:");
      jPrefix4.setBounds(8, 86, 41, 16);
      jPrefix5 = new JLabel();
      contentPane.add(jPrefix5);
      jPrefix5.setText("CO:");
      jPrefix5.setBounds(9, 113, 41, 16);
      jTextField1 = new JTextField();
      contentPane.add(jTextField1);
      jTextField1.setText("");
      jTextField1.setBounds(47, 9, 294, 18);
      jTextField1.addKeyListener(new KeyAdapter() {
        public void keyReleased(KeyEvent ev) {
          handleKeys(jPrefix1, jTextField1, ev);
        }
      });
      jTextField1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
          jSearchButton.doClick();
        }
      });
      jTextField2 = new JTextField();
      contentPane.add(jTextField2);
      jTextField2.setBounds(47, 34, 294, 18);
      jTextField2.addKeyListener(new KeyAdapter() {
        public void keyReleased(KeyEvent ev) {
          handleKeys(jPrefix2, jTextField2, ev);
        }
      });
      jTextField2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
          jSearchButton.doClick();
        }
      });
      jTextField3 = new JTextField();
      contentPane.add(jTextField3);
      jTextField3.setBounds(47, 58, 294, 18);
      jTextField3.addKeyListener(new KeyAdapter() {
        public void keyReleased(KeyEvent ev) {
          handleKeys(jPrefix3, jTextField3, ev);
        }
      });
      jTextField3.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
          jSearchButton.doClick();
        }
      });
      jTextField4 = new JTextField();
      contentPane.add(jTextField4);
      jTextField4.setBounds(47, 85, 294, 18);
      jTextField4.addKeyListener(new KeyAdapter() {
        public void keyReleased(KeyEvent ev) {
          handleKeys(jPrefix4, jTextField4, ev);
        }
      });
      jTextField4.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
          jSearchButton.doClick();
        }
      });
      jTextField5 = new JTextField();
      contentPane.add(jTextField5);
      jTextField5.setBounds(47, 112, 294, 18);
      jTextField5.addKeyListener(new KeyAdapter() {
        public void keyReleased(KeyEvent ev) {
          handleKeys(jPrefix5, jTextField5, ev);
        }
      });
      jTextField5.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
          jSearchButton.doClick();
        }
      });
      ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(operators);
      jOperator1 = new JComboBox();
      contentPane.add(jOperator1);
      jOperator1.setModel(jComboBox1Model);
      jOperator1.setBounds(352, 9, 57, 20);
      ComboBoxModel jComboBox2Model = new DefaultComboBoxModel(operators);
      jOperator2 = new JComboBox();
      contentPane.add(jOperator2);
      jOperator2.setModel(jComboBox2Model);
      jOperator2.setBounds(352, 34, 57, 20);
      ComboBoxModel jComboBox3Model = new DefaultComboBoxModel(operators);
      jOperator3 = new JComboBox();
      contentPane.add(jOperator3);
      jOperator3.setModel(jComboBox3Model);
      jOperator3.setBounds(352, 58, 57, 20);
      ComboBoxModel jComboBox4Model = new DefaultComboBoxModel(operators);
      jOperator4 = new JComboBox();
      contentPane.add(jOperator4);
      jOperator4.setModel(jComboBox4Model);
      jOperator4.setBounds(352, 83, 57, 20);
      jSearchButton = new JButton();
      contentPane.add(jSearchButton);
      jSearchButton.setText("Tra\u017ei");
      jSearchButton.setBounds(426, 120, 199, 25);
      jSearchButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
          jSearchButtonAction();
        }
      });
      jNextButton = new JButton();
      jNextButton.setText("Slede\u0107i >>");
      jNextButton.setBounds(462, 427, 167, 19);
      jNextButton.setEnabled(false);
      jNextButton.setVisible(false);
      jNextButton.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent evt) {
          jNextMousePressed(evt);
        }
      });
      contentPane.add(jNextButton);
      jPrevButton = new JButton();
      jPrevButton.setText("<< Prethodni");
      jPrevButton.setBounds(16, 427, 177, 19);
      jPrevButton.setEnabled(false);
      jPrevButton.setVisible(false);
      jPrevButton.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent evt) {
          jPreviousMousePressed(evt);
        }
      });
      contentPane.add(jPrevButton);
      jResultLabel = new JLabel();
      contentPane.add(jResultLabel);
      jResultLabel.setBounds(18, 157, 320, 14);
      jViewDetailButton = new JButton();
      jViewDetailButton.setText("Detaljan prikaz");
      jViewDetailButton.setBounds(215, 424, 226, 25);
      jViewDetailButton.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent evt) {
          jDetailedViewMousePressed(evt);
        }
        public void mouseReleased(MouseEvent evt) {
          jDetailedViewMouseReleased(evt);
        }
      });
      jViewDetailButton.setEnabled(false);
      jViewDetailButton.setVisible(false);
      contentPane.add(jViewDetailButton);
      jShowErrors = new JButton();
      contentPane.add(jShowErrors);
      jShowErrors.setText("Prika\u017ei poruke");
      jShowErrors.setBounds(352, 153, 130, 22);
      jShowErrors.setVisible(false);
      jShowErrors.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent evt) {
          jShowErrorButtonPressed();
        }
      });
      scrollPane1 = new JScrollPane();
      scrollPane1.setBounds(426, 27, 199, 70);
      Vector libs = new Vector();
      ListModel jServersComboBoxModel = new DefaultComboBoxModel(libs);
      jServersComboBox = new JList();
      jServersComboBox.setModel(jServersComboBoxModel);
      scrollPane1.setViewportView(jServersComboBox);
      jServersComboBox.addListSelectionListener(new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent evt) {
          jServerSelectionChanged(evt);
        }
      });
      jServersComboBox.setCellRenderer(new ServerListCellRenderer());
      contentPane.add(scrollPane1);
      if (libs.size() > 0)
        jServersComboBox.setSelectedIndex(0);
      jServersLabel = new JLabel();
      contentPane.add(jServersLabel);
      jServersLabel.setText("Serveri:");
      jServersLabel.setBounds(427, 7, 196, 16);
      contentPane.add(jServersLabel);
      jCompressData = new JCheckBox();
      contentPane.add(jCompressData);
      jCompressData.setText("koristi kompresiju");
      // jCompressData.setVisible(false);
      jCompressData.setBounds(422, 99, 195, 18);
      jCompressData.setSelected(true);
      jViewResults = new JButton();
      contentPane.add(jViewResults);
      jViewResults.setText("Nazad na rezultate");
      jViewResults.setBounds(487, 153, 141, 22);
      jViewResults.setEnabled(false);
      jViewResults.setVisible(false);
      jViewResults.addMouseListener(new MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          jBackToResultsPressed(evt);
        }
      });
      tModel = new BriefInfoTableModel(maxHits, tContent, fullContent,
          selectedHits, this);
      resTable = new JTable(tModel);
      TableColumnModel cm = resTable.getColumnModel();
      (cm.getColumn(0)).setMaxWidth(30);
      (cm.getColumn(1)).setMaxWidth(50);
      (cm.getColumn(2)).setMaxWidth(100);
      (cm.getColumn(2)).setCellRenderer(new LibNameCellRenderer());
      (cm.getColumn(3)).setCellRenderer(new JEditorPaneCellRenderer());

      // query master server to obtain list of available servers
      getStaticServerList();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // function handling server selections
  public void jServerSelectionChanged(ListSelectionEvent evt) {
    if (!evt.getValueIsAdjusting()) {
      libServers.clear();
      Object[] selected = jServersComboBox.getSelectedValues();
      if (selected != null) {
        for (int i = 0; i < selected.length; i++) {
          libServers.put(((LibraryServerDesc) selected[i]).getUrlAddress(),
              selected[i]);
        }
      }
    }

  }

  // function for retrieving server list using xml messaging
  private void getServerList() {
    Object[] selected = jServersComboBox.getSelectedValues();
    if (selected != null) {
      for (int i = 0; i < selected.length; i++) {
        libServers.put(((LibraryServerDesc) selected[i]).getUrlAddress(),
            selected[i]);
      }
    }
    LinkedHashMap reqParams = new LinkedHashMap();
    reqParams.put("libs", libServers);
    reqParams.put("compress", Boolean.valueOf(false));
    convID = "" + (MessagingEnvironment.getMyLibServer()).getLibId() + "_"
        + new java.util.Date().getTime();
    reqParams.put("convId", convID);
    td.invokeThreads(this, "ListServers", reqParams);
  }

  private void getStaticServerList() {
    Vector receivedList = ServerListReader.prepareServerList();
    if (receivedList != null) {
      refreshServerList(receivedList);
    } else {
      jTextPane1.setText("Lista servera trenutno nedostupna");
    }
  }

  private void refreshServerList(Vector results) {
    if (results.size() > 0) {
      LinkedHashMap libServers = MessagingEnvironment.getLibServers();
      libServers.clear();
      for (int i = 0; i < results.size(); i++) {
        LibraryServerDesc oneLib = (LibraryServerDesc) results.get(i);
        if (MessagingEnvironment.DEBUG == 1)
          System.out.println(oneLib.getFullDescriptionString());
        libServers.put(oneLib.getUrlAddress(), oneLib);
      }
      jServersComboBox.removeAll();
      Vector libs = new Vector(MessagingEnvironment.getLibServers().values());
      ListModel jServersComboBoxModel = new DefaultComboBoxModel(libs);
      jServersComboBox.setModel(jServersComboBoxModel);
      jServersComboBox.setSelectedIndex(0);
    }
  }

  // functions for error handling and displaying

  /**
   * Handles showErrorButton behaviour in the form
   */
  public void jShowErrorButtonPressed() {
    if (state == 0) {
      if ((scrollPane.getViewport()).getView() == resTable) {
        jShowErrors.setText("Prika\u017ei rezultate");
        setTextView();
        isErrorView = true;
      } else if (tContent != null && tContent.size() > 0) {
        jShowErrors.setText("Prika\u017ei poruke");
        setTableView();
        isErrorView = true;
      }
    } else {
      if (isErrorView) {
        jTextPane1.setText(RecordFactory.toFullFormat((Record) records
            .get(recordIndex), false));
        jShowErrors.setText("Prika\u017ei poruke");
        jViewDetailButton.setVisible(true);
        refreshPrevNext();
        isErrorView = false;
      } else {
        jTextPane1.setText(errorCodes);
        jShowErrors.setText("Prika\u017ei zapise");
        jViewDetailButton.setVisible(false);
        jPrevButton.setVisible(false);
        jNextButton.setVisible(false);
        isErrorView = true;
      }
    }
  }

  /*****************************************************************************
   * functions for handling navigation buttons
   ****************************************************************************/

  /**
   * @param evt
   *          Event handler for navigation button Next
   */
  public void jNextMousePressed(MouseEvent evt) {
    if (jNextButton.isEnabled() && state == 0) {
      try {
        tModel.replaceContent(UP);
        refreshPrevNext();
      } catch (ArrayIndexOutOfBoundsException aiob) {
        // do nothing
      }
    } else if (jNextButton.isEnabled() && state == 1) {
      try {
        recordIndex++;
        jTextPane1.setText(RecordFactory.toFullFormat((Record) records
            .get(recordIndex), false));
        jTextPane1.setCaretPosition(0);
        jPrevButton.setVisible(true);
        jPrevButton.setEnabled(true);
        refreshPrevNext();
        jResultLabel.setText("Rezultat: " + (recordIndex + 1) + " od "
            + records.size());
      } catch (ArrayIndexOutOfBoundsException aiob) {
        // do nothing
      }
    }
  }

  /**
   * @param evt
   *          Event handler for navigation button Previous
   */
  public void jPreviousMousePressed(MouseEvent evt) {
    if (jPrevButton.isEnabled() && state == 0) {
      try {
        tModel.replaceContent(DOWN);
        refreshPrevNext();
      } catch (ArrayIndexOutOfBoundsException aiob) {
        // do nothing
      }
    } else if (jPrevButton.isEnabled() && state == 1) {
      try {
        jTextPane1.setText("Ni\u0161ta");
        recordIndex--;
        jTextPane1.setText(RecordFactory.toFullFormat((Record) records
            .get(recordIndex), false));
        jTextPane1.setCaretPosition(0);
        refreshPrevNext();
        jResultLabel.setText("Rezultat: " + (recordIndex + 1) + " od "
            + records.size());
        scrollPane.setViewportView(jTextPane1);
      } catch (ArrayIndexOutOfBoundsException aiob) {
        // do nothing
      }
    }
  }

  /*
   * private private function just for updating status of buttons and their
   * labels
   */

  private void refreshPrevNext() {
    if (state == 0) {
      if (tModel.getLastActive() == fullContent.size()) {
        disableButton(jNextButton);
      } else {
        int countOfNext = 0;
        if (tModel.getLastActive() + maxHits <= fullContent.size())
          countOfNext = maxHits;
        else
          countOfNext = fullContent.size() - tModel.getLastActive();
        jNextButton.setText("Slede\u0107ih " + countOfNext + " >>");
        enableButton(jNextButton);
      }

      if ((tModel.getFirstActive() - tContent.size()) <= 0) {
        disableButton(jPrevButton);
      } else {
        jPrevButton.setText("<< Prethodnih " + maxHits);
        enableButton(jPrevButton);
      }

    } else if (state == 1) {
      if (recordIndex == 0)
        disableButton(jPrevButton);
      else {
        jPrevButton.setText("<< Prethodni");
        enableButton(jPrevButton);
      }
      if (recordIndex == (records.size() - 1))
        disableButton(jNextButton);
      else {
        jNextButton.setText("Slede\u0107i >>");
        enableButton(jNextButton);
      }
    }
  }

  /*****************************************************************************
   * functions for handling search button events (trigers the search threads)
   ****************************************************************************/

  private void jSearchButtonAction() {
    resetForm();
    selectedHits.clear();
    jTextPane1.setVisible(true);
    jTextPane1.setText("pretraga u toku...");
    convID = "" + (MessagingEnvironment.getMyLibServer()).getLibId() + "_"
        + new java.util.Date().getTime();
    if (libServers.size() > 0) {
      if (jTextField1.getText().trim().equals("")
          && jTextField2.getText().trim().equals("")
          && jTextField3.getText().trim().equals("")
          && jTextField4.getText().trim().equals("")) {
        jTextPane1
            .setText("Nije postavljen nijedan uslov za pretra\u017eivanje");
      } else {
        String proxyAddress = "";
        String proxyPort = "";
        String serviceAddress = "";

        String[] prefixes = new String[5];
        String[] values = new String[5];
        String[] operators = new String[4];

        prefixes[0] = jPrefix1.getText();
        prefixes[1] = jPrefix2.getText();
        prefixes[2] = jPrefix3.getText();
        prefixes[3] = jPrefix4.getText();
        prefixes[4] = jPrefix5.getText();

        values[0] = jTextField1.getText();
        values[1] = jTextField2.getText();
        values[2] = jTextField3.getText();
        values[3] = jTextField4.getText();
        values[4] = jTextField5.getText();

        operators[0] = (String) jOperator1.getSelectedItem();
        operators[1] = (String) jOperator2.getSelectedItem();
        operators[2] = (String) jOperator3.getSelectedItem();
        operators[3] = (String) jOperator4.getSelectedItem();

        // pack parameters for query remote source
        LinkedHashMap reqParams = new LinkedHashMap();
        reqParams.put("libs", libServers);
        reqParams.put("prefixes", prefixes);
        reqParams.put("values", values);
        reqParams.put("operators", operators);
        reqParams.put("convId", convID);
        reqParams.put("compress", Boolean.valueOf(jCompressData.isSelected()));
        td.invokeThreads(this, "PerformSearch", reqParams);
      }
    } else {
      jTextPane1.setText("Nije izabran nijedan server za razmenu");
    }
  }
  
  /*
   * private jBackToResults button event handler - returns the form to the table
   * view after records has been received and viewed
   */
  private void jBackToResultsPressed(MouseEvent ev) {
    if (jViewDetailButton.isEnabled()) {
      state = 0;
      jResultLabel.setText("Ukupno rezultata: " + fullContent.size());
      disableButton(jShowErrors);
      disableButton(jViewResults);
      jViewDetailButton.setText("Detaljan prikaz");
      records.clear();
      setTableView();
      refreshPrevNext();
    }
  }

  /*****************************************************************************
   * functions for handling detail view button events (trigers details
   * retreival)
   ****************************************************************************/

  /*
   * private jDetailedViewButton button onMousePress event handler
   */
  private void jDetailedViewMousePressed(MouseEvent ev) {
    if (state == 0) {
      scrollPane.setViewportView(jTextPane1);
      jTextPane1.setVisible(true);
      scrollPane.getViewport().setViewPosition(viewPortStart);
      jResultLabel.setText("");
      records.clear();
      recordIndex = 0;
    }
  }

  /*
   * private jDetailedViewButton button onMouseRelease event handler - invokes
   * communication threads
   */
  private void jDetailedViewMouseReleased(MouseEvent ev) {
    if (state == 0) {
      state = 1;
      enableButton(jViewResults);
      jViewDetailButton.setText("Preuzmi zapis");
      LinkedHashMap reqParams = new LinkedHashMap();
      reqParams.put("hits", selectedHits);
      reqParams.put("convId", convID);
      reqParams.put("compress", Boolean.valueOf(jCompressData.isSelected()));
      td.invokeThreads(this, "RetrieveRecords", reqParams);
    } else {
      if (MessagingEnvironment.DEBUG == 1)
        System.out.println("Zapis se salje u glavni editor");
      Record rec = (Record)records.get(recordIndex);
      Iterator it = rec.getFields().iterator();
      while (it.hasNext()) {
        Field f = (Field)it.next();
        if (f.getName().startsWith("99"))
          it.remove();
      }
      cmd.transferRecord(RecordFactory.toUNIMARC(rec));
    }
  }
  
  private void handleKeys(JLabel l, JTextField tf, KeyEvent ev) {
    switch (ev.getKeyCode()) {
      case KeyEvent.VK_UP:
        if (tf == jTextField1)
          jTextField5.requestFocus();
        else if (tf == jTextField2)
          jTextField1.requestFocus();
        else if (tf == jTextField3)
          jTextField2.requestFocus();
        else if (tf == jTextField4)
          jTextField3.requestFocus();
        else if (tf == jTextField5)
          jTextField4.requestFocus();
        break;
      case KeyEvent.VK_DOWN:
        if (tf == jTextField1)
          jTextField2.requestFocus();
        else if (tf == jTextField2)
          jTextField3.requestFocus();
        else if (tf == jTextField3)
          jTextField4.requestFocus();
        else if (tf == jTextField4)
          jTextField5.requestFocus();
        else if (tf == jTextField5)
          jTextField1.requestFocus();
        break;
      case KeyEvent.VK_ESCAPE:
        setVisible(false);
        break;
      case KeyEvent.VK_F9:
        String pref = l.getText().substring(0, 2);
        prefixListDlg.moveTo(pref);
        prefixListDlg.setVisible(true);
        if (prefixListDlg.isSelected()) {
          String s = prefixListDlg.getSelectedPrefix()+":";
          l.setText(s);
        }
        break;
    }
  }

  /*****************************************************************************
   * Communication functions
   ****************************************************************************/

  /**
   * Public and synchronized function accessible from runners inside
   * CommunicationThreads When thread has finished communication with server it
   * will invoke this function to process the received document
   * 
   * @param resp -
   *          XMLDocument representing received XML from server
   * @param type -
   *          type of operation
   * @param lib -
   *          data about lib server from which the result has been received
   */

  public synchronized void processResponse(Document resp,
      AbstractRemoteCall caller, LibraryServerDesc lib) {
    MessagingError me = new MessagingError();
    SOAPUtilClient su = new SOAPUtilClient();
    Vector result = caller.processResponse(resp, lib, me);
    if (result == null) {
      if (me.isActive())
        setErrors(me.getCode());
    } else {
      if (state == 0) {
        if ((caller.getClass().getName())
            .equals("com.gint.app.bisis.xmlmessaging.actions.SearchRequestActionCall"))
          refreshResults(result);
        else if ((caller.getClass().getName())
            .equals("com.gint.app.bisis.xmlmessaging.actions.ListServersActionCall"))
          refreshServerList(result);
      } else if (state == 1) {
        refreshRecordsList(result);
      }
    }

  }

  /**
   * Support function used from processResponse and accessible from runners
   * inside threads
   */
  public synchronized void setErrors(String text) {
    errors = true;
    if (state == 0) {
      jTextPane1.setText(jTextPane1.getText() + text);
      enableButton(jShowErrors);
    } else {
      errorCodes += text;
      enableButton(jShowErrors);
    }

  }

  /*
   * Support function used from processResponse @param newResults - vector of
   * newly received search results
   */
  private void refreshResults(Vector newResults) {
    if (newResults != null) {
      tModel.addResultsToTable(newResults);
      updateResultCount();
    }
  }

  /*
   * Support function used from processResponse @param newResults - vector of
   * newly received Unimarc records
   */

  private void refreshRecordsList(Vector newResults) {
    if (MessagingEnvironment.DEBUG == 1)
      System.out.println("Refreshing records list");
    if (newResults != null) {
      records.addAll(newResults);
      updateRecordCount(newResults.size());
      refreshPrevNext();
    }
  }

  /*
   * Support function used from refreshResults
   */

  private void updateResultCount() {
    jResultLabel.setText("Primljeno rezultata: " + fullContent.size());
    setTableView();
    refreshPrevNext();
  }

  /*
   * Support function used from refreshRecordsList
   */
  private void updateRecordCount(int count) {
    jResultLabel.setText("Primljeno zapisa: " + records.size());
    jTextPane1.setText(RecordFactory.toFullFormat((Record) records
        .get(recordIndex), false));
    jTextPane1.setCaretPosition(0);
    jPrevButton.setVisible(true);
    jPrevButton.setEnabled(true);
    refreshPrevNext();
  }

  /*****************************************************************************
   * Various utility functions - mostly private
   ****************************************************************************/

  private void resetForm() {
    state = 0;
    resultCounter = 0;
    recordCounter = 0;
    errors = false;
    jResultLabel.setText("");
    disableButton(jNextButton);
    disableButton(jPrevButton);
    disableButton(jViewDetailButton);
    jViewDetailButton.setText("Detaljan prikaz");
    jShowErrors.setVisible(false);
    jShowErrors.setText("Prika\u017ei poruke");
    errorCodes = "";
    tModel.clearTable();
    setTextView();
  }

  private void disableButton(JButton target) {
    target.setEnabled(false);
    target.setVisible(false);
  }

  private void enableButton(JButton target) {
    target.setEnabled(true);
    target.setVisible(true);
  }

  private void setTableView() {
    if (scrollPane.getViewport().getView() != resTable) {
      jTextPane1.setVisible(false);
      resTable.setVisible(true);
      scrollPane.setViewportView(resTable);
      resTable.setPreferredScrollableViewportSize(new Dimension(
          jTextPane1.getWidth(), jTextPane1.getHeight()));
      if (errors)
        jShowErrors.setVisible(true);
    }
  }

  private void setTextView() {
    resTable.setVisible(false);
    jTextPane1.setVisible(true);
    scrollPane.setViewportView(jTextPane1);
  }

  /**
   * @return Returns the jViewDetailButton.
   */
  public JButton getJViewDetailButton() {
    return jViewDetailButton;
  }
}
