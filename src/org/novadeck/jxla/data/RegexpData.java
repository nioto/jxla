/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/data/RegexpData.java,v $
 * $Revision: 1.3 $
 * $Date: 2005/01/06 13:18:54 $
 * $Author: nioto $
 */
package org.novadeck.jxla.data;


import org.apache.oro.text.regex.*;
import org.apache.oro.text.perl.*;

import org.novadeck.jxla.Constants;
import org.novadeck.jxla.tools.*;
import org.novadeck.jxla.config.*;

import java.text.SimpleDateFormat;

import java.util.*;



public class RegexpData extends SimpleData
{
  
  public static final String    HOSTNAME    = "host";
  public static final String    REMOTE_IP   = "remote_ip";
  public static final String    REMOTE_HOST = "remote_host";
  public static final String    USER        = "user";
  public static final String    URI         = "uri";
  public static final String    STATUS      = "status";
  public static final String    REFERER     = "referer";
  public static final String    USER_AGENT  = "agent";
  public static final String    LANGUAGE    = "lang";
  public static final String    SIZE        = "size";
  public static final String    TIME        = "time";
  public static final String    SERVER      = "server";
  
  public static final char    YEAR        = 'y';
  public static final char    MONTH       = 'm';
  public static final char    DAY         = 'd';
  public static final char    HOUR        = 'h';
  
  private int   _host;
  private int   _ip;
  private int   _user;
  private int   _uri;
  private int   _status;
  private int   _referer;
  private int   _agent;
  private int   _lang;
  private int   _size;
  
  private int   _year;
  private int   _month;
  private int   _day;
  private int   _hour;
  private int   _time;
  private int   _server;
  
  public static final char      WILDCARD    = '*';
  public static final char      IDENTIFIER  = '$';
  public static final char      DELIMITER   = '"';
  public static final char      SPACE       = ' ';
  public static final char      SLASH       = '/';
  public static final char      DOUBLEPOINT = ':';
  public static final char      CROCHET_O = '[';
  public static final char      CROCHET_F = ']';
  public static final char      TIRET     = '-';
  
  public static PatternCompiler COMPILER  = new Perl5Compiler ();
  public static PatternMatcher  MATCHER   = new Perl5Matcher ();
  
  private Pattern       _pattern    = null;
  private MatchResult   _res;
  
  
  
  private static RegexpData  _lastMatched  =null;
  
  public String   initialRegexp = null;
  public String   compiledRegexp  = null;
  
  
  /**
   *
   *
   */
  private RegexpData ()
  {
  }
  
