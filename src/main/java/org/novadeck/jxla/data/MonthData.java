package org.novadeck.jxla.data;


import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.novadeck.jxla.Constants;
import org.novadeck.jxla.config.Config;
import org.novadeck.jxla.tools.Output;
import org.novadeck.jxla.tools.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MonthData extends GeneralLogData implements Comparable<MonthData> {

  private static final long serialVersionUID = -2485540634100516741L;
  
  final Logger logger = LoggerFactory.getLogger( MonthData.class );

  //--------
  private List<DayData>   _days ;

  private Map<String,SimpleData>   _notFound;

  private int _monthNumber  = -1;
  private int _yearNumber   = -1;
  private int firstDay      = -1;

  //--------
  public MonthData(int month, int year) {
    super();
    _monthNumber  = month;
    _yearNumber   = year;
    _notFound     = new HashMap<String,SimpleData> ();
    GregorianCalendar dateTMP = new GregorianCalendar( _yearNumber, _monthNumber , 1);
    dateTMP.setTimeZone( TimeZone.getTimeZone("Central Standard Time") );
    firstDay  = dateTMP.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
    int nDays = dateTMP.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    _days = new ArrayList<DayData>(nDays);
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
    DayData day = _days.get( i );
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
    return "usage_" + _yearNumber + "" + ( month<10 ? "0" : "" ) + month + ".xml";
  }
  //-----------------------
  public StringBuilder getData() {
    StringBuilder output = new StringBuilder();
    output.append( "<month>\n" );
    output.append( "<name>" ).append( getMonthName() ).append( "</name>\n");
    output.append( "<number>" ).append( 1+_monthNumber ).append( "</number>\n");
    output.append( "<year>" ).append( _yearNumber ).append( "</year>");
    output.append( "<url>" ).append( getFileName() ).append( "</url>\n");
    output.append( "<hits>" ).append( getCount( _hits) ).append( "</hits>\n"   );
    output.append( "<files>" ).append( getCount( _files ) ).append( "</files>\n"  );
    output.append( "<pages>" ).append( getCount( _pagesView ) ).append( "</pages>\n"  );
    output.append( "<traffic>" ).append( getTraffic() ).append( "</traffic>\n"  );
    output.append( "</month>\n" );
    return output;
  }
  //-----------------------
  public void dumpDataToFile(String homePath, MyDate begin) {
    logger.debug("dumping month {}", this );
    try{
      Output out = new Output( homePath + "/" + getFileName() );
      out.writeln( Constants.HEADER_XML );
      out.writeln( "<month>");
      out.writeln( "<name>" + getMonthName() + "</name>");
      out.writeln( "<year>" + _yearNumber + "</year>");
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
      MyDate today = new MyDate();
      out.writeln( "<hitsbyday>");

      for (int i=0; i < _days.size(); i++) {
        MyDate current = new MyDate( _yearNumber , _monthNumber, 1+i, 23, 59, 59 );
        if ( current.before(today) && current.after(  begin ) ) {
          out.writeln(  "<day>");
          out.writeln(  "<number>" + (1+i) + "</number>" );
          DayData obj = _days.get( i );
          if ( obj != null){
            obj.dumpData( out );
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
        DayData day = _days.get( i );
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

      
      List<StringData> col = null;
      /////////////////////
      // status code
      dump( "status", out, hashToList( _status ), Integer.MAX_VALUE);
      /////////////////////
      // hits
      dump( "hits", out, computeHits(), Config.maxUris);
      /////////////////////
      // error 404 code
      dump( "notfound", out, hashToList( _notFound ), Config.maxNotFound );
      /////////////////////
      //referers
      dump( "referers", out, hashToList( _referers ), Config.maxRefers );
      /////////////////////
      //keywords
      dump( "keywords", out, hashToList(_keywords ), Config.maxKeywords);

      /////////////////////
      // remote IP
      col = hashToList( _remote_ip );
      int number = 0;
      out.writeln( "<users_ips>");
      for ( Iterator<StringData> ite = col.iterator(); ite.hasNext() && number<Config.maxRemoteHosts ;number++){
        StringData d = ite.next();
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
      Map<String, SimpleData> map = new HashMap<String, SimpleData>();
      
      logger.debug( " working on Countries,  col.size = {}", col.size ());
      
      for ( StringData d : col){
        String value = d.getData();
        if ( value.startsWith ( "<![CDATA[" ) ) {
            value  = value.substring ( "<![CDATA[".length (), value.length () - "]]>".length () );
        }
        String country ;
        try {
            InetAddress ia = InetAddress.getByName ( value );
            Locale l = net.wetters.InetAddressLocator.getLocale ( ia );
            country = l.getDisplayCountry ();
        } catch (java.net.UnknownHostException e) {
          country ="";
        }
        SimpleData data = map.get ( country ) ;
        if ( data == null) {
            data = new StringData( country  );
            map.put( country, data );
        }
        data.add( d.getCount() );        
      }
      logger.debug( "map.size = {}", map.size ());
      dump( "users_countries", out, hashToList(map), Integer.MAX_VALUE);
      
      /*
       *
       */
      /////////////////////
      // remote IP
      col = hashToList( _users );
      number = 0;
      out.writeln( "<users>");
      for ( Iterator<StringData> ite = col.iterator(); ite.hasNext() ;number++){
        StringData d = ite.next();
        out.writeln("<element><value>"+ Config.siteConfig.getRealUserInfo( d.getData() )+"</value><count>"+d.getCount()+"</count></element>");
      }
      out.writeln( "</users>");

      /////////////////////
      // Browser
      Pair<Map<String, SimpleData>> pair = computeUserAgent ();
      if ( Config.maxBrowsers >0) {
        col = hashToList( pair.getLeft() );
        dump( "user_agents", out, col, Config.maxBrowsers);
      }
      ////////
      // OS
      if ( Config.maxOS > 0) {
        col = hashToList( pair.getRight() );
        dump( "platforms", out, col, Config.maxOS );
      }

      out.writeln( "</month>");
      out.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //--------
	private Pair<Map<String, SimpleData>> computeUserAgent() {
    Map<String, SimpleData> browsers = new HashMap<String, SimpleData>();
    Map<String, SimpleData> oses = new HashMap<String, SimpleData>();
    for (String key : _userAgents.keySet() ) {
      if ( key == null)
        continue;
      
      String ua = key.toLowerCase ();
      String bot  = org.novadeck.jxla.tools.Bots.getBot ( ua );
      String browser ;
      String os ;
      if ( bot == null ) {
        org.nioto.browser.Browser b = new org.nioto.browser.Browser( ua );
        // check browsers
        browser = b.getShortName () + "-" + b.getMajVersion () ;//+ b.getMinVersion();
        String tmp = org.nioto.browser.B.getLongName ( b.getShortName() );
        if ( Utils.isEmpty( tmp ) ) {
            browser  ="unknown";
        } else {
          browser =  tmp + " " + b.getMajVersion()+b.getMinVersion ();
        }
        // check os
        os = b.getOsName ();
      } else {
        browser = org.novadeck.jxla.tools.Bots.getBotName (bot);
        os = "none (bot) ";
      }
      
      SimpleData  value = _userAgents.get( key );
      SimpleData data = browsers.get( browser );
      long total = value.getCount(); 
      if ( data != null ) {
        data.add( total );
      } else {
        browsers.put( browser, new SimpleData(total) );
      }
      data = oses.get( os );
      if ( data != null ) {
        data.add( value.getCount());
      } else {
        oses.put( os, new SimpleData(total) );
      }      
    }
    return new Pair<Map<String, SimpleData>> ( browsers, oses );
  }
  
  public int compareTo(MonthData month){
    if ( _yearNumber == month.getYear()) {
      return (_monthNumber - month.getMonth());
    }
    return (_yearNumber - month.getYear());
  }

  //--------
  private List<StringData> computeHits() {
    Map<String, SimpleData> map = new HashMap<String, SimpleData>( _hits.size(), 1 );
    for (String str : map.keySet()) {
      if ( Utils.canOutputHit( str) ) {
        map.put( str, _hits.get(str) );
    	}
		}
    return hashToList( map );
  }
  //--------
  public String toString(){
      return "MonthData:"+_monthNumber+"/"+_yearNumber +" tr=="+ getTraffic();
  }
}