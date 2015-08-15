/*
 * B.java
 *
 * Created on March 9, 2003, 5:05 PM
 */

package org.nioto.browser;

import java.util.*;
/**
 *
 * @author  tonio
 */
public class B {
  static Map<String, String> _browsers =  new HashMap <String, String>();
  static Map<String, String> _shortnameToLongname =  new HashMap<String, String> ();
  static List<String> _browsersNames= new ArrayList <String>();
  static {
    addBrowser ( "microsoft internet explorer", "IE", "Internet Explorer" );
    addBrowser ( "msie", "IE", "Internet Explorer" );
    addBrowser ( "netscape6", "NS", "Netscape" );
    addBrowser ( "netscape", "NS", "Netscape" );
    addBrowser ( "galeon", "GA", "Galeon" );
    addBrowser ( "phoenix", "PX", "Phoenix" );
    addBrowser ( "mozilla firebird", "FB", "FireBird");
    addBrowser ( "firebird", "FB", "FireBird");
    addBrowser ( "firefox", "FX", "Firefox");
    addBrowser ( "chimera", "CH", "Chimera" );
    addBrowser ( "camino", "CA", "Camino" );
    addBrowser ( "epiphany", "EP", "Epiphany" );
    addBrowser ( "safari", "SF", "Safari" );
    addBrowser ( "k-meleon", "KM","K-meleon" );
    addBrowser ( "mozilla", "MZ","Mozilla" );
    addBrowser ( "opera", "OP", "Opera" );
    addBrowser ( "konqueror", "KQ" , "Konqueror");
    addBrowser ( "icab", "IC", "Icab" );
    addBrowser ( "lynx", "LX","Lynx" );
    addBrowser ( "links", "LI","Links" );
    addBrowser ( "ncsa mosaic", "MO","NCSA Mosaic" );
    addBrowser ( "amaya", "AM", "Amaya" );
    addBrowser ( "omniweb", "OW","OmniWeb" );
    addBrowser ( "hotjava", "HJ", "Hot Java" );
    addBrowser ( "browsex", "BX", "BrowseX" );
    addBrowser ( "amigavoyager", "AV","Amiga Voyager" );
    addBrowser ( "amiga-aweb", "AW", "Amiga Aweb");
    addBrowser ( "ibrowse", "IB", "Ibrowse");
  }
  
  
  private static void addBrowser (String name, String twoLettersName, String outputName ){
    _browsers.put ( name, twoLettersName );
    _browsersNames .add ( name );
    _shortnameToLongname.put ( twoLettersName, outputName );
  }
  public static String BROWSER_REGEXP = getBrowserRegexp ();
  
  public static String getShortName ( String s ) {
    return (String)_browsers.get ( s );
  }
  public static String getLongName ( String shortName ) {
    return (String)_shortnameToLongname.get ( shortName );
  }
  public static String getBrowserRegexp () {
    //    Set keys = _browsers.keySet ();
    String result="";
    Iterator<String> ite = _browsersNames.iterator ();
    while ( ite.hasNext () ) {
      result += ite.next ();
      if ( ite.hasNext ()) {
        result += "|";
      }
    }
    //    result="microsoft internet explorer|msie|netscape6|netscape|galeon|phoenix|chimera|camino|safari|k-meleon|mozilla|opera|konqueror|icab|lynx|links|ncsa mosaic|amaya|omniweb|hotjava|browsex";
    String version_string = "[/\\sa-z\\(]*([0-9]+)([\\.0-9a-z]+)?";
    result = "("+result+")" + version_string;
    return result;
  }
}
