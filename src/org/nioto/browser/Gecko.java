/*
 * Gecko.java
 *
 * Created on March 12, 2003, 10:10 PM
 */

package org.nioto.browser;

/**
 *
 * @author  tonio
 */
public class Gecko extends BrowserCore {
  
  
  
  public static void setGecko ( Browser b ) {
    String ua = b.getUA ();
    String shortName = b.getShortName ();
    String match[] = new String [10];
    String mozv [] = new String [10];
    
    if(preg_match ( REConstants.RE_GECKO_1, ua, match)) {
      if (preg_match (REConstants.RE_GECKO_2, ua, mozv)) {
        // mozilla release
      } else
        if (preg_match (REConstants.RE_GECKO_3, ua, mozv)) {
        // mozilla milestone version
        }
      if ( shortName.equals ( B.getShortName ("mozilla") ) ) {
        if(preg_match (REConstants.RE_GECKO_4, mozv[1], match)) {
          b.setMajVersion ( match[1] );
          b.setMinVersion ( match[2] );
        }
      }
    } else
      if ( shortName.equals ( B.getShortName ("mozilla") ) ) {
      b.setShortName ( B.getShortName ( "netscape" ) );
      }
  }
  
  public static void main (String a[]){
    Test.main (a);
  }
}
