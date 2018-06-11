package com.gint.app.bisis.circ;

import java.sql.Types;

public class Column{

  private String name;
  private String caption;
  private int dataType;
  private boolean rowId;  
  private boolean required;
  private boolean editable;
  private int position;
  private boolean sequence;
  private String seqVar;

  public Column(){
    name = new String("Default");
    caption = new String("Default");
    dataType = Types.OTHER;
    required = false;
    rowId = false;
    editable = true;
    position = -1;
  }

  public Column(String name, String caption, int dataType, int position){
    this.name = name;
    this.caption = caption;
    this.dataType = dataType;
    this.position = position;
    required = false;
    rowId = false;
    editable = true;
  }

  public Column(String name, int dataType, int position){
    this(name,name,dataType,position);
  }

  public Column(String name, int dataType){
    this(name,name,dataType,-1);
  }

  public String getName(){
    return name;
  }

  public String getCaption(){
    return caption;
  }  

  public int getDataType(){
    return dataType;
  }

  public int getPosition(){
    return position;
  }

  public boolean isRowId(){
    return rowId;
  }  

  public boolean isRequired(){
    return required;
  }  

  public boolean isEditable(){
    return editable;
  }  
  
  public void setName(String name){
    this.name = name;
  }

  public void setCaption(String caption){
    this.caption = caption;
  }  

  public void setDataType(int dataType){
    this.dataType = dataType;
  }

  public void setPosition(int position){
    this.position = position;
  }

  public void setRowId(boolean rowId){
    this.rowId = rowId;
  }  

  public void setRequired(boolean required){
    this.required = required;
  }  

  public void setEditable(boolean editable){
    this.editable = editable;
  }

  public String toString(){
    return new String("NAME:"+name+" DATATYPE:"+dataType+" POSITION:"+position+" KEY:"+rowId);
  }

  public void setSequence(String name){
    seqVar = new String(name);
    sequence = true;
  }

  public boolean isSequence(){
    return sequence;
  }

  public String getSequence(){
    return seqVar;
  }
  
  public boolean equals(Column col){
//    if(name.equals(col.getName()) && dataType == col.getDataType())
    if(dataType == col.getDataType())
      return true;
    else
      return false;
  }
}
