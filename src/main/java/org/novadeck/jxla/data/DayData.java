package org.novadeck.jxla.data;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.novadeck.jxla.tools.Output;
import org.novadeck.jxla.tools.Utils;


@SuppressWarnings("serial")
public class DayData implements java.io.Serializable {

  // day of the month
  private int       _day;
  // month
  private MonthData     _father;
  
  // TODO: change to treemap
  protected Map<String,Object>   _referers= new HashMap<String,Object>();
  protected Map<String,String>   _remote_ip= new HashMap<String,String>();
  protected Map<String,Object>   _hits= new HashMap<String,Object>();
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

    int status = l.getStatus().intValue();
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
  protected long getCount( HashMap<String,SimpleData> map ) {
    Set<String> set = map.keySet();
    long total = 0;
    for (Iterator<String> ite = set.iterator(); ite.hasNext(); ) {
      SimpleData obj = map.get( ite.next() );
      total += obj.getCount();
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
