/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/data/MonthData.java,v $
 * $Revision: 1.3 $
 * $Date: 2005/01/06 13:18:54 $
 * $Author: nioto $
 */
package org.novadeck.jxla.data;


import java.util.*;

import org.novadeck.jxla.tools.*;
import org.novadeck.jxla.config.*;
import org.novadeck.jxla.Constants;


public class MonthData extends GeneralLogData implements Comparable {

  private static final long serialVersionUID = -2485540634100516741L;

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
    int i = l.getLogDate().getDate() - 1 ;
    DayData day = (DayData)_days.get( i );
    day.addLine( l );
  }

  //-----------------------
  public void add404( String s ) {
    inc( s, _notFound );
  }
  //-----------------------
  private String getMonthName() {
    return Constants.getMonth (_monthNumber);
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
    output.append( "<traffic>" + getTraffic() + "</traffic>\n"  );
    output.append( "</month>\n" );
    return output;
  }
  //-----------------------
  public void dumpDataToFile(String homePath, Date begin) {
    if (Config.DEBUG) 
      System.out.println("dumping month " + toString()  );
    try{
      Output out = new Output( homePath + "/" + getFileName() );
      out.writeln( Constants.HEADER_XML );
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


      out.writeln( "<traffic>"+ getTraffic() + "</traffic>"  );

      out.writeln( "</total>");

      /////////////////////
      // hits by day
      Date today = new Date();
      out.writeln( "<hitsbyday>");

      for (int i=0; i < _days.size(); i++) {
        Date current = new Date( _yearNumber , _monthNumber, 1+i, 23, 59, 59 );
        if ( current.before(today) && current.after(  begin ) ) {
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
        out.writeln( "<name>" + Constants.getDay (i) + "</name>" );
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
        out.writeln("<element><value>"+d.getData()+"</value><count>"+d.getCount()+"</count></element>");
      }
      out.writeln( "</referers>");
      /////////////////////
      //keywords
      col = hashToList( _keywords );
      number = 0;
      out.writeln( "<keywords>");
      for ( Iterator ite = col.iterator(); ite.hasNext() && number<Config.maxRefers ;number++){
        StringData d = (StringData)ite.next();
        out.writeln("<element><value>"+d.getData()+"</value><count>"+d.getCount()+"</count></element>");
      }
      out.writeln( "</keywords>");

      /////////////////////
      // remote IP
      col = hashToList( _remote_ip );
      number = 0;
      out.writeln( "<users_ips>");
      for ( Iterator ite = col.iterator(); ite.hasNext() && number<Config.maxRemoteHosts ;number++){
        StringData d = (StringData)ite.next();
        String value = d.getData ();
        if ( Config.dnsEnable){
          if ( value.startsWith ( "<![CDATA[" ) ) {
              value  = value.substring ( "<![CDATA[".length (), value.length () - "]]>".length () );
          }
          value   = Config.dnsDumpFile.gethostName( value );
        }       
        out.writeln("<element><value>"+value+"</value><count>"+d.getCount()+"</count></element>");
      }
      out.writeln( "</users_ips>");

      /*
       *
       */
      HashMap map = new HashMap();
      if (Config.DEBUG) 
      {
        System.out.println("working on Countries");
        System.out.println("col.size=="+col.size ());
      }
      for ( Iterator ite = col.iterator(); ite.hasNext() ;){
        StringData d = (StringData)ite.next();
        String value = d.getData();
        if ( value.startsWith ( "<![CDATA[" ) )
        {
            value  = value.substring ( "<![CDATA[".length (), value.length () - "]]>".length () );
        }
        String country ;
        try
        {
            java.net.InetAddress ia = java.net.InetAddress.getByName ( value );
            Locale l = net.wetters.InetAddressLocator.getLocale ( ia );
            country = l.getDisplayCountry ();
        }
        catch (java.net.UnknownHostException e)
        {
          country ="";
        }
        SimpleData data = (SimpleData)map.get ( country ) ;
        if ( data == null)
        {
            data = new StringData( country  );
            map.put( country, data );
        }
        data.add( d.getCount() );        
      }
      if (Config.DEBUG) 
        System.out.println("map.size="+ map.size ());
      col = hashToList( map );
      if (Config.DEBUG) 
        System.out.println("col.size"+col.size ());
      number = 0;
      out.writeln( "<users_countries>");
      for ( Iterator ite = col.iterator(); ite.hasNext() ;){
        StringData d = (StringData)ite.next();
        out.writeln("<element><value>"+d.getData()+"</value><count>"+d.getCount()+"</count></element>");
      }
      out.writeln( "</users_countries>");
      
      /*
       *
       */
      /////////////////////
      // remote IP
      col = hashToList( _users );
      number = 0;
      out.writeln( "<users>");
      for ( Iterator ite = col.iterator(); ite.hasNext() ;number++){
        StringData d = (StringData)ite.next();
        out.writeln("<element><value>"+ Config.siteConfig.getRealUserInfo( d.getData() )+"</value><count>"+d.getCount()+"</count></element>");
      }
      out.writeln( "</users>");
      try
      {
        /////////////////////
        // remote IP
        HashMap tmp [] = computeUserAgent2 ();
      
        if ( Config.maxBrowsers >0)
        {
          col = hashToList( tmp[0] );
          number = 0;
          out.writeln( "<user_agents>");
          for ( Iterator ite = col.iterator(); ite.hasNext() && number<Config.maxBrowsers;number++){
            StringData d = (StringData)ite.next();
            out.writeln("<element><value>"+d.getData()+"</value><count>"+d.getCount()+"</count></element>");
          }
          out.writeln( "</user_agents>");
        }
        ////////
        if ( Config.maxOS > 0)
        {
          col = hashToList( tmp[1] );
          number = 0;
          out.writeln( "<platforms>");
          for ( Iterator ite = col.iterator(); ite.hasNext() && number<Config.maxOS;number++){
            StringData d = (StringData)ite.next();
            out.writeln("<element><value>"+d.getData()+"</value><count>"+d.getCount()+"</count></element>");
          }
          out.writeln( "</platforms>");
        }
      //////////////////////
      }
      catch (Throwable t )
      {
          t.printStackTrace();
      }
      

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
      if (!( obj instanceof java.lang.String )) {
          System.err.println("################################################################");
          System.err.println("################################################################");
          System.err.println( "found data that is not a string : " +obj.getClass() );
          System.err.println("################################################################");
          System.err.println("################################################################");
          
      }
      list.add( new StringData( obj.toString(), ((SimpleData)map.get(obj)).getCount()) );
    }
    Collections.sort( list );
    return list;
  }

  //--------
  private HashMap[] computeUserAgent2() {
    Set set = _userAgents.keySet();
    HashMap browsers = new HashMap();
    HashMap oses = new HashMap();
    for (Iterator ite = set.iterator(); ite.hasNext(); )
    {
      Object key = ite.next();
      //String ua = Utils.getUserAgent( obj.toString() );
      if ( key == null)
        continue;
      
      String ua = key.toString ().toLowerCase ();
      String bot  = org.novadeck.jxla.tools.Bots.getBot ( ua );
      String browser ;
      String os ;
      if ( bot == null )
      {
          org.nioto.browser.Browser b = new org.nioto.browser.Browser( ua );
          // check browsers
          browser = b.getShortName () + "-" + b.getMajVersion () ;//+ b.getMinVersion();
          String tmp = (String)org.nioto.browser.B.getLongName ( b.getShortName() );
          if ( tmp ==null || tmp.length ()==0)
          {
              browser  ="unknown";
          }
          else
            browser =  tmp+ " " + b.getMajVersion()+b.getMinVersion ();
          // check os
          os = b.getOsName ();
      }
      else
      {
          browser = org.novadeck.jxla.tools.Bots.getBotName (bot);
          os = "none (bot) ";
      }
      
      Object value = _userAgents.get( key );
      SimpleData data = (SimpleData)browsers.get( browser );
      long total =((SimpleData)value).getCount(); 
      if ( data != null )
      {
        data.add( total );
      }
      else
      {
        browsers.put( browser, new SimpleData(total) );
      }
      data = (SimpleData)oses.get( os );
      if ( data != null )
      {
        data.add( ((SimpleData)value).getCount());
      }
      else
      {
        oses.put( os, new SimpleData(total) );
      }      
    }
    HashMap result [] = { browsers, oses };
    return result;
  }
  
  public int compareTo(java.lang.Object obj) throws ClassCastException {
    MonthData month = (MonthData)obj;
    if ( _yearNumber == month.getYear()) {
      return (_monthNumber - month.getMonth());
    }
    return (_yearNumber - month.getYear());
  }

  //--------
  public String toString(){
      return "MonthData:"+_monthNumber+"/"+_yearNumber +" tr=="+ getTraffic();
  }
  
  //--------
  private List computeHits() {
    HashMap map = new HashMap( _hits.size(), 1 );
    Object[] objects = _hits.keySet().toArray();
    for (int i = 0; i < objects.length; i++ ) {
      if ( Utils.canOutputHit( objects[i].toString() ) )
        map.put( objects[i], _hits.get(objects[i]) );
    }
    return hashToList( map );
  }
  
  /** 
   * The following 2 methods are only needed to pass from JXLA 1.0 to JXLA 1.1
   * In JXLA 1.0, all remote_ip are dns reversed ( when available ) in the serialized data.
   * In JXLA 1.1, we store remote_ip as ip and only dns reverse the top Config.maxRemoteHosts ips
   * to avoid unnecessary loss of time in dns queries. This save a lot of time.
   **/
  public void convertDnsNamesToIp( Map namesToIP){
    convertDnsNamesToIp ( this._remote_ip, namesToIP );
    for (Iterator ite = this._days.iterator (); ite.hasNext (); ){
      DayData day = (DayData)ite.next ();
      convertDnsNamesToIp( day._remote_ip, namesToIP );
    }
  }
  private static void convertDnsNamesToIp(Map input, Map namesToIP){
    Map newRemoteIp = new HashMap();
    Set entries = input.entrySet ();
    for ( Iterator ite = entries.iterator (); ite.hasNext () ; ){
      Map.Entry entry = (Map.Entry)ite.next ();
      Object obj = namesToIP.get ( entry.getKey () );
      if ( obj == null) 
        System.out.println("something wrong with :" + entry.getKey ());
      else
        newRemoteIp.put ( obj, entry.getValue ());
    }
    input.clear ();
    input.putAll ( newRemoteIp );
  }
  
}
