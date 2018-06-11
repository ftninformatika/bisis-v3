package com.gint.app.bisis.circ;

import javax.swing.*;
import java.awt.event.*;
/*
  Kod ove komponente je potrebno da se programski odradi setData metoda,
  odnosno snimanje podataka u bazu
*/

public class JDBComboBox extends JComboBox {
  private JDBComboBoxModel comboModel = null;

  public JDBComboBox(){
    super();
  }

  public JDBComboBox(JDBComboBoxModel cModel){
    super();
    setComboModel(cModel);
  }

  public JDBComboBox(DataSet dataSet, String column){
    super();
    comboModel = new JDBComboBoxModel(dataSet, column);
    setComboModel(comboModel);
  }

  public void setComboModel(JDBComboBoxModel cModel){
    comboModel =  cModel;
    setModel(comboModel);

    addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(!comboModel.isDataChanged()) comboModel.setDataChanged(true);
        comboModel.post();
      }
    });
  }
}
