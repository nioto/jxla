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
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/data/Site.java,v $
 * $Revision: 1.1 $
 * $Date: 2002/01/21 21:39:11 $
 * $Author: nioto $
 */
package org.novadeck.jxla.data;


import java.text.*;
import java.util.*;

import org.novadeck.jxla.data.*;
import org.novadeck.jxla.tools.Output;
import org.novadeck.jxla.Statics;

public class Site {
  // all sites available
  private static HashMap _sites = new HashMap();

  // local variables
  //--------
  private String  _name;
  private List _months = new ArrayList();

  public Date beginLogDate = null;

  //============================================================================
  //--------
  public Site( String str ) {
    System.out.println(" creating " + str ) ;
    _name      = str;
  }

  public String getName() {
    return _name;
  }
  //============================================================================
  //--------
  public void addHit( Line l ) {
    if (beginLogDate == null) {
      beginLogDate = l.getDate();
    }
    int mm = l.getDate().getMonth() ;
    int yy = l.getDate().getYear()  ;

    MonthData month = getMonth( mm, yy );
    if (month == null) {
      month = new MonthData( mm , yy );
      _months.add( month );
    }
    month.addLine( l );
  }


  private MonthData getMonth( int month, int year ) {
    for (int i=_months.size()-1; i>=0  ; i--) {
      MonthData tmp = (MonthData)_months.get( i );
      if ( ( month == tmp.getMonth()) && ( year == tmp.getYear() ) ) {
        return tmp;
      }
    }
    return null;
  }


  //============================================================================
  public StringBuffer getMonthData( int month, int year ) {
    MonthData monthData = getMonth( month, year );
    if (monthData == null) return null;
    StringBuffer sb = new StringBuffer();
    sb.append( "<site>\n" );
    sb.append( "<name>"+_name + "</name>\n") ;
    sb.append( "<hits>" + monthData.getHits()+"</hits>\n");
    sb.append( "<bandwidth>" + monthData.getTraffic()+"</bandwidth>\n");
    sb.append( "</site>\n" );
    return sb;
  }
  //============================================================================
  public StringBuffer getDayData( int day, int month, int year ) {
    MonthData monthData = getMonth( month, year );
    if (monthData == null) return null;
    System.out.println("found month !!");
    return monthData.getDayData( day, _name );
  }
  //============================================================================
  //--------
  public static void addLine( Line l){
    Site s = getSite( l.getHost() );
    s.addHit( l );
  }
  //--------
  public StringBuffer getData(String statsDirectory) {
    Collections.sort( _months );
    StringBuffer output = new StringBuffer( Statics.HEADER_XML );
    output.append( "<site>\n" );
    output.append( "<name>"+_name + "</name>\n") ;
    MonthData m = (MonthData)_months.get( 0 );
    int currentMonth  = m.getMonth()  ;
    int currentYear   = m.getYear()   ;
    for ( int i =0; i< _months.size(); i++) {
      m = (MonthData)_months.get( i );
      //////////////////////////////////////////////////////////////////////////
      // print empty months
      while ( (currentMonth < m.getMonth()) && (currentYear <= m.getYear()) ) {
        MonthData tmp = new MonthData( currentMonth, currentYear );
        currentMonth ++;
        if ( currentMonth == 12 ) {
          currentYear ++;
          currentMonth =0;
        }
      }
      //////////////////////////////////////////////////////////////////////////
      output.append( m.getData( ) );
      m.dumpDataToFile( statsDirectory, beginLogDate ) ;
      currentMonth  = m.getMonth()  ;
      currentYear   = m.getYear()   ;
    }
    output.append( "</site>\n" );
    return output;
  }


  //============================================================================
  //--------
  public static Site getSite( String host ){
    Site  s = (Site)_sites.get( host );
    if ( s ==null) {
      s = new Site( host );
      _sites.put( host, s);
    }
    return s;
  }
  //--------
  public static String[] getSiteHosts() {
    Set list = _sites.keySet();
    String res [] = new String[list.size()];
    int current = 0;
    for (Iterator ite= list.iterator(); ite.hasNext(); ){
      res[current++] = ite.next().toString();
    }
    Arrays.sort( res );
    return res;
  }


  //////////////////////////////////////////////////////////////////////////////

}