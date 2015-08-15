/*
 * BrowserCore.java
 *
 * Created on March 11, 2003, 9:27 PM
 */

package org.nioto.browser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author  tonio
 */
public class BrowserCore {
  public final static int WINDOWS_OS= 1;
  public final static int MAC_OS    = 2;
  public final static int OS2_OS    = 3;
  public final static int SUNOS_OS  = 4;
  public final static int IRIX_OS   = 5;
  public final static int HPUX_OS   = 6;
  public final static int AIX_OS    = 7;
  public final static int DEC_OS    = 8;
  public final static int VMS_OS    = 9;
  public final static int SCO_OS    = 10;
  public final static int LINUX_OS  = 11;
  public final static int BSD_OS    = 12;
  public final static int AMIGA_OS  = 13;
  public final static int AMIGA_EXTRA= 14;
  
  /** Creates a new instance of BrowserCore */
  public BrowserCore () {
  }
  
  ////////////////////////////////////
  public static boolean stristr (String s, String pattern) {
    return ( s==null || pattern==null ? false : s.toLowerCase ().indexOf (pattern.toLowerCase ())>=0 );
  }
  ////////////////////////////////////
  public static boolean empty (String s) {
    return s==null || s.length ()==0;
  }
  ////////////////////////////////////
  
  public static Pattern getPattern ( int re ) {
    Pattern pattern;
    switch( re ) {
      case WINDOWS_OS:
        pattern = REConstants.RE_WINDOWS;
        break;
      case MAC_OS:
        pattern = REConstants.RE_MAC;
        break;
      case OS2_OS:
        pattern = REConstants.RE_OS2;
        break;
      case SUNOS_OS:
        pattern = REConstants.RE_SUNOS;
        break;
      case IRIX_OS:
        pattern = REConstants.RE_IRIX;
        break;
      case HPUX_OS:
        pattern = REConstants.RE_HPUX;
        break;
      case AIX_OS:
        pattern = REConstants.RE_AIX;
        break;
      case DEC_OS:
        pattern = REConstants.RE_DEC;
        break;
      case VMS_OS:
        pattern = REConstants.RE_VMS;
        break;
      case SCO_OS:
        pattern = REConstants.RE_SCO;
        break;
      case LINUX_OS:
        pattern = REConstants.RE_LINUX;
        break;
      case BSD_OS:
        pattern = REConstants.RE_BSD;
        break;
      case AMIGA_OS :
        pattern = REConstants.RE_AMIGA;
        break;
      case AMIGA_EXTRA :
        pattern = REConstants.RE_AMIGA_EXTRA;
        break;
      default :
        throw new IllegalArgumentException ( " no such pattern ") ;
    }
    return pattern;
  }
  
  ////////////////////////////////////
  public static String str_replace ( String pattern, String replace, String s ) {
    if (s==null|| pattern==null)
      return null;
    StringBuffer sb = new StringBuffer ();
    for ( int i = s.indexOf ( pattern); i>=0; i=s.indexOf ( pattern) ) {
      sb.append ( s.substring (0, i) );
      sb.append ( replace );
      s = s.substring ( i + pattern.length () );
    }
    sb.append ( s );
    return sb.toString ();
  }
  
  ////////////////////////////////////
  public static boolean preg_match ( int re, String ua, String match[]) {
    Pattern pattern = getPattern ( re );
    return preg_match ( pattern, ua, match );
  }
  public static boolean preg_match ( Pattern pattern, String ua, String match[]) {
    if ( empty (ua) )
      return false;
    for ( int i=0; i<match.length; i++) {
      match[i]=null;
    }
//    Arrays.fill(match,null);
    Matcher matcher =  pattern.matcher(ua);
    if ( matcher.matches() ) {
      for (int i =1; i<matcher.groupCount(); i++ ) {
        match[i] = matcher.group ( i );
      }
      return true;
    } else
      return false;
  }
  
  public static boolean preg_match_all ( int re, String ua, String match[] ) {
    return  preg_match_all (getPattern ( re ), ua, match );
    
  }
  
  public static boolean preg_match_all ( Pattern pattern, String ua, String match[] ) {
    for ( int i=0; i<match.length; i++) {
      match[i]=null;
    }
    boolean uaMatches = false;
    Matcher matcher = pattern.matcher(ua);
    while ( matcher.find() ) {
      uaMatches = true;
      for (int i =1; i<matcher.groupCount(); i++ ) {
        match[i] = matcher.group ( i );
      }
    }
    
    return uaMatches;
  }
  
  public static void main ( String args[]) {
    System.out.println ( str_replace ("34", "12", "1234ToTO345634"));
  }
  
  
}
