/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/config/SimpleSiteConfig.java,v $
 * $Revision: 1.3 $
 * $Date: 2005/01/06 13:18:54 $
 * $Author: nioto $
 */
package org.novadeck.jxla.config;


import org.novadeck.jxla.tools.Utils;
import org.novadeck.jxla.data.RegexpData;

import java.util.HashMap;


/*
 *
 */

public class SimpleSiteConfig implements AbstractSiteConfig {


  private static final String RELATIVE_DIRECTORY_4_STATS = "/stat/";

  public SimpleSiteConfig() {
  }


  public String getStatsDirectory(String hostname) {
    return "E:/tmp/" + hostname +"/";
  }

  public String getRealHostName(String hostname) {
    if ( hostname != null) hostname=hostname.toLowerCase();
    return hostname;
  }

  public String getRealUserInfo(String user) {
    return user;
  }


  public boolean ignoreLine( RegexpData re ) {
    String ip = re.getRemoteIP();
    if ( ip.startsWith( "127." )) 
      return true;
    else
      return false;
  }



  /** Retrieve the directory where to store a summary of requests to the differents
   * websites in the platform.
   */
  public String getMainDirForGeneralStat (String site)
  {
    String s = System.getProperty("global");
    if (org.novadeck.jxla.tools.Utils.isEmpty( s ) ) {
      return null;
    }
    else {
      return s;
    }
  }
  
}
