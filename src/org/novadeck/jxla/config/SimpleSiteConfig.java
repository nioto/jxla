/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/config/SimpleSiteConfig.java,v $
 * $Revision: 1.4 $
 * $Date: 2005/03/19 17:27:07 $
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


  public SimpleSiteConfig() {
  }

  public String getStatsDirectory(String hostname) {
    return System.getProperty("outputdir");
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
