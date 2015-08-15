/*
 * Browser.java
 *
 * Created on March 12, 2003, 11:33 PM
 */

package org.nioto.browser;

/**
 *
 * @author  tonio
 */
public class Browser {
  
  public static final PhpSniff parser = new PhpSniff ();
  
  private String majVersion;
  private String minVersion;
  private String shortName;
  private String osName;
  private String UA;
  int _hashCode =0;
  /** Creates a new instance of Browser */
  public Browser (String user_agent) {
    UA = user_agent.toLowerCase ();
    osName = parser.getOsInformation ( UA );
    PhpSniff.set_browser_info ( this );
    PhpSniff.setGecko ( this );
  }
  
  public String getUA () {
    return UA;
  }
  
  public String getMajVersion () {
    return majVersion==null? "": majVersion;
  }
  public void setMajVersion ( String v) {
    majVersion = v;
  }
  public String getMinVersion () {
    String tmp = (minVersion==null? "": minVersion);
    return tmp;
    /*
    if ( tmp.length () <2)
      return tmp;
    else
      return tmp.substring (0, 2);
*/  }
  public void setMinVersion ( String v) {
    minVersion = v;
  }
  
  public String getShortName () {
    return shortName == null? "" : shortName;
  }
  public void setShortName ( String s) {
    shortName = s ;
  }
  
  public String getLongName ( ) {
    String s = B.getLongName ( getShortName () );
    return s == null? "" : s ;
  }
  
  public String getOsName () {
    return osName ==null ? "" : osName;
  }
  public void setOsName ( String s) {
    osName = s;
  }
  
  public String toString () {
    return osName + "/" + shortName + "/" + majVersion ;
  }
  
  /////////////////////////////
  public boolean equals ( Object obj ) {
    if ( obj instanceof Browser ) {
      Browser b = (Browser)obj;
      return ( b.getMajVersion ().equals ( this.majVersion ) &&
          b.getShortName ().equals ( this.shortName ) );
    }
    return false;
  }
  public int hashCode () {
    if ( _hashCode == 0 )
      _hashCode = toString ().hashCode ();
    return _hashCode;
  }
}