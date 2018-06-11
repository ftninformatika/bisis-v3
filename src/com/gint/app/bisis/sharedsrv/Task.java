package com.gint.app.bisis.sharedsrv;

public interface Task {
  public void setLogTreshold(int logTreshold);
  public void log(String msg);
  public void start();
}