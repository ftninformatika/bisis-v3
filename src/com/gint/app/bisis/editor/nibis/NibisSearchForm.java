package com.gint.app.bisis.editor.nibis;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.axis.AxisFault;

import com.gint.app.bisis.editor.RezanceUtilities;
import com.gint.app.bisis.editor.edit.Field;
import com.gint.app.bisis.editor.edit.Subfield;
import com.gint.app.bisis.editor.nibis.binding.HelloIF;
import com.gint.app.bisis.editor.nibis.binding.MojservisLocator;
import com.gint.util.gui.WindowUtils;

public class NibisSearchForm extends javax.swing.JPanel {
	JLabel jOperatorLabel;
	JComboBox jOperatorComboBox;
	JButton jTakeOverButton;
	JButton jSearch;
	JButton jNext;
	JButton jPrevious;
	JTextArea jResults;
	JLabel jYearToLabel;
	JTextField jYearToField;
	JTextField jAuthorField;
	JLabel jPublisherLabel;
	JTextField jYearFromField;
	JLabel jTitleLabel;
	JComboBox jCyrillicComboBox;
	JLabel jCyrillicLabel;
	JMenuItem jMenuItem1;
	JLabel jYear1Label;
	JTextField jPublisherField;
	JLabel jKeywordsLabel;
	JTextField jKeyWords;
	JTextField jTitleField;
	JLabel jISBNLabel;
	JLabel jAuthorLabel;
	JLabel jResultCount;
	JTextField jTextField1;
	JTextField jISBN;
	JScrollPane jTextHolderPane;
  
	//improvizacija
	String searchResults[] = null;
	int activeIndex = 0;

	public NibisSearchForm() {
		initGUI();
	}
  
