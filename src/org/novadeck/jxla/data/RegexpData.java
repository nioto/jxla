/*
 * JXLA is released under an Apache-style license.
 * ==============================================
 *
 * The JXLA License, Version 1.0
 *
 * Copyright (c) 2002 JXLA. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by
 *        The JXLA Project Team (http://jxla.novadeck.org/)
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The name "JXLA" must not be used to endorse or promote
 *    products derived from this software without
 *    prior written permission. For written permission,
 *    please contact nioto@users.sourceforge.net.
 *
 * 5. Products derived from this software may not be called "JXLA",
 *    nor may "JXLA" appear in their name, without prior written
 *    permission of the JXLA Team.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE TM4J PROJECT OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 */


/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/data/RegexpData.java,v $
 * $Revision: 1.2 $
 * $Date: 2002/02/10 15:05:06 $
 * $Author: nioto $
 */
package org.novadeck.jxla.data;


import org.apache.oro.text.regex.*;
import org.apache.oro.text.perl.*;

import org.novadeck.jxla.Statics;
import org.novadeck.jxla.tools.*;
import org.novadeck.jxla.config.*;

import java.text.SimpleDateFormat;

import java.util.*;



public class RegexpData extends SimpleData {


  public static final SimpleDateFormat fd   = new SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.ENGLISH);

  public static final String    HOSTNAME    = "host";
  public static final String    REMOTE_IP   = "remote_ip";
  public static final String    USER        = "user";
  public static final String    URI         = "uri";
  public static final String    STATUS      = "status";
  public static final String    REFERER     = "referer";
  public static final String    USER_AGENT  = "agent";
  public static final String    LANGUAGE    = "lang";
  public static final String    SIZE        = "size";

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

  public static final char      WILDCARD    = '*';
  public static final char      IDENTIFIER  = '$';
  public static final char      DELIMITER   = '"';
  public static final char      SPACE       = ' ';

  public static PatternCompiler COMPILER  = new Perl5Compiler();
  public static PatternMatcher  MATCHER   = new Perl5Matcher();

  private Pattern       _pattern    = null;
  private MatchResult   _res;



  private static RegexpData  _lastMatched  =null;

  public String   initialRegexp = null;



  /**
   *
   *
   */
  private RegexpData(){
  }

  public RegexpData(String s) {
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

    // analyze s as a simple regexep
    if ( Utils.isEmpty( s ) ) { quit( "regexp is empty "); }
    int length  = s.length();
    int current = 0;
    char[] arr  = s.toCharArray();
    int pos     = 1;

    StringBuffer regexp = new StringBuffer();

    while ( current < length ){
      switch ( arr[current] ) {
        case YEAR:
          regexp.append( "([0-9]{2,4})" );
          current ++;
          _year   =   pos++;
          break;
        case MONTH:
          regexp.append( "([0-9a-zA-Z]{1,3})" );
          current ++;
          _month  =   pos++;
          break;
        case DAY:
          regexp.append( "([ 0-9]{1,2})" );
          current ++;
          _day    =   pos++;
          break;
        case HOUR:
          regexp.append( "([0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})" );
          current ++;
          _hour   =   pos++;
          break;
        case IDENTIFIER:
          // load the parameter
          current++;
          StringBuffer sb = new StringBuffer();
          boolean found = false;
          while ( (current<length) && ( !found ) ) {
            sb.append( arr[current] );
            current++;
            found = true;
            if ( HOSTNAME.equals( sb.toString() ) ) {
              regexp.append( "([a-zA-Z0-9\\.\\-_]*)" );
              _host = pos ++;
            } else if ( REMOTE_IP.equals( sb.toString() ) ) {
              regexp.append( "([0-9\\.]*)" );
              _ip       = pos ++;
            } else if ( USER.equals(sb.toString() ) ) {
              regexp.append( "([^ ]*)" );
              _user     = pos ++;
            } else if ( URI.equals( sb.toString() ) ) {
              regexp.append( "(http://[^/]*)?(\\/[^ ]*)" );
              pos++;
              _uri      = pos ++;
            } else if ( STATUS.equals( sb.toString() ) ) {
              regexp.append( "([0-9]*)" );
              _status   = pos ++;
            } else if ( REFERER.equals( sb.toString() ) ) {
              regexp.append( "\"([^\"]*)\"" );
              _referer  = pos ++;
            } else if ( USER_AGENT.equals( sb.toString() ) ) {
              regexp.append( "\"([^\"]*)\"" );
              _agent    = pos ++;
            } else if ( LANGUAGE.equals( sb.toString() ) ) {
              regexp.append( "\"([^\"]*)\"" );
              _lang     = pos ++;
            } else if ( SIZE.equals( sb.toString() ) ) {
              regexp.append( "([0-9]*)" );
              _size  = pos ++;
            } else {
              found = false;
            }
          }
          if (!found)
            quit( "bad regexp look at " + sb) ;

          break;
        case WILDCARD:
          current ++;
          regexp.append( "[^ ]*" );
/*          if (current<length)
          else
            regexp.append( ".*" );*/
          break;
        case DELIMITER:
          regexp.append( DELIMITER );
          current ++;
          break;
        case SPACE:
          regexp.append( SPACE );
          current ++;
          break;
        default:
          quit("malformed regexp = >" + s + "<");
      }
    }
    regexp.append('$');
    try {
      _pattern  = COMPILER.compile(regexp.toString());
    }
    catch(MalformedPatternException e) {
      quit( " malformed regexp  for "  + s  + "////" + regexp.toString());
    }
  }

  //============================================================================
  private boolean match( String s ) {
    _globalCounter  ++;
    _res =null;
    if (MATCHER.matches( s, _pattern )) {
      inc();
      _lastMatched  = this;
      _res = MATCHER.getMatch();
      return true;
    }
    return false;
  }

  //============================================================================
  private String getExpr( int rank ) {
    if ( (_res == null ) || (rank <= 0 ) )  return null;
    return _res.group( rank );
  }

  //============================================================================
  /**
   * @return  */  
  public String getHost()     {   return getExpr( _host );    }

  public String getRemoteIP() {
    String ip = getExpr( _ip );
    return ip;
  }

  public String getUser()     {   return getExpr( _user );    }

  public String getURI()      {   return getExpr( _uri );     }

  public String getStatus()   {   return getExpr( _status );  }

  public String getReferer()  {   return getExpr( _referer ); }

  public String getAgent()    {   return getExpr( _agent );   }

  public String getLanguage() {   return getExpr( _lang );    }

  public long   getSize()     {
    if ( (_res == null ) || (_size<= 0 ) )  return 0;
    String s = getExpr( _size );
    if (Utils.isEmpty( s ) ) return 0;
    else return Long.parseLong( s );
  }

  public Date getDate()     {
    StringBuffer sb = new StringBuffer();
    String tmp = null;
    tmp = getExpr( _day );
    if (tmp !=null)
      sb.append( tmp ).append( ' ' );
    tmp = getExpr( _month );
    if (tmp !=null)
      sb.append( tmp ).append( ' ' );
    tmp = getExpr( _year );
    if (tmp !=null)
      sb.append( tmp ).append( ' ' );
    else
      sb.append( Calendar.getInstance().get( Calendar.YEAR ) ).append( ' ' );
    tmp = getExpr( _hour );
    if (tmp !=null)
      sb.append( tmp ).append( ' ' );
    //    sb.deleteCharAt( sb.length() - 1 );
    Date date = null;
    try{
      date  = fd.parse( sb.toString() );
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }
  //============================================================================
  //----------------
  private void quit(String s ) {
    System.err.println("ERROR : " + s);
    System.err.println("exiting ");
    System.exit(0);
  }

  //============================================================================
  //----------------
  private static ArrayList    _availableRegexp  = new ArrayList();
  private static int          _globalCounter    = 0;
  private static RegexpData[] _arrayRe          = null;

  //----------------
  public static void addRegexp( RegexpData re ) {
    if (_lastMatched == null) _lastMatched = re;
    if (! _availableRegexp.contains( re ) ) { _availableRegexp.add( re );  }
    _arrayRe      =  (RegexpData[])_availableRegexp.toArray( new RegexpData[0] );
  }

  //----------------
  public static void updateList() {
    if ( _availableRegexp.size() ==1) return;
    Collections.sort( _availableRegexp );
    _arrayRe      =  (RegexpData[])_availableRegexp.toArray( new RegexpData[0] );
  }

  public static Line getLine( String s ) {
    //System.out.println("receive ==" + s);
    if ( Utils.isComment( s ) ) return null;
    s = s.trim();
    //System.out.println("line not a comment ");
    // need to update list for first use

    if ( (_globalCounter % 10000) ==0 )  {
      updateList();
      //      _globalCounter =0;
      //      dumpCounters();
    }

    int i=0;

    Line l = null;

    if (RegexpData._lastMatched.match( s ) ) {
      RegexpData re = _lastMatched;
      try {
        /////////////////
        if ( Config.siteConfig.ignoreLine( re ) )
          return Line.EMPTY_LINE;
        /////////////////
        l = new Line( re.getHost(), re.getDate(), re.getRemoteIP(), re.getURI(), re.getReferer(), re.getAgent(), re.getStatus(), re.getSize(), re.getUser());
        return l;
      } catch (Throwable t ){
        //t.printStackTrace();
      }
    }

    while ( ( i < _arrayRe.length) && ( !_arrayRe[i].match( s ) ) ) {
      i++;
    }
    try{
      if (i < _arrayRe.length) {
        RegexpData re = _arrayRe[i];
        /////////////////
        if ( Config.siteConfig.ignoreLine( re ) )
          return Line.EMPTY_LINE;
        /////////////////
        l = new Line( re.getHost(), re.getDate(), re.getRemoteIP(), re.getURI(), re.getReferer(), re.getAgent(), re.getStatus(), re.getSize(), re.getUser() );
        _lastMatched = re;
      }
    } catch ( Throwable t ) {/*t.printStackTrace();*/}
    return l;
  }

  //==========================================
  public final static void dumpCounters(){
    for (int i=0; i<_arrayRe.length; i++) {
      System.out.println("re  = " +_arrayRe[i].initialRegexp);
      System.out.println("match " + _arrayRe[i].getCount());
    }
  }
  //==========================================
  public final static void displayRegexp(){
    for (int i=0; i<_arrayRe.length; i++) {
      System.out.println(_arrayRe[i].initialRegexp);
    }
  }



}
