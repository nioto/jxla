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
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/data/MonthData.java,v $
 * $Revision: 1.1 $
 * $Date: 2002/01/21 21:39:11 $
 * $Author: nioto $
 */
package org.novadeck.jxla.data;


import java.util.*;

import org.novadeck.jxla.tools.*;
import org.novadeck.jxla.config.*;
import org.novadeck.jxla.Statics;


public class MonthData extends GeneralLogData implements Comparable {

  //--------
  private List   _days ;

  private HashMap   _notFound;

  private int _monthNumber  = -1;
  private int _yearNumber   = -1;
  private int firstDay      = -1;

  //--------
  public MonthData(int month, int year) {
    super();
    _monthNumber  = month;
    _yearNumber   = year;
    _notFound     = new HashMap();
    GregorianCalendar dateTMP = new GregorianCalendar( 1900 + _yearNumber, _monthNumber , 1);
    dateTMP.setTimeZone( TimeZone.getTimeZone("Central Standard Time") );
    firstDay  = dateTMP.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
    int nDays = dateTMP.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    //System.out.println("first day for month " + month +"/" + (1900+_yearNumber) + " is " + firstDay );
    //System.out.println("nb days for month " + month +"/" + (1900+_yearNumber) + " is " + nDays );
    _days = new ArrayList(nDays);
    for (int i=0; i< nDays; i++) {
      _days.add( new DayData(i, this) );
    }
  }

  //--------
  public int getMonth()   { return _monthNumber;  }
  public int getYear()    { return _yearNumber;   }


  //--------
  public void addLine( Line l ) {
    int i = l.getDate().getDate() - 1 ;
    DayData day = (DayData)_days.get( i );
    day.addLine( l );
  }

