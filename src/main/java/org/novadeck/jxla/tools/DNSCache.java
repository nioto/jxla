package org.novadeck.jxla.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DNSCache {

	final static Logger logger = LoggerFactory.getLogger( DNSCache.class );
	
  Map<String,String> _cache ;
  int     _added ;
  String  _filename;
  long    _time=0;
  int _failed =0;
  
  public DNSCache( Map<String, String> map ) {
    _cache        = map;
    _added        = 0;
  }
  public DNSCache( String file ) {
    _cache        = new HashMap<String, String>();
    _added        = 0;
    _filename     = file;
  }

  private void addEntry( String ip, String hostName ) {
    _added ++;
    _cache.put( ip, hostName);
  }

  private void setFileName( String file ) {
    _filename = file;
  }
  public String getFileName() {
    return _filename;
  }
  public String gethostName( String ip ){
    String name  = _cache.get( ip );
    if (name == null) {
      try {
        InetAddress r = InetAddress.getByName( ip );
        name = r.getHostName();
      }
      catch (UnknownHostException e) {
      	_failed++;
      	name = ip;
      }
      addEntry( ip, name ) ;
    }
    return name;
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
        @SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>)objIn.readObject();
        cache = new DNSCache( map );
        cache.setFileName( file );
        objIn.close();
        ostream.close();
      }
      else{
        cache = new DNSCache( file );
        logger.error("DNS cache file doesn't exist, it will be created at end");
      }
    } catch (Exception e) {
      logger.error( "something wrong happened when reading file {}", file, e);
      cache = new DNSCache( file );
    }
    return cache;
  }

  public void dump( ) {
    if ( _added == 0 ){
      logger.debug( "no added entry in dns , not writing a new file");
    }
    logger.debug("dumping dns cache");
    logger.debug("added {} entries ", _added );    
    logger.debug("failed {} entries ", _failed );    
    logger.debug("time to reverse : {}", _time);
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
    		logger.error("something wrong happens when writing file {}", _filename, e );
    }
  }
  
  public Map<String, String> getReverseMap() {
      Map<String, String> m = new HashMap<String, String>( _cache.size()+1, 1);
      for (Entry<String, String> entry : m.entrySet()) {
				m.put( entry.getValue(), entry.getKey() );
			}
      return m;
  }

}
