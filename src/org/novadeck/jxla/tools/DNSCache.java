/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/tools/DNSCache.java,v $
 * $Revision: 1.3 $
 * $Date: 2005/01/06 13:18:54 $
 * $Author: nioto $
 */
package org.novadeck.jxla.tools;

import org.novadeck.jxla.config.Config;

import java.io.Serializable;

import java.util.HashMap;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.io.*;


public class DNSCache {

  HashMap _cache ;
  transient int     _added ;
  transient String  _filename;
  transient long    _time=0;
  transient int _failed =0;
  public DNSCache( HashMap map ) {
    _cache        = map;
    _added        = 0;
  }
  public DNSCache( String file ) {
    _cache        = new HashMap();
    _added        = 0;
    _filename     = file;
  }

  private void addEntry( String ip, String hostName ) {
    _added ++;
    _cache.put( ip, hostName);
/*    if (_added > 100){
      dump( );
      _added =0;
    }
 */
  }

  private void setFileName( String file ) {
    _filename = file;
  }
  public String getFileName() {
    return _filename;
  }
  public String gethostName( String ip ){
    Object obj  = _cache.get( ip );
    if (obj == null) {
      String s = ip;
long begin = System.currentTimeMillis ();
      try {
        InetAddress r = InetAddress.getByName( ip );
        s = r.getHostName();
      }
      catch (UnknownHostException e) {
_failed++;
      }
_time += ( System.currentTimeMillis () -begin);
      addEntry( ip, s ) ;
      return s;
    }
    return obj.toString();
  }

  public String toString(){
    return _cache.toString();
  }

  public static final DNSCache load( String file ) {
    DNSCache cache;
    try{
      File f = new File( file );
      if ( f.exists() ){
        FileInputStream ostream   = new FileInputStream( f );
        ObjectInputStream objIn   = new ObjectInputStream(ostream);
        HashMap map = (HashMap)objIn.readObject();
        cache = new DNSCache( map );
        cache.setFileName( file );
        objIn.close();
        ostream.close();
      }
      else{
        cache = new DNSCache( file );
        System.err.println("DNS cache file doesn't exist, it will be created at end");
      }
    } catch (Exception e) {
      System.err.println("something wrong happens when reading file " + file + " ex=" +e.getMessage()  );
      cache = new DNSCache( file );
    }
    return cache;
  }

  public void dump( ) {
    if ( _added == 0){
      System.out.println("no added entry in dns , not writing a new file");
    }
    if (Config.DEBUG) 
      System.out.println("dumping dns cache");
System.out.println("added " + _added + " entries " );    
System.out.println("failed =" + _failed+ " entries " );    
System.out.println("time to reverse ==" + _time);
    try{
      File f = new File( _filename );
      if (f.exists()) {
        File backup = new File( _filename +".bak" );
        f.renameTo( backup );
      }
      ObjectOutputStream objIn   = new ObjectOutputStream(new FileOutputStream( _filename ));
      objIn.writeObject( _cache );
      objIn.flush();
      objIn.close();
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("something wrong happens when writing file " + _filename );
    }
  }
  
  public HashMap getReverseMap()
  {
      java.util.Set s = _cache.keySet ();
      HashMap m = new HashMap();
      for (java.util.Iterator ite = s.iterator (); ite.hasNext () ;)
      {
          Object key = ite.next ();
          m.put ( _cache.get ( key ), key );
      }
      return m;
  }

}
