/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/data/DayData.java,v $
 * $Revision: 1.2 $
 * $Date: 2005/01/06 13:18:54 $
 * $Author: nioto $
 */
package org.novadeck.jxla.data;


import org.novadeck.jxla.tools.*;

import java.util.*;


public class DayData implements java.io.Serializable {

  // day of the month
  private int       _day;
  // month
  private MonthData     _father;
  
  protected HashMap   _referers= new HashMap();
  protected HashMap   _remote_ip= new HashMap();
  protected HashMap   _hits= new HashMap();
  long _traffic     = 0;
  long _hitsCount   = 0;
  long _pagesView   = 0;
  long _files       = 0;

  //============================================================================
  public DayData( int day, MonthData month) {
    super();
    _day        = day;
    _father     = month;
  }

  public int getDay() {
    return _day;
  }
  //============================================================================
  public void addLine( Line l ) {
    String tmp  = l.getReferer();
    if (tmp!=null) {
        _referers.put( tmp, null );
      _father.addReferer( tmp );
    }

    tmp = l.getKeywords();
    if (tmp!=null) {
      _father.addKeywords( tmp );
    }

    tmp = l.getVisitor();
    if (tmp!=null) {
        _remote_ip.put( tmp,null );
      _father.addRemoteIP( tmp );
    }

    tmp = l.getURI();
    if ( Utils.isPageView( tmp ) ){
        _pagesView ++;
      _father.addPageView( tmp );
    }

    _hits.put( tmp,null );
    _hitsCount++;
    _father.addHit( tmp );

    long status = l.getStatus().longValue();
    if ( status == 200 ){
        _files++;
      _father.addFile( tmp );
    }
    else if (status == 404) {
      _father.add404( tmp );
    }
    _father.addStatus( l.getStatus().toString() );

    tmp = l.getUserAgent();
    if (!Utils.isEmpty(tmp)){
      _father.addUserAgent( tmp );
    }
    tmp = l.getUser();
    if (!Utils.isEmpty(tmp)){
      _father.addUser( tmp );
    }
    _traffic += l.getSize();
    _father.addTraffic( l.getSize() );
  }

  //============================================================================
  private void incStatus( Long field, HashMap map) {
    Object obj = map.get( field );
    if (obj == null) {
      obj = new SimpleData( );
      map.put( field, obj );
    }
    ((SimpleData)obj).inc();
  }
  public void dumpData(Output out ) throws java.io.IOException {
    out.writeln( "<hits>"       + _hitsCount              + "</hits>"   );
    out.writeln( "<files>"      + _files                  + "</files>"  );
    out.writeln( "<pages>"      + _pagesView              + "</pages>"  );
    out.writeln( "<referers>"   + _referers.size()        + "</referers>"  );
    out.writeln( "<urls>"       + _hits.size()            + "</urls>"  );
    out.writeln( "<remote_ips>" + _remote_ip.size()       + "</remote_ips>"  );
    out.writeln( "<traffic>"    + _traffic                + "</traffic>"  );
  
  }

  public static  void dumpEmptyData( Output out ) throws java.io.IOException {
    out.writeln( "<hits>0</hits>"   );
    out.writeln( "<files>0</files>"  );
    out.writeln( "<pages>0</pages>"  );
    out.writeln( "<referers>0</referers>"  );
    out.writeln( "<urls>0</urls>"  );
    out.writeln( "<remote_ips>0</remote_ips>"  );
    out.writeln( "<traffic>0</traffic>"  );
  }
  protected long getCount( HashMap map ) {
    Set set = map.keySet();
    long total = 0;
    for (Iterator ite = set.iterator(); ite.hasNext(); ) {
      Object obj = map.get( ite.next() );
      total += ((SimpleData)obj).getCount();
    }
    return total;
  }

  public long getHits(){
    return _hitsCount;
  }

  public long getFiles(){
    return _files;
  }

  public long getPages(){
    return _pagesView ;
  }
  
}
