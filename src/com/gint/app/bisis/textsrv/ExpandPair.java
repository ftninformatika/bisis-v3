package com.gint.app.bisis.textsrv;

import java.io.*;

public class ExpandPair implements Serializable {

  public ExpandPair(String content, int count) {
    this.content = content;
    this.count = count;
  }

  public String getContent() {
    return content;
  }

  public int getCount() {
    return count;
  }

  private String content;
  private int count;
}