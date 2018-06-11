package com.gint.app.bisis.circ.warnings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.CardLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.io.BufferedWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.apache.xmlbeans.XmlOptions;

import com.gint.app.bisis.circ.DBConnection;
import com.gint.app.bisis.circ.DBConnection2;
import com.gint.app.bisis.circ.NewMember;
import com.gint.util.file.FileUtils;
import com.gint.util.file.INIFile;
import com.gint.util.string.StringUtils;
import com.gint.util.xml.XMLUtils;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JRViewer;
import warning.RootDocument;
import javax.swing.JCheckBox;


public class Warnings extends JFrame {

	private JPanel jContentPane = null;
	private JPanel mainPanel = null;
	private JPanel jBottom = null;
	private JButton btnPrev = null;
	private JButton btnNext = null;
	private JPanel firstPanel = null;
	private JPanel listPanel = null;
	private JScrollPane jScrollPane = null;
	private JTable tbList = null;
	private JLabel lType = null;
	private JComboBox cmbType = null;
	private JLabel lBranch = null;
	private JComboBox cmbBranch = null;
	private JLabel lBegDate = null;
	private JTextField tfBegDate = null;
	private JLabel lEndDate = null;
	private JTextField tfEndDate = null;
	private JButton btnEdit = null;
	private CardLayout mCardLayout = null;
	private WarningTableModel warningTableModel = null;  //  @jve:decl-index=0:visual-constraint="189,420"
	private int visPanel = 1;
	private RootDocument doc = null;
	private JPanel reportPanel = null;
	private JCheckBox chbSave = null;
	private JLabel lSave = null;
	private DBConnection dbconn = null;
	private JPanel reportListPanel = null;
	private JPanel changePanel = null;
	private ChangeTemplate changeTemplate = null;
	private JButton btnHistory = null;
	private JPanel historyPanel = null;
	private JScrollPane historyScrollPane = null;
	private JTable tbHistory = null;
	//private HistoryTableModel historyTableModel = null;  //  @jve:decl-index=0:visual-constraint=""
	private HistoryTableSorter historyTableSorter = null;
	private TableSorter sorter = null;
	private Vector branchCodes = null;
	private DefaultComboBoxModel cmbTypeModel = null;
	private DefaultComboBoxModel cmbBranchModel = null;
	private Counters counters = null;
	
	
	public Warnings(DBConnection dbconn) {
		super();
		this.dbconn = dbconn;
		initialize();
		loadCombos();
	}

