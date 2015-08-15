/*
 * PhpSniff.java
 *
 * Created on 11 mars 2003, 13:11
 */

package org.nioto.browser;

/**
 *
 * @author  tonio
 */
public class PhpSniff extends Gecko {
  public String getOsInformation ( String ua ) {
    String match[] = new String[10];
    if ( preg_match_all ( WINDOWS_OS, ua, match ) ) {
      String v    = match[ 2 ];
      String v2   = match[ 3 ];
      if ( stristr ( v, "NT")  && "5.1".equals (v2) )     v = "xp";
      else if ( "2000".equals ( v ) )                     v = "2k";
      else if ( stristr ( v, "NT") && "5.0".equals (v2) ) v = "2k";
      else if ( stristr ( v, "9x") && (!empty (v2) && v2.startsWith ("4.9")) ) v = "98";
      else if ( "16bit".equals ( v+v2 ))                  v = "31";
      else                                                v = v+v2;
      if ( empty ( v ) )                                  v = "win";
      return v;
    } else if( preg_match ( AMIGA_OS, ua, match) ) {
      String v = "amiga";
      if ( stristr ( ua,"morphos") ) {
        v = "amiga-morphos";
      } else if ( stristr (ua,"mc680x0") ) {
        v = "amiga-mc680x0";
      } else if ( stristr (ua,"ppc") ) {
        v = "amiga-ppc";
      } else if ( preg_match (AMIGA_EXTRA,ua,match) ) {
        v = "amiga-"+match[1];
      }
      return v;
    } else if ( preg_match ( OS2_OS, ua, match ) )
      return "os2";
    else if ( preg_match ( MAC_OS, ua, match ) ) {
      String os ;
      os = ( !empty ( match[1] ) ?  "68k" : "" );
      os = ( !empty ( match[2] ) ?  "osx" : os );
      os = ( !empty ( match[3] ) ?  "ppc" : os );
      os = ( !empty ( match[4] ) ?  "osx" : os );
      return os;
    } else if ( preg_match ( SUNOS_OS, ua, match ) ) {
      if (!stristr ("sun", match[1])) match[1]="sun"+match[1];
      return match[1]+match[2];
    } else if ( preg_match ( IRIX_OS, ua, match )) {
      return match[1]+match[2];
    } else if ( preg_match ( HPUX_OS, ua, match ) ) {
      match[1] = str_replace ( "-","", match[1] );
      return match[1]+match[2];
    } else if ( preg_match ( AIX_OS, ua, match ))
      return "aix"+match[1];
    else if ( preg_match ( DEC_OS, ua, match ))
      return "dec";
    else if ( preg_match ( VMS_OS, ua, match ))
      return "vms";
    else if ( preg_match ( SCO_OS, ua, match ))
      return "sco";
    else if ( stristr ( ua, "unix_system_v") )
      return "unixware";
    else if ( stristr ( ua, "ncr") )
      return "mpras";
    else if ( stristr ( ua, "reliantunix") )
      return "reliantunix";
    else if ( stristr ( ua, "sinix") )
      return "sinix";
    else if ( preg_match ( BSD_OS, ua, match ))
      return match[1]+match[2];
    else if ( preg_match ( LINUX_OS, ua, match ))
      return "linux";
    else
      return "unknown";
  }
  
  //-------------------------------------
  public static void set_browser_info ( Browser b ) {
    String ua = b.getUA ();
    String match[] = new String[10];
    if(preg_match_all ( REConstants.RE_BROWSER_PATTERN, ua, match ) ) {
      
      //result =
      b.setShortName ( B.getShortName ( match[1] ) );
      
      // get the position of the last browser found
            /*
            System.out.println ("browser:" + result );
            System.out.println ("long_name:" + match[1] );
            System.out.println ("maj_ver:" + match[2] );
             */
      b.setMajVersion ( match[2] );
      // parse the minor version string and look for alpha chars
      String min = ".0";
      if (  preg_match ( REConstants.RE_MINVERSION, match[3],match) ) {
        if(!empty ( match[1])) {
          min = match[1] ;
        }
                /*
                if(!empty (match[2]))
                    System.out.println ("letter_ver:" + match[2]);
                 */
      }
      b.setMinVersion ( min );
            /*
             */
      // insert findings into container
      //System.out.println ("version:" );
    }
    //return result;
  }
}