  public RegexpData (String s)
  {
    initialRegexp = s;
    _host     = -1;
    _ip       = -1;
    _user     = -1;
    _uri      = -1;
    _status   = -1;
    _referer  = -1;
    _agent    = -1;
    _lang     = -1;
    _size     = -1;
    
    _year     = -1;
    _month    = -1;
    _day      = -1;
    _hour     = -1;
    _time     = -1;
    _server   = -1;
    // analyze s as a simple regexep
    if ( Utils.isEmpty ( s ) )
    { quit ( "regexp is empty "); }
    int length  = s.length ();
    int current = 0;
    char[] arr  = s.toCharArray ();
    int pos     = 1;
    
    StringBuffer regexp = new StringBuffer ();
    
    while ( current < length )
    {
      switch ( arr[current] )
      {
        case YEAR:
          regexp.append ( "([0-9]{2,4})" );
          current ++;
          _year   =   pos++;
          break;
        case MONTH:
          regexp.append ( "([0-9a-zA-Z]{1,3})" );
          current ++;
          _month  =   pos++;
          break;
        case DAY:
          regexp.append ( "([ 0-9]{1,2})" );
          current ++;
          _day    =   pos++;
          break;
        case HOUR:
          regexp.append ( "([0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})" );
          current ++;
          _hour   =   pos++;
          break;
        case IDENTIFIER:
          // load the parameter
          current++;
          StringBuffer sb = new StringBuffer ();
          boolean found = false;
          while ( (current<length) && ( !found ) )
          {
            sb.append ( arr[current] );
            current++;
            found = true;
            if ( HOSTNAME.equals ( sb.toString () ) )
            {
              regexp.append ( "([a-zA-Z0-9\\.\\-_]*)" );
              _host = pos ++;
            }
            else if ( REMOTE_IP.equals ( sb.toString () ) )
            {
              regexp.append ( "([0-9\\.]*)" );
              _ip       = pos ++;
            }
            else if ( REMOTE_HOST.equals ( sb.toString () ) )
            {
              regexp.append ( "([a-zA-Z0-9\\-\\.]*)" );
              _ip       = pos ++;
            }
            else if ( USER.equals (sb.toString () ) )
            {
              regexp.append ( "([^ ]*)" );
              _user     = pos ++;
            } else if ( URI.equals ( sb.toString () ) )
            {
              regexp.append ( "(http://[^/]*)?(\\/[^ ]*)" );
              pos++;
              _uri      = pos ++;
            }
            else if ( STATUS.equals ( sb.toString () ) )
            {
              regexp.append ( "([0-9]*)" );
              _status   = pos ++;
            }
            else if ( REFERER.equals ( sb.toString () ) )
            {
              regexp.append ( "\"([^\"]*)\"" );
              _referer  = pos ++;
            }
            else if ( USER_AGENT.equals ( sb.toString () ) )
            {
              regexp.append ( "\"([^\"]*)\"" );
              _agent    = pos ++;
            }
            else if ( LANGUAGE.equals ( sb.toString () ) )
            {
              regexp.append ( "\"([^\"]*)\"" );
              _lang     = pos ++;
            }
            else if ( SIZE.equals ( sb.toString () ) )
            {
              regexp.append ( "([0-9\\-][0-9]*)" );
              _size  = pos ++;
            }
            else if ( TIME.equals ( sb.toString () ) )
            {
              regexp.append ( "([0-9]*)" );
              _time   = pos ++;
            }
            else if ( SERVER.equals ( sb.toString () ) )
            {
              regexp.append ( "([a-zA-Z0-9\\.\\-_]*)" );
              _server = pos ++;
            }
            else
            {
              found = false;
            }
          }
          if (!found)
            quit ( "bad regexp look at " + sb) ;
          
          break;
        case WILDCARD:
          current ++;
          regexp.append ( "[^ ]*" );
/*          if (current<length)
          else
            regexp.append( ".*" );*/
          break;
        case DELIMITER:
          regexp.append ( DELIMITER );
          current ++;
          break;
        case SPACE:
          regexp.append ( SPACE );
          current ++;
          break;
        case SLASH:
          regexp.append ( SLASH );
          current ++;
          break;
        case DOUBLEPOINT:
          regexp.append ( DOUBLEPOINT );
          current ++;
          break;
        case CROCHET_O:
          regexp.append ( '\\' );
          regexp.append ( CROCHET_O);
          current ++;
          break;
        case CROCHET_F:
          regexp.append ( '\\' );
          regexp.append ( CROCHET_F );
          current ++;
          break;
        case TIRET:
          regexp.append ( TIRET );
          current ++;
          break;
        default:
          quit ("malformed regexp = >" + s + "< look @ " + arr[current] );
      }
    }
    regexp.append ('$');
    compiledRegexp = regexp.toString ();
    if (Config.DEBUG)
    {
      System.out.println ("re==");
      System.out.println (compiledRegexp);
    }
    try
    {
      _pattern  = COMPILER.compile ( compiledRegexp );
    }
    catch(MalformedPatternException e)
    {
      quit ( " malformed regexp  for "  + s  + "////" + regexp.toString ());
    }
  }
  
  public String getCompiledRegexp ()
  {
    return compiledRegexp;
  }
  //============================================================================
  public boolean match ( String s )
  {
    _globalCounter  ++;
    _res =null;
    if (MATCHER.matches ( s, _pattern ))
    {
      inc ();
      _lastMatched  = this;
      _res = MATCHER.getMatch ();
      return true;
    }
    return false;
  }
  
  //============================================================================
  private String getExpr ( int rank )
  {
    if ( (_res == null ) || (rank <= 0 ) )  return null;
    return _res.group ( rank );
  }
  
  //============================================================================
  /**
   * @return  */
  public String getHost ()
  {   return getExpr ( _host );    }
  
  public String getRemoteIP ()
  {
    String ip = getExpr ( _ip );
    return ip;
  }
  
  public String getUser ()
  {   return getExpr ( _user );    }
  
  public String getURI ()
  {   return getExpr ( _uri );     }
  
  public String getStatus ()
  {   return getExpr ( _status );  }
  
  public String getReferer ()
  {   return getExpr ( _referer ); }
  
  public String getAgent ()
  {   return getExpr ( _agent );   }
  
  public String getLanguage ()
  {   return getExpr ( _lang );    }
  
  
  public int getHour ()
  {
    String s = getExpr ( _hour );
    return Integer.parseInt ( s.substring ( 0, s.indexOf (':') ) );
  }
  
