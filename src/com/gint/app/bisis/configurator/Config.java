package com.gint.app.bisis.configurator;

/**
 * Represents a current BISIS client configuration.
 * 
 * @author Branko Milosavljevic, mbranko@uns.ns.ac.yu
 */
public class Config {

  public Config(String locale, String dbType, String driver, String URL,
      String username, String password, String validator, String serverType,
      String reportType) {
    this.locale = locale;
    this.dbType = dbType;
    this.driver = driver;
    this.URL = URL;
    this.username = username;
    this.password = password;
    this.validator = validator;
    this.serverType = serverType;
    this.reportType = reportType;
  }
  
  public String getLocale() {
    return locale;
  }
  
  public String getDbType() {
    return dbType;
  }
  
  public String getDriver() {
    return driver;
  }
  
  public String getURL() {
    return URL;
  }
  
  public String getUsername() {
    return username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public String getValidator() {
    return validator;
  }
  
  public String getServerType() {
    return serverType;
  }
  
  public String getReportType() {
    return reportType;
  }
  
  public void setLocale(String s) {
    locale = s;
  }
  
  public void setDbType(String s) {
    dbType = s;
  }
  
  public void setDriver(String s) {
    driver = s;
  }
  
  public void setURL(String s) {
    URL = s;
  }
  
  public void setUsername(String s) {
    username = s;
  }

  public void setPassword(String s) {
    password = s;
  }
  
  public void setValidator(String s) {
    validator = s;
  }
  
  public void setServerType(String s) {
    serverType = s;
  }
  
  public void setReportType(String s) {
    reportType = s;
  }
  
  private String locale;
  private String dbType;
  private String driver;
  private String URL;
  private String username;
  private String password;
  private String validator;
  private String serverType;
  private String reportType;
}
