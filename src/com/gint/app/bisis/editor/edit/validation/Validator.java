package com.gint.app.bisis.editor.edit.validation;

import java.util.Vector;
import com.gint.app.bisis.editor.edit.*;

public interface Validator {
  public boolean isValid(Field f, Subfield sf, String content) throws Exception;
  public boolean isValid(Field f, Subfield sf, Subfield ssf, String content, boolean hasSubsubfields) throws Exception;
  public String preAdjust(String id, Field f, String oldValue, Vector fields);
  public String postAdjust(String id, Field f, String oldValue, Vector fields);
} 