  public long   getSize ()
  {
    if ( (_res == null ) || (_size<= 0 ) )  return 0;
    String s = getExpr ( _size );
    if (Utils.isEmpty ( s )  || "-".equals ( s ) ) return 0;
    else return Long.parseLong ( s );
  }
  
  public int getTime ()
  {
    if ( _time < 0 ) return 0;
    return Integer.parseInt ( getExpr ( _time ).trim () );
  }
  public String getServer ()
  {
    if ( _server < 0 ) return "";
    return getExpr ( _server );
  }
  
  public int getDay ()
  {
    return Integer.parseInt ( getExpr ( _day ).trim () );
  }
  public int getMonth ()
  {
    String month = getExpr ( _month ).toLowerCase ();
    //      jan feb mar apr may jun jul aug sep oct nov dec
    char c = month.charAt (0);
    switch ( c )
    {
      case 'j' :
        if ( "jan".equals ( month ) )
          return 0;
        else if( "jun".equals ( month ) )
          return 5;
        else
          return 6;
      case 'f' :
        return 1;
      case 'm' :
        if ( "mar".equals ( month ) )
          return 2;
        else
          return 4;
      case 'a' :
        if ( "apr".equals ( month ) )
          return 3;
        else
          return 7;
      case 's' :
        return 8;
      case 'o' :
        return 9;
      case 'n' :
        return 10;
      default:
        return 11;
    }
  }
  public int getYear ()
  {
    return Integer.parseInt ( getExpr ( _year ).trim () );
  }

  //============================================================================
  //----------------
  private void quit (String s )
  {
    System.err.println ("ERROR : " + s);
    System.err.println ("exiting ");
    System.exit (0);
  }
  
  //============================================================================
  //----------------
  private static ArrayList    _availableRegexp  = new ArrayList ();
  private static int          _globalCounter    = 0;
  private static RegexpData[] _arrayRe          = null;
  
  //----------------
  public static void addRegexp ( RegexpData re )
  {
    if (_lastMatched == null) _lastMatched = re;
    if (! _availableRegexp.contains ( re ) )
    { _availableRegexp.add ( re );  }
    _arrayRe      =  (RegexpData[])_availableRegexp.toArray ( new RegexpData[0] );
  }
  //----------------
  public static void updateList ()
  {
    if ( _availableRegexp.size () ==1) return;
    Collections.sort ( _availableRegexp );
    _arrayRe      =  (RegexpData[])_availableRegexp.toArray ( new RegexpData[0] );
  }
  
  public static Line getLine ( String s )
  {
    if ( Utils.isComment ( s ) )
    {
      if (Config.DEBUG)
        System.out.println ("comment");
      return null;
    }
    s = s.trim ();
    // need to update list for first use
    if ( (_globalCounter % 10000) ==0 )
    {
      updateList ();
      _globalCounter =1;
    }
    
    int i=0;
    
    Line l = null;
    
    if (RegexpData._lastMatched.match ( s ) )
    {
      RegexpData re = _lastMatched;
      /////////////////
      if ( !Config.siteConfig.ignoreLine ( re ) )
        l = Line.getLine ( re.getHost (), re.getHour (), re.getDay (), re.getMonth (), re.getYear (), re.getRemoteIP (),
        re.getURI (), re.getReferer (), re.getAgent (), re.getStatus (), re.getSize (), re.getUser (), re.getTime (), re.getServer () );
      return l;
    }
    while ( ( i < _arrayRe.length) && ( !_arrayRe[i].match ( s ) ) )
    {
      i++;
    }
    
    if (i < _arrayRe.length)
    {
      RegexpData re = _arrayRe[i];
      /////////////////
      if ( !Config.siteConfig.ignoreLine ( re ) )
        l = Line.getLine ( re.getHost (), re.getHour (), re.getDay (), re.getMonth (), re.getYear (), re.getRemoteIP (),
        re.getURI (), re.getReferer (), re.getAgent (), re.getStatus (), re.getSize (), re.getUser (), re.getTime (), re.getServer () );
      _lastMatched = re;
    }
    return l;
  }
  
  //==========================================
  public final static void dumpCounters ()
  {
    for (int i=0; i<_arrayRe.length; i++)
    {
      System.out.println ("re  = " +_arrayRe[i].initialRegexp);
      System.out.println ("match " + _arrayRe[i].getCount ());
    }
  }
  //==========================================
  public final static void displayRegexp ()
  {
    for (int i=0; i<_arrayRe.length; i++)
    {
      System.out.println (_arrayRe[i].initialRegexp);
    }
  }
  
  
  
}
