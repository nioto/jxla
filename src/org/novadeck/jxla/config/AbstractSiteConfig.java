/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/config/AbstractSiteConfig.java,v $
 * $Revision: 1.2 $
 * $Date: 2005/01/06 13:18:54 $
 * $Author: nioto $
 */
package org.novadeck.jxla.config;


import org.novadeck.jxla.data.Line;
import org.novadeck.jxla.data.RegexpData;


/**
 * This class defines an interface to get information from
 * your WebSites configuration.
 *
 */

public interface AbstractSiteConfig {

  /**
   * Retrieve the internal name to use for log analyze,
   * many websites use different names for the same ressources
   * like www.novadeck.org and novadeck.org, so we need
   * only one name.
   * @hostname : the hostname in the lod file
   * @return String : the internal name for computing logs
   */
  public String getRealHostName(String hostname);

  /**
   * Convert any user info from log files to a 'readable'
   * form for the output. In log file we can store his login but
   * want to output his fullname.
   */
  public String getRealUserInfo(String user);

  /**
   * Method to check if the log line must be ignore,
   * may be a comment ( beginnnig with # ), or an URL we don't want to
   * output it ( like a url of developping next generation of website, or
   * an admin part of the site ). Can also work with the user info.
   */
  public boolean ignoreLine( RegexpData re ) ;

  /**
   * Retrieve the directory where to store the output files, depanding of
   * the website.
   */
  public String getStatsDirectory(String hostname);

  /**
   * Retrieve the directory where to store a summary of requests to the differents
   * websites in the platform.
   */
  public String getMainDirForGeneralStat( String site ) ;
}
