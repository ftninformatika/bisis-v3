package com.gint.app.bisis.circ.reports;

import java.awt.print.*;

import com.gint.util.file.FileUtils;
import com.gint.util.file.INIFile;

public class Paper extends PageFormat // DINA4 is a European paper format
{
  static double x0 = 20;
  static double y0 = 20;
  static int Height; 
  static int Width;

  public double getHeight() {
    return (double) Height;
  }

  public double getImageableHeight() {
    return (double) Height - 40;
  }

  public double getWidth() {
    return (double) Width;
  }

  public double getImageableWidth() {
    return (double) Width - 40;
  }

  public double getImageableX() {
    return x0;
  }

  public double getImageableY() {
    return y0;
  }
  
  static {
    INIFile iniFile = new INIFile(
        FileUtils.getClassDir(Paper.class) + "/config.ini"); 
    
    String tmp = iniFile.getString("circ", "height");
    try{
    	Height = Integer.parseInt(tmp);
    }catch (Exception e){
    	Height = 842;
    }
    tmp = iniFile.getString("circ", "width");
    try{
    	Width = Integer.parseInt(tmp);
    }catch (Exception e){
    	Width = 595;
    }
  }
}