  //-----------------------
  public void add404( String s ) {
    inc( s, _notFound );
  }
  //-----------------------
  private String getMonthName() {
    return Statics.MONTH[_monthNumber];
    //    return Long.toString( _monthNumber );
  }
  //-----------------------
  private String getFileName(){
    int month = _monthNumber +1;
    return "usage_" + (_yearNumber +1900) + "" + ( month<10 ? "0" : "" ) + month + ".xml";
  }
  //-----------------------
  public StringBuffer getData() {
    StringBuffer output = new StringBuffer();
    output.append( "<month>\n" );
    output.append( "<name>" + getMonthName() + "</name>\n");
    output.append( "<number>" + (1+_monthNumber) + "</number>\n");
    output.append( "<year>" + (1900+_yearNumber) + "</year>");
    output.append( "<url>" + getFileName() + "</url>\n");
    output.append( "<hits>"   + getCount( _hits       ) + "</hits>\n"   );
    output.append( "<files>"  + getCount( _files      ) + "</files>\n"  );
    output.append( "<pages>"  + getCount( _pagesView  ) + "</pages>\n"  );
    output.append( "</month>\n" );
    return output;
  }
  //-----------------------
  public void dumpDataToFile(String homePath, Date begin) {
    try{
      Output out = new Output( homePath + "/" + getFileName() );
      out.writeln( Statics.HEADER_XML );
      out.writeln( "<month>");
      out.writeln( "<name>" + getMonthName() + "</name>");
      out.writeln( "<year>" + (1900+_yearNumber) + "</year>");

      out.writeln( "<total>");
      out.writeln( "<hits>"     + getCount( _hits       ) + "</hits>"   );
      out.writeln( "<files>"    + getCount( _files      ) + "</files>"  );
      out.writeln( "<pages>"    + getCount( _pagesView  ) + "</pages>"  );
      out.writeln( "<referer>"  + _referers.size()        + "</referer>"  );
      out.writeln( "<url>"      + _hits.size()            + "</url>"  );
      out.writeln( "<remote_ip>"+ _remote_ip.size()       + "</remote_ip>"  );

      /////////////////////
      //  Traffic by site
/*      long traffic = 0;
      for (int i=0; i < _days.size(); i++) {
        Object obj = _days.get( i );
        if ( obj!= null){
          traffic +=  ((DayData)obj).getTraffic();
        }
      }
 */
      out.writeln( "<traffic>"+ getTraffic() + "</traffic>"  );

      out.writeln( "</total>");

      /////////////////////
      // hits by day
      Date today = new Date();
      out.writeln( "<hitsbyday>");

      for (int i=0; i < _days.size(); i++) {
        Date current = new Date( _yearNumber , _monthNumber, 1+i, 23, 59, 59 );
        if ( current.compareTo(today)<0 && current.compareTo( begin )>0 ) {
          out.writeln(  "<day>");
          out.writeln(  "<number>" + (1+i) + "</number>" );
          Object obj = _days.get( i );
          if ( obj != null){
            ((DayData)obj).dumpData( out );
          } else {
            DayData.dumpEmptyData(out);
          }
          out.writeln(  "</day>");
        }
      }
      out.writeln( "</hitsbyday>");

      /////////////////////
      // hits by day of week
      long [][] total = new long[7][3];
      for (int i=0; i < _days.size(); i++) {
        Object obj = _days.get( i );
        DayData day = (DayData)obj;
        if (day != null) {
          total[(i+firstDay)%7][0] += day.getHits();
          total[(i+firstDay)%7][1] += day.getFiles();
          total[(i+firstDay)%7][2] += day.getPages();
        }
      }

      out.writeln( "<hitsbydayofweek>");
      for (int i=0; i<7; i++){
        out.writeln( "<day>");
        /////////////////////////////////////////////////////////////////////
        out.writeln( "<name>" + Statics.DAYS[i]         + "</name>" );
        out.writeln( "<number>" + i  + "</number>" );
        out.writeln( "<hits>" +total[i][0] +"</hits>");
        out.writeln( "<files>"+total[i][1] +"</files>");
        out.writeln( "<pages>"+total[i][2] +"</pages>");
        out.writeln( "</day>");
      }
      out.writeln( "</hitsbydayofweek>");





      List col = null;
      int number ;
      /////////////////////
      // status code
      col = hashToList( _status );
      number = 0;
      out.writeln( "<status>");
      for ( Iterator ite = col.iterator(); ite.hasNext() ;number++){
        StringData d = (StringData)ite.next();
        out.writeln("<element><value>"+d.getData()+"</value><count>"+d.getCount()+"</count></element>");
      }
      out.writeln( "</status>");
      /////////////////////
      // hits
      col = computeHits();
      number = 0;
      out.writeln( "<hits>");
      for ( Iterator ite = col.iterator(); ite.hasNext() && number<Config.maxUris ;number++){
        StringData d = (StringData)ite.next();
        out.writeln("<element><value>"+d.getData()+"</value><count>"+d.getCount()+"</count></element>");
      }
      out.writeln( "</hits>");
      /////////////////////
      // error 404 code
      col = hashToList( _notFound );
      number = 0;
      out.writeln( "<notfound>");
      for ( Iterator ite = col.iterator(); ite.hasNext() && number<Config.maxNotFound ;number++){
        StringData d = (StringData)ite.next();
        out.writeln("<element><value>"+d.getData()+"</value><count>"+d.getCount()+"</count></element>");
      }
      out.writeln( "</notfound>");
      /////////////////////
      //referers
      col = hashToList( _referers );
      number = 0;
      out.writeln( "<referers>");
      for ( Iterator ite = col.iterator(); ite.hasNext() && number<Config.maxRefers ;number++){
        StringData d = (StringData)ite.next();
        out.writeln("<element><value><![CDATA["+d.getData()+"]]></value><count>"+d.getCount()+"</count></element>");
      }
      out.writeln( "</referers>");
      /////////////////////
      //keywords
      col = hashToList( _keywords );
      number = 0;
      out.writeln( "<keywords>");
      for ( Iterator ite = col.iterator(); ite.hasNext() && number<Config.maxRefers ;number++){
        StringData d = (StringData)ite.next();
        out.writeln("<element><value><![CDATA["+d.getData()+"]]></value><count>"+d.getCount()+"</count></element>");
      }
      out.writeln( "</keywords>");

      /////////////////////
      // remote IP
      col = hashToList( _remote_ip );
      number = 0;
      out.writeln( "<users_ips>");
      for ( Iterator ite = col.iterator(); ite.hasNext() && number<Config.maxRemoteHosts ;number++){
        StringData d = (StringData)ite.next();
        out.writeln("<element><value>"+d.getData()+"</value><count>"+d.getCount()+"</count></element>");
      }
      out.writeln( "</users_ips>");

      /////////////////////
      // remote IP
      col = hashToList( _users );
      number = 0;
      out.writeln( "<users>");
      for ( Iterator ite = col.iterator(); ite.hasNext() ;number++){
        StringData d = (StringData)ite.next();
        out.writeln("<element><value><![CDATA["+ Config.siteConfig.getRealUserInfo( d.getData() )+"]]></value><count>"+d.getCount()+"</count></element>");
      }
      out.writeln( "</users>");

      /////////////////////
      // remote IP
      col = computeUserAgent();
      number = 0;
      out.writeln( "<user_agents>");
      for ( Iterator ite = col.iterator(); ite.hasNext() && number<Config.maxAgents;number++){
        StringData d = (StringData)ite.next();
        out.writeln("<element><value>"+d.getData()+"</value><count>"+d.getCount()+"</count></element>");
      }
      out.writeln( "</user_agents>");


      out.writeln( "</month>");
      out.close();




    }catch (Exception e) {
      e.printStackTrace();
    }
  }




