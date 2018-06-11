package com.gint.app.bisis.textsrv;

public class SubqueryHit {

  public SubqueryHit(String prefix, String content, int count) {
    this.prefix = prefix;
    this.content = content;
    this.count = count;
  }

  public String getPrefix() {
    return prefix;
  }

  public String getContent() {
    return content;
  }

  public int getCount() {
    return count;
  }

  private String prefix;
  private String content;
  private int count;
} 