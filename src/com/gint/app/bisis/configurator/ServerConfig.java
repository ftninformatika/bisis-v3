package com.gint.app.bisis.configurator;
/**
 * 
 * Represents a current BISIS server configuration.
 * 
 * @author Branko Milosavljevic, mbranko@uns.ns.ac.yu
 */
public class ServerConfig extends Config {
  

  public ServerConfig(String locale, String dbType, String driver, String URL,
      String username, String password, String validator, String serverType,
      String reportType, String libUsername, String libPassword) {

    super(locale, dbType, driver, URL, username, password, validator, 
      serverType, reportType);
    this.libUsername = libUsername;
    this.libPassword = libPassword;
  }


  public String getLibUsername() {
    return libUsername;
  }
  
  public String getLibPassword() {
    return libPassword;
  }
  
  public void setLibUsername(String s) {
    libUsername = s;
  }
  
  public void setLibPassword(String s) {
    libPassword = s;
  }
  
  private String libUsername;
  private String libPassword;
}
