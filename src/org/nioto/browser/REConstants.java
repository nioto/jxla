/*
 * REConstants.java
 *
 * Created on 11 mars 2003, 12:54
 */

package org.nioto.browser;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author  tonio
 */
public class REConstants {
  
  
  public static Pattern RE_WINDOWS;
  public static Pattern RE_MAC;
  public static Pattern RE_OS2;
  public static Pattern RE_SUNOS;
  public static Pattern RE_IRIX;
  public static Pattern RE_HPUX;
  public static Pattern RE_AIX;
  public static Pattern RE_DEC;
  public static Pattern RE_VMS;
  public static Pattern RE_SCO;
  public static Pattern RE_LINUX;
  public static Pattern RE_BSD;
  public static Pattern RE_AMIGA;
  public static Pattern RE_AMIGA_EXTRA;
  
  public static Pattern RE_BROWSER_PATTERN;
  
  public static Pattern RE_GECKO_1;
  public static Pattern RE_GECKO_2;
  public static Pattern RE_GECKO_3;
  public static Pattern RE_GECKO_4;
  public static Pattern RE_GECKO_5;
  
  public static Pattern RE_MINVERSION;
  static {
    init ();
  }
  public static void init () {
    try {
      RE_WINDOWS  = Pattern.compile ( Constants.RE_WINDOWS_STR );
      RE_MAC      = Pattern.compile ( Constants.RE_MAC_STR );
      RE_OS2      = Pattern.compile ( Constants.RE_OS2_STR );
      RE_SUNOS    = Pattern.compile ( Constants.RE_SUNOS_STR );
      RE_IRIX     = Pattern.compile ( Constants.RE_IRIX_STR );
      RE_HPUX     = Pattern.compile ( Constants.RE_HPUX_STR );
      RE_AIX      = Pattern.compile ( Constants.RE_AIX_STR );
      RE_DEC      = Pattern.compile ( Constants.RE_DEC_STR );
      RE_VMS      = Pattern.compile ( Constants.RE_VMS_STR );
      RE_SCO      = Pattern.compile ( Constants.RE_SCO_STR );
      RE_LINUX    = Pattern.compile ( Constants.RE_LINUX_STR );
      RE_BSD      = Pattern.compile ( Constants.RE_BSD_STR );
      RE_AMIGA    = Pattern.compile ( Constants.RE_AMIGA_STR );
      RE_AMIGA_EXTRA  = Pattern.compile ( Constants.RE_AMIGA_EXTRA_STR );
      
      RE_BROWSER_PATTERN   = Pattern.compile ( B.BROWSER_REGEXP );
      
      RE_GECKO_1  = Pattern.compile ( Constants.RE_GECKO_1_STR );
      RE_GECKO_2  = Pattern.compile ( Constants.RE_GECKO_2_STR );
      RE_GECKO_3  = Pattern.compile ( Constants.RE_GECKO_3_STR );
      RE_GECKO_4  = Pattern.compile ( Constants.RE_GECKO_4_STR );
      
      RE_MINVERSION  = Pattern.compile ( Constants.RE_MINVERSION );
    } catch ( PatternSyntaxException mpe ) {
      mpe.printStackTrace ();
      System.exit (0);
    }
  }
}
