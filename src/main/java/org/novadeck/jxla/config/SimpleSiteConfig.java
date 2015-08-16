package org.novadeck.jxla.config;


import org.novadeck.jxla.data.RegexpData;
import org.novadeck.jxla.tools.Utils;


/**
 *
 */
public class SimpleSiteConfig implements AbstractSiteConfig {


  public SimpleSiteConfig() {
  }

  public String getStatsDirectory(String hostname) {
    return System.getProperty("outputdir");
  }

  public String getRealHostName(String hostname) {
    return System.getProperty("hostname");
  }

  public String getRealUserInfo(String user) {
    return user;
  }


  public boolean ignoreLine( RegexpData re ) {
    String ip = re.getRemoteIP();
    return ( ip.startsWith( "127." ));
  }


  /** Retrieve the directory where to store a summary of requests to the differents
   * websites in the platform.
   */
  public String getMainDirForGeneralStat (String site)
  {
    String s = System.getProperty("global");
    return ( Utils.isEmpty( s ) ? null : s );
  }

}