  //--------
  private List hashToList( HashMap map ) {
    Set set = map.keySet();
    List list   = new ArrayList();
    for (Iterator ite = set.iterator(); ite.hasNext(); ) {
      Object obj = ite.next();
      list.add( new StringData( obj.toString(), ((SimpleData)map.get(obj)).getCount()) );
    }
    Collections.sort( list );
    return list;
  }


  //--------
  private List computeUserAgent() {
    Set set = _userAgents.keySet();
    HashMap newMap  =  new HashMap();
    for (Iterator ite = set.iterator(); ite.hasNext(); ) {
      Object obj = ite.next();
      String ua = Utils.getUserAgent( obj.toString() );
      if ( ua == null)
        continue;
      if ( newMap.containsKey( ua ) ){
        SimpleData data = (SimpleData)newMap.get(ua);
        data.add( ((SimpleData)_userAgents.get(obj)).getCount());
      } else {
        Object data = _userAgents.get(obj);
        newMap.put( ua, data);
      }
    }
    return hashToList( newMap);
  }

  public int compareTo(java.lang.Object obj) {
    MonthData month = (MonthData)obj;
    if ( _yearNumber == month.getYear()) {
      return (_monthNumber - month.getMonth());
    }
    return (_yearNumber - month.getYear());
  }


  //-----------------------
  public StringBuffer getDayData( int day , String siteName) {
    DayData data = (DayData)this._days.get( day );
    long traffic = data.getTraffic() ;
    if ( traffic <= 0) {
      System.out.println("traffic null for day " + day );
      return null;
    }
    System.out.println("traffic not null for day " + day );
    StringBuffer sb = new StringBuffer();
    sb.append( "<site>\n<name>" +siteName + "</name>\n");
    sb.append( "<hits>" + data.getHits()+"</hits>\n");
    sb.append( "<bandwidth>" + traffic +"</bandwidth>\n</site>\n");
    return sb;
  }

  //--------
  private List computeHits() {
    Object[] objects = _hits.keySet().toArray();
    for (int i = 0; i < objects.length; i++ ) {
      if ( !Utils.canOutputHit( objects[i].toString() ) )
        _hits.remove( objects[i] );
    }
    return hashToList( _hits );
  }

}