	/**
	* Initializes the GUI.
	* Auto-generated code - any changes you make will disappear.
	*/
	public void initGUI() {
		try {
			preInitGUI();
			jNext = new JButton();
			jCyrillicLabel = new JLabel();
			jISBN = new JTextField();
			jAuthorLabel = new JLabel();
			jISBNLabel = new JLabel();
			jTitleField = new JTextField();
			jKeyWords = new JTextField();
			jKeywordsLabel = new JLabel();
			jPublisherField = new JTextField();
			jYear1Label = new JLabel();
			jCyrillicComboBox = new JComboBox();
			jTitleLabel = new JLabel();
			jYearFromField = new JTextField();
			jPublisherLabel = new JLabel();
			jAuthorField = new JTextField();
			jYearToField = new JTextField();
			jYearToLabel = new JLabel();
			jResults = new JTextArea();
			jPrevious = new JButton();
			jSearch = new JButton();
			jTakeOverButton = new JButton();
			jOperatorComboBox = new JComboBox();
			jOperatorLabel = new JLabel();
			jResultCount = new JLabel();
			jTextHolderPane = new JScrollPane();
			this.setLayout(null);
			this.setPreferredSize(new java.awt.Dimension(597, 420));
			this.setName("Nibis");
			jNext.setEnabled(false);
			jNext.setText("slede\u0107i >>");
			//jNext.setLabel("slede\u0107i >>");
			jNext.setVisible(true);
			jNext.setPreferredSize(new java.awt.Dimension(100, 20));
			jNext.setBounds(new java.awt.Rectangle(121, 386, 100, 20));
			this.add(jNext);
			jNext.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					jNextMousePressed(evt);
				}
			});
			jCyrillicLabel.setLayout(null);
			jCyrillicLabel.setText("\u0106irilica:");
			jCyrillicLabel.setVisible(true);
			jCyrillicLabel.setPreferredSize(new java.awt.Dimension(60, 20));
			jCyrillicLabel.setBounds(new java.awt.Rectangle(450, 11, 60, 20));
			this.add(jCyrillicLabel);
			jISBN.setVisible(true);
			jISBN.setPreferredSize(new java.awt.Dimension(240, 20));
			jISBN.setAutoscrolls(false);
			jISBN.setBounds(new java.awt.Rectangle(155, 10, 240, 20));
			this.add(jISBN);
			jAuthorLabel.setLayout(null);
			jAuthorLabel.setText("Autor:");
			jAuthorLabel.setVisible(true);
			jAuthorLabel.setPreferredSize(new java.awt.Dimension(60, 20));
			jAuthorLabel.setBounds(new java.awt.Rectangle(10, 40, 60, 20));
			this.add(jAuthorLabel);
			jISBNLabel.setLayout(null);
			jISBNLabel.setText("ISBN:");
			jISBNLabel.setVisible(true);
			jISBNLabel.setPreferredSize(new java.awt.Dimension(60, 20));
			jISBNLabel.setBounds(new java.awt.Rectangle(10, 10, 60, 20));
			this.add(jISBNLabel);
			jTitleField.setVisible(true);
			jTitleField.setPreferredSize(new java.awt.Dimension(240, 20));
			jTitleField.setBounds(new java.awt.Rectangle(155, 71, 240, 20));
			this.add(jTitleField);
			jKeyWords.setVisible(true);
			jKeyWords.setPreferredSize(new java.awt.Dimension(240, 20));
			jKeyWords.setBounds(new java.awt.Rectangle(155, 104, 240, 20));
			this.add(jKeyWords);
			jKeywordsLabel.setLayout(null);
			jKeywordsLabel.setText("Klju\u010dne re\u010di:");
			jKeywordsLabel.setVisible(true);
			jKeywordsLabel.setPreferredSize(new java.awt.Dimension(80, 20));
			jKeywordsLabel.setBounds(new java.awt.Rectangle(10, 104, 80, 20));
			this.add(jKeywordsLabel);
			jPublisherField.setVisible(true);
			jPublisherField.setPreferredSize(new java.awt.Dimension(240, 20));
			jPublisherField.setBounds(
				new java.awt.Rectangle(155, 134, 240, 20));
			this.add(jPublisherField);
			jYear1Label.setLayout(null);
			jYear1Label.setText("Godina izdavanja (od):");
			jYear1Label.setVisible(true);
			jYear1Label.setPreferredSize(new java.awt.Dimension(140, 20));
			jYear1Label.setBounds(new java.awt.Rectangle(10, 165, 140, 20));
			this.add(jYear1Label);
			jCyrillicComboBox.addItem("ne");
			jCyrillicComboBox.addItem("da");
			jCyrillicComboBox.setVisible(true);
			jCyrillicComboBox.setPreferredSize(new java.awt.Dimension(60, 20));
			jCyrillicComboBox.setFocusable(false);
			jCyrillicComboBox.setBounds(
				new java.awt.Rectangle(522, 10, 60, 20));
			this.add(jCyrillicComboBox);
			jTitleLabel.setLayout(null);
			jTitleLabel.setText("Naslov:");
			jTitleLabel.setVisible(true);
			jTitleLabel.setPreferredSize(new java.awt.Dimension(60, 20));
			jTitleLabel.setBounds(new java.awt.Rectangle(10, 70, 60, 20));
			this.add(jTitleLabel);
			jYearFromField.setVisible(true);
			jYearFromField.setPreferredSize(new java.awt.Dimension(60, 20));
			jYearFromField.setBounds(new java.awt.Rectangle(155, 165, 60, 20));
			this.add(jYearFromField);
			jPublisherLabel.setLayout(null);
			jPublisherLabel.setText("Izdava\u010d:");
			jPublisherLabel.setVisible(true);
			jPublisherLabel.setPreferredSize(new java.awt.Dimension(60, 20));
			jPublisherLabel.setBounds(new java.awt.Rectangle(10, 134, 60, 20));
			this.add(jPublisherLabel);
			jAuthorField.setVisible(true);
			jAuthorField.setPreferredSize(new java.awt.Dimension(240, 20));
			jAuthorField.setBounds(new java.awt.Rectangle(155, 40, 240, 20));
			this.add(jAuthorField);
			jYearToField.setVisible(true);
			jYearToField.setPreferredSize(new java.awt.Dimension(60, 20));
			jYearToField.setBounds(new java.awt.Rectangle(155, 198, 60, 20));
			this.add(jYearToField);
			jYearToLabel.setLayout(null);
			jYearToLabel.setText("Godina izdavanja (do):");
			jYearToLabel.setVisible(true);
			jYearToLabel.setPreferredSize(new java.awt.Dimension(140, 20));
			jYearToLabel.setBounds(new java.awt.Rectangle(10, 198, 140, 20));
			this.add(jYearToLabel);
			jTextHolderPane.setVisible(true);
			jTextHolderPane.setPreferredSize(new java.awt.Dimension(575, 141));
			jTextHolderPane.setBounds(new java.awt.Rectangle(10, 233, 575, 141));
			this.add(jTextHolderPane);
			jResults.setEditable(false);
			jResults.setVisible(true);
			jTextHolderPane.add(jResults);
			jTextHolderPane.setViewportView(jResults);
			jPrevious.setEnabled(false);
			jPrevious.setText("<< prethodni");
			jPrevious.setLabel("<< prethodni");
			jPrevious.setMargin(new Insets(2, 2, 2, 2));
			jPrevious.setVisible(true);
			jPrevious.setPreferredSize(new java.awt.Dimension(100, 20));
			jPrevious.setBounds(new java.awt.Rectangle(12, 386, 100, 20));
			this.add(jPrevious);
			jPrevious.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					jPreviousMousePressed(evt);
				}
			});
			jResultCount.setText("");
			jResultCount.setVisible(false);
			jResultCount.setPreferredSize(new java.awt.Dimension(160, 20));
			jResultCount.setBounds(new java.awt.Rectangle(240, 386, 160, 20));
			this.add(jResultCount);


			jSearch.setText("Tra\u017ei");
			jSearch.setLabel("Tra\u017ei");
			jSearch.setVisible(true);
			jSearch.setPreferredSize(new java.awt.Dimension(100, 20));
			jSearch.setBounds(new java.awt.Rectangle(485, 199, 100, 20));
			this.add(jSearch);
			jSearch.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					jSearchMousePressed(evt);
				}
			});
			jTakeOverButton.setText("Preuzmi zapis");
			jTakeOverButton.setLabel("Preuzmi zapis");
			jTakeOverButton.setVisible(true);
			jTakeOverButton.setPreferredSize(new java.awt.Dimension(120, 20));
			jTakeOverButton.setFocusable(false);
			jTakeOverButton.setBounds(
				new java.awt.Rectangle(464, 386, 120, 20));
			this.add(jTakeOverButton);
			jTakeOverButton.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					jTakeOverButtonMousePressed(evt);
				}
			});
			jOperatorComboBox.addItem("=");
			jOperatorComboBox.addItem("<");
			jOperatorComboBox.addItem(">");
			jOperatorComboBox.addItem("opseg");
			jOperatorComboBox.setVisible(true);
			jOperatorComboBox.setPreferredSize(new java.awt.Dimension(60, 20));
			jOperatorComboBox.setBounds(
				new java.awt.Rectangle(522, 40, 60, 20));
			this.add(jOperatorComboBox);
			jOperatorLabel.setLayout(null);
			jOperatorLabel.setText("Operator:");
			jOperatorLabel.setVisible(true);
			jOperatorLabel.setPreferredSize(new java.awt.Dimension(60, 20));
			jOperatorLabel.setBounds(new java.awt.Rectangle(450, 40, 60, 20));
			this.add(jOperatorLabel);
			postInitGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	* Add your pre-init code in here
	*/
	public void preInitGUI() {
	}
	/**
	* Add your post-init code in here
	*/
	public void postInitGUI() {
    jResults.setFont(new Font("monospaced", Font.PLAIN, 12));
	}


	/**
	* Auto-generated code - any changes you make will disappear!!!
	* This static method creates a new instance of this class and shows
	* it inside a new JFrame, (unless it is already a JFrame).
	*/
	public void showGUI(JFrame parent) {
		try {
			container = new JDialog(parent, "NIBIS pretraga i preuzimanje", true);
			container.setContentPane(this);
			container.pack();
      WindowUtils.centerOnScreen(container);
      recordPicked = false;
			container.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** Auto-generated event handler method */
	public void jTakeOverButtonMousePressed(MouseEvent evt) {
		recordPicked = true;
    container.setVisible(false);
	}

	/** Auto-generated event handler method */
	public void jSearchMousePressed(MouseEvent evt) {
		jResults.setText("Obradjujem zahtev");
		jNext.setEnabled(false);
		jPrevious.setEnabled(false);
		jTakeOverButton.setEnabled(false);
		jResultCount.setVisible(false);

		try {
			System.setProperty("http.proxyHost", proxyHost);
			System.setProperty("http.proxyPort", proxyPort);
			MojservisLocator service = new MojservisLocator();
			service.setHelloIFPortAddress(nibisService);
			HelloIF port = service.getHelloIFPort();
			//set query parameters
			String isbn = jISBN.getText();
			if (isbn.trim().equals(""))
				isbn = null;
			String author = jAuthorField.getText().trim();
			String title = jTitleField.getText().trim();
			String keywords = jKeyWords.getText().trim();
			String publisher = jPublisherField.getText().trim();
			String yearfrom = jYearFromField.getText().trim();
			String yearto = jYearFromField.getText().trim();
			String operator = "" + jOperatorComboBox.getSelectedIndex();
			String cir = null;
			if (jCyrillicComboBox.getSelectedIndex() == 1)
				cir = "ON";
			String result =
				port.mojservis(
					isbn,
					cir,
					author,
					title,
					keywords,
					publisher,
					yearfrom,
					yearto,
					operator);
			if (result != null && !result.equals("")) {
				searchResults = Nibis2Bisis.convert(result);
				jTakeOverButton.setEnabled(true);
				activeIndex = 0;
				jResults.setText(formatRecord(searchResults[activeIndex]));
        jResults.setCaretPosition(0);
				jResultCount.setText(
					"Rezultat: "
						+ (activeIndex + 1)
						+ " od "
						+ searchResults.length);
				jResultCount.setVisible(true);
				if (searchResults.length > 1) {
					jNext.setEnabled(true);
				}
			} else {
				jResults.setText("Nema rezultata!");
			}
		} catch (javax.xml.rpc.ServiceException se) {
			jResults.setText("Greska pri inicijalizaciji servisa!");
			se.printStackTrace();
		} catch (AxisFault af) {
			jResults.setText("Greska pri izvrsenju upita!\n" + af);
			//af.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
      jResults.setText("Greska prilikom izvrsenja upita!\n" + e);
		}
	}

	/** Auto-generated event handler method */
	public void jNextMousePressed(MouseEvent evt) {
		if (jNext.isEnabled()) {
			try {
				jResults.setText(formatRecord(searchResults[activeIndex + 1]));
        jResults.setCaretPosition(0);
				activeIndex++;
				jPrevious.setEnabled(true);
				if (activeIndex == (searchResults.length - 1))
					jNext.setEnabled(false);
				jResultCount.setText(
					"Rezultat: "
						+ (activeIndex + 1)
						+ " od "
						+ searchResults.length);
			} catch (ArrayIndexOutOfBoundsException aiob) {
				//do nothing
			}
		}
	}

	/** Auto-generated event handler method */
	public void jPreviousMousePressed(MouseEvent evt) {
		if (jPrevious.isEnabled()) {
			try {
				jResults.setText(formatRecord(searchResults[activeIndex - 1]));
        jResults.setCaretPosition(0);
				activeIndex--;
				jNext.setEnabled(true);
				if (activeIndex == 0)
					jPrevious.setEnabled(false);
				jResultCount.setText(
					"Rezultat: "
						+ (activeIndex + 1)
						+ " od "
						+ searchResults.length);
			} catch (ArrayIndexOutOfBoundsException aiob) {
				//do nothing
			}
		}
	}
  private JDialog container;
  private String proxyHost;
  private String proxyPort;
  private String nibisService;
  private Vector selectedRecord;
  private boolean recordPicked;

  public String getNibisService() {
    return nibisService;
  }
  public String getProxyHost() {
    return proxyHost;
  }
  public String getProxyPort() {
    return proxyPort;
  }
  public void setNibisService(String string) {
    nibisService = string;
  }
  public void setProxyHost(String string) {
    proxyHost = string;
  }
  public void setProxyPort(String string) {
    proxyPort = string;
  }
  public Vector getSelectedRecord() {
    return selectedRecord;
  }
  public boolean isRecordPicked() {
    return recordPicked;
  }

  private String formatRecord(String record) {
    StringBuffer retVal = new StringBuffer();
    Vector fields = RezanceUtilities.unpackRezance(record);
    for (int i = 0; i < fields.size(); i++) {
      Field currField = (Field)fields.elementAt(i);
      retVal.append(currField.getName());
      retVal.append(" ");
      retVal.append(currField.getInd1().replace(' ', '#'));
      retVal.append(currField.getInd2().replace(' ', '#'));
      retVal.append(" ");
      Vector subfields = currField.getSubfields();
      for (int j = 0; j < subfields.size(); j++) {
        Subfield sf = (Subfield)subfields.elementAt(j);
        retVal.append("[");
        retVal.append(sf.getName());
        retVal.append("]");
        Vector secFields = sf.getSecFieldInVect();
        Vector subsubfields = sf.getSubsubfields();
        if (secFields.size() > 0) {
          for (int k = 0; k < secFields.size(); k++) {
            Field secF = (Field)secFields.elementAt(k);
            retVal.append(secF.getName());
            retVal.append(currField.getInd1().replace(' ', '#'));
            retVal.append(currField.getInd2().replace(' ', '#'));
            Vector secSubfields = secF.getSubfields();
            for (int l = 0; l < secSubfields.size(); l++) {
              Subfield secSF = (Subfield)secSubfields.elementAt(l);
              retVal.append("[");
              retVal.append(secSF.getName());
              retVal.append("]");
              retVal.append(secSF.getContent().replace('\r', ' ').replace('\n', ' '));
            }
          }
        } else if (subsubfields.size() > 0) {
          for (int k = 0; k < subsubfields.size(); k++) {
            Subfield ssf = (Subfield)subsubfields.elementAt(k);
            retVal.append("<");
            retVal.append(ssf.getName());
            retVal.append(">");
            retVal.append(ssf.getContent().replace('\r', ' ').replace('\n', ' '));
          }
        } else {
          retVal.append(sf.getContent().replace('\r', ' ').replace('\n', ' '));
        }
      }
      retVal.append("\n");
    }
    selectedRecord = fields;
    return retVal.toString();
  }

}
