package org.novadeck.jxla.data;

import java.util.*;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GeneralLogData implements Serializable  {

  public static long total =0;

  // differents counters
  protected HashMap<String, SimpleData>   _referers;
  protected HashMap<String, SimpleData>   _keywords;
  protected HashMap<String, SimpleData>   _remote_ip;
  protected HashMap<String, SimpleData>   _hits;
  protected HashMap<String, SimpleData>   _pagesView;
  protected HashMap<String, SimpleData>   _files;
  protected HashMap<String, SimpleData>   _status;
  protected HashMap<String, SimpleData>   _userAgents;
  protected HashMap<String, SimpleData>   _users;
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
  protected void inc( String key, HashMap<String, SimpleData> map ){
    SimpleData obj = map.get( key );
    if (obj == null) {
      obj = new SimpleData();
      map.put( key, obj );
    }
    obj.inc();
  }

  //--------
  protected long getCount( HashMap<String, SimpleData> map ) {
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

}
