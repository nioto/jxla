package org.novadeck.jxla.data;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.novadeck.jxla.tools.Output;

@SuppressWarnings("serial")
public class GeneralLogData implements Serializable  {

  public static long total =0;

  // differents counters
  protected Map<String, SimpleData>   _referers;
  protected Map<String, SimpleData>   _keywords;
  protected Map<String, SimpleData>   _remote_ip;
  protected Map<String, SimpleData>   _hits;
  protected Map<String, SimpleData>   _pagesView;
  protected Map<String, SimpleData>   _files;
  protected Map<String, SimpleData>   _status;
  protected Map<String, SimpleData>   _userAgents;
  protected Map<String, SimpleData>   _users;
  protected long      _traffic;

  public GeneralLogData() {
      total++;
    _referers   = new HashMap<String, SimpleData>();
    _keywords   = new HashMap<String, SimpleData>();
    _remote_ip  = new HashMap<String, SimpleData>();
    _hits       = new HashMap<String, SimpleData>();
    _pagesView  = new HashMap<String, SimpleData>();
    _files      = new HashMap<String, SimpleData>();
    _status     = new HashMap<String, SimpleData>();
    _userAgents = new HashMap<String, SimpleData>();
    _users      = new HashMap<String, SimpleData>();
    _traffic    = 0;
  }


  //============================================================================
  //--------
  public void addReferer( String s ) {
    inc( s, _referers );
  }
  //--------
  public void addKeywords( String s ) {
    inc( s, _keywords );
  }
  //--------
  public void addRemoteIP( String s ) {
    inc( s , _remote_ip );
  }
  public void addStatus( String s ) {
    inc( s, _status );
  }
  public void addUserAgent( String s ) {
    inc( s, _userAgents );
  }
  public void addUser( String s ) {
    inc( s, _users );
  }
  public void addHit( String s ) {
    inc( s , _hits );
  }
  public void addFile( String s ) {
    inc( s , _files );
  }
  public void addPageView( String s ) {
    inc( s , _pagesView);
  }
  //-----------------------
  protected void inc( String key, Map<String, SimpleData> map ){
    SimpleData obj = map.get( key );
    if (obj == null) {
      obj = new SimpleData();
      map.put( key, obj );
    }
    obj.inc();
  }

  //--------
  protected long getCount( Map<String, SimpleData> map ) {
    Set<String> set = map.keySet();
    long total = 0;
    for (Iterator<String> ite = set.iterator(); ite.hasNext(); ) {
      SimpleData obj = map.get( ite.next() );
      total += obj.getCount();
    }
    return total;
  }
  //============================================================================
  public long getTraffic(){
    return _traffic;
  }
  public void addTraffic(long l){
    _traffic = _traffic + l;
  }
  //============================================================================
  public long getPages(){
    return getCount(_pagesView);
  }
  //============================================================================
  public long getFiles(){
    return getCount( _files );
  }

  public long getHits(){
    return getCount( _hits );
  }

  protected <T extends SimpleData> void dump(String node, Output output, List<StringData> col, int max)
  throws IOException{
    int number = 0;
    output.write("<");
    output.write(node);
    output.writeln(">");
    
    for ( Iterator<StringData> ite = col.iterator(); ite.hasNext() && number < max; number++){
      StringData d = ite.next();
      output.write("<element><value>");
      output.write( d.getData() );
      output.write( "</value><count>" );
      output.write( d.getCount());
      output.writeln( "</count></element>");
    }
    
    output.write("</");
    output.write(node);
    output.writeln(">");
  }
  //--------
  protected<T extends SimpleData> List<StringData> hashToList( Map<String, T> map ) {
    Set<String> set = map.keySet();
    List<StringData> list   = new ArrayList<StringData>();
    for (Iterator<String> ite = set.iterator(); ite.hasNext(); ) {
      String obj = ite.next();
      list.add( new StringData( obj, map.get(obj).getCount()) );
    }
    Collections.sort( list );
    return list;
  }
}