	private void initialize() {
		this.setSize(new java.awt.Dimension(780,550));
		this.setContentPane(getJContentPane());
		this.setTitle("Opomene");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Dimension screen = getToolkit().getScreenSize();
	    this.setLocation((screen.width - getSize().width) / 2,
	        (screen.height - getSize().height) / 2);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setPreferredSize(new java.awt.Dimension(780,550));
			jContentPane.add(getMainPanel(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJBottom(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	private JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel();
			mCardLayout = new CardLayout();
			mainPanel.setLayout(mCardLayout);
			mainPanel.setPreferredSize(new java.awt.Dimension(780,510));
			mainPanel.add(getFirstPanel(), getFirstPanel().getName());
			mainPanel.add(getListPanel(), getListPanel().getName());
			mainPanel.add(getReportPanel(), getReportPanel().getName());
			mainPanel.add(getReportListPanel(), getReportListPanel().getName());
			mainPanel.add(getChangePanel(), getChangePanel().getName());
			mainPanel.add(getHistoryPanel(), getHistoryPanel().getName());
		}
		return mainPanel;
	}

	private JPanel getJBottom() {
		if (jBottom == null) {
			FlowLayout flowLayout2 = new FlowLayout();
			jBottom = new JPanel();
			jBottom.setLayout(flowLayout2);
			jBottom.setPreferredSize(new java.awt.Dimension(780,40));
			flowLayout2.setVgap(5);
			flowLayout2.setHgap(200);
			jBottom.add(getBtnPrev(), null);
			jBottom.add(getBtnNext(), null);
		}
		return jBottom;
	}

	private JButton getBtnPrev() {
		if (btnPrev == null) {
			btnPrev = new JButton();
			btnPrev.setText("<< Nazad");
			btnPrev.setEnabled(false);
			btnPrev.setPreferredSize(new java.awt.Dimension(180,26));
			btnPrev.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					handleBtnPrev();
				}
			});
		}
		return btnPrev;
	}

	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("Prika\u017ei korisnike >>");
			btnNext.setPreferredSize(new java.awt.Dimension(180,26));
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					handleBtnNext();
				}
			});
		}
		return btnNext;
	}

	private JPanel getFirstPanel() {
		if (firstPanel == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.insets = new java.awt.Insets(10,10,10,100);
			gridBagConstraints11.gridy = 2;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.insets = new java.awt.Insets(50,10,10,100);
			gridBagConstraints8.gridy = 0;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints7.gridy = 3;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.insets = new java.awt.Insets(10,0,50,200);
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints6.insets = new java.awt.Insets(10,50,50,10);
			gridBagConstraints6.gridy = 3;
			lEndDate = new JLabel();
			lEndDate.setText("Do datuma");
			lEndDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.insets = new java.awt.Insets(10,0,10,200);
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints4.insets = new java.awt.Insets(10,50,10,10);
			gridBagConstraints4.gridy = 2;
			lBegDate = new JLabel();
			lBegDate.setText("Od datuma");
			lBegDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new java.awt.Insets(10,0,10,100);
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints2.insets = new java.awt.Insets(10,50,10,10);
			gridBagConstraints2.gridy = 1;
			lBranch = new JLabel();
			lBranch.setText("Ogranak");
			lBranch.setHorizontalTextPosition(javax.swing.SwingConstants.TRAILING);
			lBranch.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.insets = new java.awt.Insets(50,0,10,100);
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints.insets = new java.awt.Insets(50,50,10,10);
			gridBagConstraints.gridy = 0;
			lType = new JLabel();
			lType.setText("Vrsta opomene");
			lType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			firstPanel = new JPanel();
			firstPanel.setLayout(new GridBagLayout());
			firstPanel.setName("firstPanel");
			firstPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			firstPanel.setPreferredSize(new java.awt.Dimension(780,510));
			firstPanel.add(lType, gridBagConstraints);
			firstPanel.add(getCmbType(), gridBagConstraints1);
			firstPanel.add(lBranch, gridBagConstraints2);
			firstPanel.add(getCmbBranch(), gridBagConstraints3);
			firstPanel.add(lBegDate, gridBagConstraints4);
			firstPanel.add(getTfBegDate(), gridBagConstraints5);
			firstPanel.add(lEndDate, gridBagConstraints6);
			firstPanel.add(getTfEndDate(), gridBagConstraints7);
			firstPanel.add(getBtnEdit(), gridBagConstraints8);
			firstPanel.add(getBtnHistory(), gridBagConstraints11);
		}
		return firstPanel;
	}

	private JPanel getListPanel() {
		if (listPanel == null) {
			lSave = new JLabel();
			FlowLayout flowLayout1 = new FlowLayout();
			listPanel = new JPanel();
			listPanel.setLayout(flowLayout1);
			listPanel.setName("listPanel");
			listPanel.setPreferredSize(new java.awt.Dimension(780,510));
			listPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			lSave.setText("Sa\u010duvaj podatke o poslanim opomenama u bazu podataka");
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			listPanel.add(getJScrollPane(), null);
			listPanel.add(getChbSave(), null);
			listPanel.add(lSave, null);
		}
		return listPanel;
	}

	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbList());
			jScrollPane.setPreferredSize(new java.awt.Dimension(750,440));
		}
		return jScrollPane;
	}

	private JTable getTbList() {
		if (tbList == null) {
			tbList = new JTable();
			tbList.setShowGrid(true);
			tbList.setModel(getWarningTableModel());
			tbList.setPreferredScrollableViewportSize(new java.awt.Dimension(750,440));
		}
		return tbList;
	}

	private JComboBox getCmbType() {
		if (cmbType == null) {
			cmbType = new JComboBox();
			cmbType.setModel(getCmbTypeModel());
		}
		return cmbType;
	}

	private JComboBox getCmbBranch() {
		if (cmbBranch == null) {
			cmbBranch = new JComboBox();
			cmbBranch.setModel(getCmbBranchModel());
		}
		return cmbBranch;
	}

	private JTextField getTfBegDate() {
		if (tfBegDate == null) {
			tfBegDate = new JTextField();
		}
		return tfBegDate;
	}

	private JTextField getTfEndDate() {
		if (tfEndDate == null) {
			tfEndDate = new JTextField();
		}
		return tfEndDate;
	}

	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("Izmeni tekst...");
			btnEdit.setPreferredSize(new java.awt.Dimension(150,25));
			btnEdit.setMinimumSize(new java.awt.Dimension(150,25));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					handleBtnEdit();
				}
			});
		}
		return btnEdit;
	}

	private WarningTableModel getWarningTableModel() {
		if (warningTableModel == null) {
			warningTableModel = new WarningTableModel();
		}
		return warningTableModel;
	}
	
	private JPanel getReportPanel() {
		if (reportPanel == null) {
			reportPanel = new JPanel();
			reportPanel.setLayout(new BorderLayout());
			reportPanel.setName("reportPanel");
			reportPanel.setPreferredSize(new java.awt.Dimension(780,510));
			reportPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
		}
		return reportPanel;
	}
	
	private JCheckBox getChbSave() {
		if (chbSave == null) {
			chbSave = new JCheckBox();
			chbSave.setSelected(true);
		}
		return chbSave;
	}
	
	private JPanel getReportListPanel() {
		if (reportListPanel == null) {
			reportListPanel = new JPanel();
			reportListPanel.setLayout(new BorderLayout());
			reportListPanel.setName("reportListPanel");
			reportListPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			reportListPanel.setPreferredSize(new java.awt.Dimension(780,510));
		}
		return reportListPanel;
	}

	private JPanel getChangePanel() {
		if (changePanel == null) {
			changePanel = new JPanel();
			changePanel.setName("changePanel");
			changePanel.setPreferredSize(new java.awt.Dimension(780,510));
			changePanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			changePanel.add(getChangeTemplate(), null);
		}
		return changePanel;
	}
	
	private ChangeTemplate getChangeTemplate(){
		if (changeTemplate == null) {
			changeTemplate = new ChangeTemplate(dbconn);
		}
		return changeTemplate;
	}

	private JButton getBtnHistory() {
		if (btnHistory == null) {
			btnHistory = new JButton();
			btnHistory.setText("Istorija...");
			btnHistory.setPreferredSize(new java.awt.Dimension(150,25));
			btnHistory.setMinimumSize(new java.awt.Dimension(150,25));
			btnHistory.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					handleBtnHistory();
				}
			});
		}
		return btnHistory;
	}
	
	private JPanel getHistoryPanel() {
		if (historyPanel == null) {
			historyPanel = new JPanel();
			historyPanel.setName("historyPanel");
			historyPanel.setPreferredSize(new java.awt.Dimension(780,510));
			historyPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
			historyPanel.add(getHistoryScrollPane(), null);
		}
		return historyPanel;
	}

	private JScrollPane getHistoryScrollPane() {
		if (historyScrollPane == null) {
			historyScrollPane = new JScrollPane();
			historyScrollPane.setPreferredSize(new java.awt.Dimension(750,470));
			historyScrollPane.setViewportView(getTbHistory());
		}
		return historyScrollPane;
	}

	private JTable getTbHistory() {
		if (tbHistory == null) {
			tbHistory = new JTable(getHistoryTableModel());
			getHistoryTableModel().setTableHeader(tbHistory.getTableHeader());
		}
		return tbHistory;
	}

	private HistoryTableSorter getHistoryTableModel() {
		if (historyTableSorter == null) {
			historyTableSorter = new HistoryTableSorter(new HistoryTableModel());
			historyTableSorter.setTableHeader(getTbHistory().getTableHeader());
		}
		return historyTableSorter;
	}
	
	private DefaultComboBoxModel getCmbTypeModel() {
		if (cmbTypeModel == null) {
			cmbTypeModel = new DefaultComboBoxModel();
		}
		return cmbTypeModel;
	}
	
	private DefaultComboBoxModel getCmbBranchModel() {
		if (cmbBranchModel == null) {
			cmbBranchModel = new DefaultComboBoxModel();
		}
		return cmbBranchModel;
	}
	
	//ponalazi korisnike koji su u zadatom periodu zaduzili nesto i istekao im je rok vracanja
	//napravi doc fajl i setuje ga WarningTableModelu
	private void getUsers(){
		String ctlg_no = "";
        String cur = "";	        
        String single_id = "";
        int userdays = 0, days = 0;
        int last = 0, cur_no=0;
        boolean newuser = true, warnind = false;
        RootDocument temp = null;
        RootDocument.Root.Opomena template = null;
        Calendar maxEndDate = null;
        double trostruko = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
        String today = sdf.format(new Date());
        boolean cyr = false;
		String wtext = "";
		int warn_no = -1;
		int cur_year = -1;
		
	  if (Utils.checkDates(getTfBegDate().getText().trim(),getTfEndDate().getText().trim())){
		try{
			int branchCode = ((Integer)branchCodes.get(getCmbBranchModel().getIndexOf(getCmbBranchModel().getSelectedItem()))).intValue();
			int warn_type = getCmbTypeModel().getIndexOf(getCmbTypeModel().getSelectedItem());
			
			
			String qry = "select wtext from warning_types where id = " + warn_type;
			Statement stmt = dbconn.getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(qry);
			if (rset.next()){
				wtext = rset.getString(1);
			}
			rset.close();
			counters = new Counters(dbconn, warn_type);
			
			if (branchCode == 0){
				if (getTfBegDate().getText().trim().equals("") && getTfEndDate().getText().trim().equals("")){
					qry = "select single_id, ctlg_no, lend_date, resume_date, lend_type "
						+ "from lending "
			            + "where return_date is null "
						+ "order by single_id";
				}else if (getTfBegDate().getText().trim().equals("")){
					qry = "select single_id, ctlg_no, lend_date, resume_date, lend_type "
						+ "from lending "
			            + "where (lend_date <= '"+ Utils.getDBEndDate(getTfEndDate().getText().trim()) +"' or resume_date <= '"+ Utils.getDBEndDate(getTfEndDate().getText().trim()) + "') "
			            + "and return_date is null "
						+ "order by single_id"; 
				}else if (getTfEndDate().getText().trim().equals("")){
					qry = "select single_id, ctlg_no, lend_date, resume_date, lend_type "
						+ "from lending "
			            + "where (lend_date >= '"+ Utils.getDBStartDate(getTfBegDate().getText().trim()) +"' or resume_date >= '"+ Utils.getDBStartDate(getTfBegDate().getText().trim()) + "') "
			            + "and return_date is null "
						+ "order by single_id"; 
				}else{
					qry = "select single_id, ctlg_no, lend_date, resume_date, lend_type "
						+ "from lending "
			            + "where ((lend_date >= '"+ Utils.getDBStartDate(getTfBegDate().getText().trim()) +"' and lend_date <= '"+ Utils.getDBEndDate(getTfEndDate().getText().trim()) +"') or "
			            + "(resume_date >= '" + Utils.getDBStartDate(getTfBegDate().getText().trim()) + "' and resume_date <= '"+ Utils.getDBEndDate(getTfEndDate().getText().trim()) +"')) "
			            + "and return_date is null "
						+ "order by single_id";
				}
			}else{
				if (getTfBegDate().getText().equals("") && getTfEndDate().getText().equals("")){
					qry = "select single_id, ctlg_no, lend_date, resume_date, lend_type "
						+ "from lending "
			            + "where return_date is null "
			            + "and location = "+ branchCode
						+ " order by single_id";
				}else if (getTfBegDate().getText().trim().equals("")){
					qry = "select single_id, ctlg_no, lend_date, resume_date, lend_type "
						+ "from lending "
			            + "where (lend_date <= '"+ Utils.getDBEndDate(getTfEndDate().getText().trim()) +"' or resume_date <= '"+ Utils.getDBEndDate(getTfEndDate().getText().trim()) + "') "
			            + "and return_date is null "
			            + "and location = "+ branchCode
						+ " order by single_id"; 
				}else if (getTfEndDate().getText().trim().equals("")){
					qry = "select single_id, ctlg_no, lend_date, resume_date, lend_type "
						+ "from lending "
			            + "where (lend_date >= '"+ Utils.getDBStartDate(getTfBegDate().getText().trim()) +"' or resume_date >= '"+ Utils.getDBStartDate(getTfBegDate().getText().trim()) + "') "
			            + "and return_date is null "
			            + "and location = "+ branchCode
						+ " order by single_id"; 
				}else{
					qry = "select single_id, ctlg_no, lend_date, resume_date, lend_type "
						+ "from lending "
						+ "where ((lend_date >= '"+ Utils.getDBStartDate(getTfBegDate().getText().trim()) +"' and lend_date <= '"+ Utils.getDBEndDate(getTfEndDate().getText().trim()) +"') or "
			            + "(resume_date >= '" + Utils.getDBStartDate(getTfBegDate().getText().trim()) + "' and resume_date <= '"+ Utils.getDBEndDate(getTfEndDate().getText().trim()) +"')) "
			            + "and return_date is null "
			            + "and location = "+ branchCode
						+ " order by single_id";
				}
			}
			
	        rset = stmt.executeQuery(qry);	        
	        while (rset.next()){	        	
	        	single_id = rset.getString(1);
	           	if(!cur.equals(single_id)){
	        		cur = single_id;
	        		newuser = true;
	        		warnind = Utils.isSetWarnInd(dbconn.getConnection(), single_id);
	        		userdays = Utils.getUserPeriod(dbconn.getConnection(), single_id);
	        		maxEndDate = null;
	        		trostruko = 0;
	        	}
        		ctlg_no = rset.getString(2);
        		if (warnind){
					days = Utils.getLendPeriod(dbconn.getConnection(), rset.getInt(5));
					days = Utils.criteria(days, userdays);
	
					Timestamp beg = rset.getTimestamp(4);
					if (beg == null) {
						beg = rset.getTimestamp(3);
					}
					if (beg.before(Timestamp.valueOf(Utils.getDBEndDate(getTfEndDate().getText().trim())))){
					  
					
						Calendar now = Calendar.getInstance();
						Calendar endDate = Calendar.getInstance();
						endDate.setTimeInMillis(beg.getTime());
						endDate.add(Calendar.DATE, days);
		
						if (now.after(endDate)) {
							if (maxEndDate == null || endDate.after(maxEndDate)){
								maxEndDate = endDate;
							}
							Utils.getPubData(dbconn.getConnection(), ctlg_no);
							if (newuser){
								newuser = false;
								if (doc == null){
									temp = RootDocument.Factory.parse(wtext);
									template = temp.getRoot().getOpomenaArray(0);
									doc = RootDocument.Factory.newInstance();
									doc.addNewRoot().addNewOpomena();
									doc.getRoot().setOpomenaArray(0,template);
									doc.getRoot().setCirilica(temp.getRoot().getCirilica());
									cur_no = 0;
									if (temp.getRoot().getCirilica() == 1){
										cyr = true;
									}
								
								}else{
									cur_no = doc.getRoot().sizeOfOpomenaArray();
									doc.getRoot().addNewOpomena();
									doc.getRoot().setOpomenaArray(cur_no,template);
								}
								
								cur_year =  maxEndDate.get(Calendar.YEAR) % 100;
								warn_no = counters.getNext(cur_year);
								
								try{
									String q = "select s.last_name, s.first_name, s.parent_name, u.address, u.zipcode, u.city, s.doc_no, s.doc_city, s.jmbg " +
											"from single s, users u " +
											"where u.id = '" + single_id + "' and u.id = s.user_id";
									Statement st = dbconn.getConnection().createStatement();
									ResultSet rs = st.executeQuery(q);
									if (rs.next()){
										if (!doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().getBropomenetext().equals(""))
											doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().setBropomene(warn_no + "/" +StringUtils.padZeros(cur_year,2));
										if (!doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().getRoktext().equals(""))
											doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().setRok(" "+ sdf.format(maxEndDate.getTime()));
										doc.getRoot().getOpomenaArray(cur_no).getBody().setDodatuma(doc.getRoot().getOpomenaArray(cur_no).getBody().getDodatuma() + " " + today);
										doc.getRoot().getOpomenaArray(cur_no).getPodaci().setPrezime(Utils.convert(rs.getString(1),cyr));
										doc.getRoot().getOpomenaArray(cur_no).getPodaci().setIme(Utils.convert(rs.getString(2),cyr));
										if (!doc.getRoot().getOpomenaArray(cur_no).getPodaci().getRoditelj().equals("") && rs.getString(3) != null)
											doc.getRoot().getOpomenaArray(cur_no).getPodaci().setImeroditelja(Utils.convert(rs.getString(3),cyr));
										doc.getRoot().getOpomenaArray(cur_no).getPodaci().setAdresa(Utils.convert(rs.getString(4),cyr));
										doc.getRoot().getOpomenaArray(cur_no).getPodaci().setZip(rs.getString(5));
										doc.getRoot().getOpomenaArray(cur_no).getPodaci().setMesto(Utils.convert(rs.getString(6),cyr));
										doc.getRoot().getOpomenaArray(cur_no).getPodaci().setUserid(single_id);
										if (!doc.getRoot().getOpomenaArray(cur_no).getPodaci().getDocno().equals("") && rs.getString(7) != null)
											doc.getRoot().getOpomenaArray(cur_no).getPodaci().setDocno(doc.getRoot().getOpomenaArray(cur_no).getPodaci().getDocno() + rs.getString(7));
										if (!doc.getRoot().getOpomenaArray(cur_no).getPodaci().getDocmesto().equals("") && rs.getString(8) != null)
											doc.getRoot().getOpomenaArray(cur_no).getPodaci().setDocmesto(doc.getRoot().getOpomenaArray(cur_no).getPodaci().getDocmesto() + Utils.convert(rs.getString(8),cyr));
										if (!doc.getRoot().getOpomenaArray(cur_no).getPodaci().getJmbg().equals("") && rs.getString(9) != null)
											doc.getRoot().getOpomenaArray(cur_no).getPodaci().setJmbg(doc.getRoot().getOpomenaArray(cur_no).getPodaci().getJmbg() + rs.getString(9));
									}
									rs.close();
									st.close();
								}catch(Exception e){
									e.printStackTrace();
								}
							}
							
							if (cur_year != maxEndDate.get(Calendar.YEAR) % 100){
								counters.turnBack(cur_year);
								cur_year =  maxEndDate.get(Calendar.YEAR) % 100;
								warn_no = counters.getNext(cur_year);
							}
							if (!doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().getBropomenetext().equals(""))
								doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().setBropomene(warn_no + "/" +StringUtils.padZeros(cur_year,2));
							if (!doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().getRoktext().equals(""))
								doc.getRoot().getOpomenaArray(cur_no).getZaglavlje().setRok(" "+ sdf.format(maxEndDate.getTime()));
							
							doc.getRoot().getOpomenaArray(cur_no).getBody().addNewTabela();
							last = doc.getRoot().getOpomenaArray(cur_no).getBody().getTabelaArray().length ;
							doc.getRoot().getOpomenaArray(cur_no).getBody().getTabelaArray()[last-1].setNaslov(Utils.convert(Utils.getNaslov(),cyr));
							doc.getRoot().getOpomenaArray(cur_no).getBody().getTabelaArray()[last-1].setAutor(Utils.convert(Utils.getAutor(),cyr));
							doc.getRoot().getOpomenaArray(cur_no).getBody().getTabelaArray()[last-1].setInvbroj(ctlg_no);
							doc.getRoot().getOpomenaArray(cur_no).getBody().getTabelaArray()[last-1].setSignatura(Utils.getSignatura());
							doc.getRoot().getOpomenaArray(cur_no).getBody().getTabelaArray()[last-1].setBrdana(Utils.dayDistance(endDate,now));
							if (!doc.getRoot().getOpomenaArray(cur_no).getFooter().getPojedinacno().equals(""))
								doc.getRoot().getOpomenaArray(cur_no).getFooter().setPojedinacno(doc.getRoot().getOpomenaArray(cur_no).getFooter().getPojedinacno() + " " + Utils.getCena() + ";");
							if (!doc.getRoot().getOpomenaArray(cur_no).getFooter().getTrostrukotext().equals("")){
								try{
									trostruko = trostruko + Double.parseDouble(Utils.getCena())*3;
									doc.getRoot().getOpomenaArray(cur_no).getFooter().setTrostruko(new BigDecimal(trostruko));
								}catch (NumberFormatException e){
									
								}
							}
		
						}
		        	}
        		}
	        }
			rset.close();
			stmt.close();
			getWarningTableModel().setDoc(doc);
		}catch(Exception e){
			e.printStackTrace();
		}
	  }else{
		  JOptionPane.showMessageDialog(this,"Gre\u0161ka u formatu datuma!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
	  }
	}
	
	//proverava da li je bibliotekar odlucio da ne stampa za neke korisnike opomene,
	//izbaci te korisnike i pravi jasper fajlove opomena i spiska opomena
	private void getReport(){
		try{
		if (doc != null){
			int len = doc.getRoot().sizeOfOpomenaArray();
			int j = 0;
			for (int i = 0; i < len; i++){
				if (!getWarningTableModel().printOrNot(i)){
					doc.getRoot().removeOpomena(j);
				}else{
					j++;
				}
			}
			
			boolean cyr = false;
			if (doc.getRoot().getCirilica() == 1){
				cyr = true;
			}
			
			StringWriter sw = new StringWriter();
			XmlOptions xmlOptions = new XmlOptions();
	        xmlOptions.setSavePrettyPrint();
	        doc.save(sw,xmlOptions);
	        JasperReport subreport = (JasperReport)JRLoader.loadObject(
	            Warnings.class.getResource(
	              "/com/gint/app/bisis/circ/warnings/jaspers/details.jasper").openStream());
           JasperReport warning = (JasperReport)JRLoader.loadObject(
   	            Warnings.class.getResource(
   	              "/com/gint/app/bisis/circ/warnings/jaspers/warning.jasper").openStream());
		   Map params = new HashMap(2);
		   params.put("sub", subreport);
		   params.put("warning", warning);
	   
           JRXmlDataSource ds = new JRXmlDataSource(XMLUtils
                   .getDocumentFromString(sw.toString()), "/root/opomena");
           JasperPrint jp = JasperFillManager.fillReport(
                   Warnings.class.getResource(
                       "/com/gint/app/bisis/circ/warnings/jaspers/all.jasper").openStream(), 
                       params, ds);   
           JRViewer jr = new JRViewer(jp);
           getReportPanel().add(jr, java.awt.BorderLayout.CENTER);
           
           
           String naslov = Utils.convert("Spisak " + getCmbTypeModel().getSelectedItem() + " posaltih na dan " + new SimpleDateFormat("dd.MM.yyyy.").format(new Date()), cyr);
           String rbr = Utils.convert("R.br.", cyr);
           String bropomene = Utils.convert("Br.opomene", cyr);
           String brclana = Utils.convert("Br.\u010dlana", cyr);
           String ime = Utils.convert("Prezime i ime", cyr);
           String datum = Utils.convert("Rok vra\u0107anja", cyr);
           //String napomena = Utils.convert("Napomena", cyr);
           Map paramslist = new HashMap(7);
    	   paramslist.put("naslov", naslov);
    	   paramslist.put("rbr", rbr);
    	   paramslist.put("bropomene", bropomene);
    	   paramslist.put("brclana", brclana);
    	   paramslist.put("ime", ime);
    	   paramslist.put("datum", datum);
    	   //paramslist.put("napomena", napomena);
    	   
    	   JRXmlDataSource dslist = new JRXmlDataSource(XMLUtils
                   .getDocumentFromString(sw.toString()), "/root/opomena");
           JasperPrint jplist = JasperFillManager.fillReport(
                   Warnings.class.getResource(
                       "/com/gint/app/bisis/circ/warnings/jaspers/list.jasper").openStream(), 
                       paramslist, dslist);           
           JRViewer jrlist = new JRViewer(jplist);
           getReportListPanel().add(jrlist, java.awt.BorderLayout.CENTER);
		}
           
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	//sacuva podatke o poslatim opomenama u bazu
	private void save(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
		if (doc != null){
			if (getChbSave().isSelected()){
				INIFile iniFile = new INIFile(
			            FileUtils.getClassDir(NewMember.class) + "/config.ini"); 
			  	String location = iniFile.getString("circ", "homeLocation");
			    int len = doc.getRoot().sizeOfOpomenaArray();
			    String user_id = null;
			    String ctlg_no = null;
			    String warn_no = null;
			    String deadline = null;
			    int warn_type = getCmbTypeModel().getIndexOf(getCmbTypeModel().getSelectedItem());
			    
			    counters.finish();
			    
			    String wqry = "insert into warnings (warn_type, user_id, ctlg_no, wdate, deadline, location, warn_no)values ("+warn_type+", ?, ?, ?, ?, "+ location +", ?)";
			    String uqry = "insert into blocked_cards (user_id, reason_id) values (?, 2)";
			    try{
			        PreparedStatement wstmt = dbconn.getConnection().prepareStatement(wqry);
			        PreparedStatement ustmt = dbconn.getConnection().prepareStatement(uqry);
					for (int i = 0; i < len; i++){
					    user_id = doc.getRoot().getOpomenaArray(i).getPodaci().getUserid();
					    warn_no = doc.getRoot().getOpomenaArray(i).getZaglavlje().getBropomene();
					    int len2 = doc.getRoot().getOpomenaArray(i).getBody().sizeOfTabelaArray();
					    deadline = doc.getRoot().getOpomenaArray(i).getZaglavlje().getRok();
					    for (int j = 0; j<len2; j++){
					        ctlg_no = doc.getRoot().getOpomenaArray(i).getBody().getTabelaArray(j).getInvbroj();
					        wstmt.setString(1,user_id);
					        wstmt.setString(2, ctlg_no);
					        wstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
					        wstmt.setTimestamp(4, new Timestamp((sdf.parse(deadline)).getTime()));
					        wstmt.setString(5, warn_no);
					        wstmt.executeUpdate();
					        dbconn.getConnection().commit();
					    }
					    try{
					    	ustmt.setString(1,user_id);
					    	ustmt.executeUpdate();
					    	dbconn.getConnection().commit();
					    }catch(Exception e1){
					    	dbconn.getConnection().rollback();
					    }
					}
					ustmt.close();
					wstmt.close();
			    }catch (Exception e){
			        e.printStackTrace();
			    }
			}else{
				counters.rollbackForUpdate();
			}
		}else{
			counters.rollbackForUpdate();
		}
	}
	
	
	//uzima spisak korisnika iz HistoryTableModela i napravi jasper fajl koji predstavlja registar utuzenja
	private void getHistoryList(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
		boolean cyr = false;
		String naslov = Utils.convert("Registar " + getCmbTypeModel().getSelectedItem(), cyr);
		String period = Utils.convert("period: "+ getTfBegDate().getText()+ " - "+getTfEndDate().getText(),cyr);
        String rbr = Utils.convert("R.br.", cyr);
        String bropomene = Utils.convert("Br.opomene", cyr);
        String brclana = Utils.convert("Br.\u010dlana", cyr);
        String ime = Utils.convert("Prezime i ime", cyr);
        String datum = Utils.convert("Dat. vra\u0107anja", cyr);
        String napomena = Utils.convert("Napomena", cyr);
        String ctlgno = Utils.convert("Inv. broj", cyr);
        
        Map paramslist = new HashMap(7);
 	    paramslist.put("naslov", naslov);
 	    paramslist.put("period", period);
 	    paramslist.put("rbr", rbr);
 	    paramslist.put("bropomene", bropomene);
 	    paramslist.put("brclana", brclana);
 	    paramslist.put("ime", ime);
 	    paramslist.put("datum", datum);
 	    paramslist.put("napomena", napomena);
 	    paramslist.put("ctlgno", ctlgno);
 	   
 	    try{
 	       String brop = "";
 	       list.RootDocument doc = list.RootDocument.Factory.newInstance();
 	       doc.addNewRoot();
 	       int curNo1 = 0;
 	       int curNo2 = 0;
 	       for (int i = 0; i< getHistoryTableModel().getRowCount(); i++){
 	 	      	if (getHistoryTableModel().getValueAt(i,0)==null || !brop.equals(getHistoryTableModel().getValueAt(i,0))){
 	 	      		doc.getRoot().addNewOpomena();
 	 	      		curNo1 = doc.getRoot().getOpomenaArray().length - 1;
 	 	      		doc.getRoot().getOpomenaArray(curNo1).setBropomene((String)getHistoryTableModel().getValueAt(i,0));
 	 	      	    doc.getRoot().getOpomenaArray(curNo1).setUserid((String)getHistoryTableModel().getValueAt(i,4));
 	 	      	    doc.getRoot().getOpomenaArray(curNo1).setIme((String)getHistoryTableModel().getValueAt(i,5));
 	 	      	    brop = (String)getHistoryTableModel().getValueAt(i,0);
 	 	      	}
 	 	        doc.getRoot().getOpomenaArray(curNo1).addNewKnjiga();
 	 	        curNo2 = doc.getRoot().getOpomenaArray(curNo1).getKnjigaArray().length - 1;
 	 	        doc.getRoot().getOpomenaArray(curNo1).getKnjigaArray(curNo2).setCtlgno((String)getHistoryTableModel().getValueAt(i,6));
 	 	        if (getHistoryTableModel().getValueAt(i,7) != null){
 	 	        	doc.getRoot().getOpomenaArray(curNo1).getKnjigaArray(curNo2).setDatum(sdf.format(getHistoryTableModel().getValueAt(i,7)));
 	 	        }
 	 	        if (getHistoryTableModel().getValueAt(i,8) != null){
 	 	        	doc.getRoot().getOpomenaArray(curNo1).getKnjigaArray(curNo2).setNapomena((String)getHistoryTableModel().getValueAt(i,8));
 	 	        }
 	 	        
 	       }
 	       
 	      StringWriter sw = new StringWriter();
	      XmlOptions xmlOptions = new XmlOptions();
	      xmlOptions.setSavePrettyPrint();
	      doc.save(sw,xmlOptions);
	      

	    JasperReport subreport = (JasperReport)JRLoader.loadObject(
	            Warnings.class.getResource(
	              "/com/gint/app/bisis/circ/warnings/jaspers/subHistoryList.jasper").openStream());
	    paramslist.put("subreport", subreport);
	    
 	    JRXmlDataSource dslist = new JRXmlDataSource(XMLUtils
                .getDocumentFromString(sw.toString()), "/root/opomena");
 	    
        JasperPrint jplist = JasperFillManager.fillReport(
                Warnings.class.getResource(
                    "/com/gint/app/bisis/circ/warnings/jaspers/historyList.jasper").openStream(), 
                    paramslist, dslist);           
        JRViewer jrlist = new JRViewer(jplist);
        getReportListPanel().add(jrlist, java.awt.BorderLayout.CENTER);
        
 	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}

	
	
	private void loadCombos(){
		try{
			String qry = "select id, name from branch";
			Statement stmt = dbconn.getConnection().createStatement();
	        ResultSet rset = stmt.executeQuery(qry);
	        branchCodes = new Vector();
	        while (rset.next()){
	        	branchCodes.add(new Integer(rset.getInt(1)));
	        	getCmbBranchModel().addElement(rset.getString(2));
	        }
	        rset.close();
	        
	        qry = "select id, name from warning_types";
	        rset = stmt.executeQuery(qry);
	        while (rset.next()){
	        	getCmbTypeModel().addElement(rset.getString(2));
	        	
	        }
	        rset.close();
	        stmt.close();
	        
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	private void handleBtnHistory(){
		if (Utils.checkDates(getTfBegDate().getText().trim(), getTfEndDate().getText().trim())){
			getHistoryTableModel().setContent(getTfBegDate().getText(), getTfEndDate().getText(),((Integer)branchCodes.get(getCmbBranchModel().getIndexOf(getCmbBranchModel().getSelectedItem()))).intValue(),getCmbTypeModel().getIndexOf(getCmbTypeModel().getSelectedItem()), dbconn);
			getBtnNext().setText("Registar");
			getBtnPrev().setEnabled(true);
			visPanel = 6;
			showPanel("historyPanel");
		}else{
			JOptionPane.showMessageDialog(this,"Gre\u0161ka u formatu datuma!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void handleBtnPrev(){
		if (visPanel == 5 && getChangeTemplate().isDirty()){
			int answer = JOptionPane.showConfirmDialog(
		              getMainPanel(), 
		              "Menjali ste podatke! Da li \u017eelite da ih sa\u010duvate?", 
		              "Info", JOptionPane.YES_NO_OPTION, 
		              JOptionPane.QUESTION_MESSAGE);
		    if (answer == JOptionPane.YES_OPTION)
		    	getChangeTemplate().save();
		}
		btnPrev.setEnabled(false);
		getBtnNext().setEnabled(true);
		getBtnNext().setText("Prika\u017ei korisnike >>");
		getWarningTableModel().resetModel();
		getHistoryTableModel().resetModel();
		doc = null;
		getReportPanel().removeAll();
		getReportListPanel().removeAll();
		showPanel("firstPanel");
		visPanel = 1;
		if (counters != null){
		    counters.rollbackForUpdate();
		}
	}
	
	private void handleBtnNext(){
		switch (visPanel){
		case 1:	getUsers();
				getBtnPrev().setEnabled(true);
				btnNext.setText("Prika\u017ei opomene >>");
				showPanel("listPanel");
				visPanel = 2;
				break;
		case 2: getReport();
			    save();
				btnNext.setText("Spisak");
				showPanel("reportPanel");
		        visPanel = 3;
				break;
		case 3: btnNext.setText("Opomene");
			    showPanel("reportListPanel");
			    visPanel = 4;
			    break;
		case 4: btnNext.setText("Spisak");
			    showPanel("reportPanel");
			    visPanel = 3;
			    break;
		case 5: if (getChangeTemplate().isDirty()){
					getChangeTemplate().save();
				}
				break;
		case 6: getHistoryList();
			    showPanel("reportListPanel");
			    getBtnNext().setEnabled(false);
	    		visPanel = 3;
	    		break;
		}
	}
	
	private void handleBtnEdit(){
		getChangeTemplate().setContent(getCmbTypeModel().getIndexOf(getCmbTypeModel().getSelectedItem()));
		getBtnNext().setText("Sa\u010duvaj");
		getBtnPrev().setEnabled(true);
		visPanel = 5;
		showPanel("changePanel");
	}
	
	public void showPanel(String name){
		mCardLayout.show(getMainPanel(),name);
	}


	
 }
