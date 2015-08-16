package org.novadeck.jxla.data;


import java.util.Date;

import org.novadeck.jxla.config.Config;
import org.novadeck.jxla.tools.SearchEngine;

public class Line {
  private Date    _date = new Date(0);
  private String  _host;
  private String  _remoteIP;
  private String  _uri;
  private String  _referer;
  private String  _userAgent;
  private Integer _status;
  private long    _size;
  private String  _user;
  private int     m_time;
  private String  m_webServer;

  private String _fullUri;
  private String  _keywords = null;

  private static Line INSTANCE = new Line();

  private Line() {
  }

  public void release() {
    _host=null;
    _remoteIP=null;
    _uri=null;
    _fullUri=null;
    _referer=null;
    _userAgent=null;
    _status=null;
    _size=-1;
    _user=null;
    _keywords = null;
    m_time =0;
    m_webServer = null;
  }
  
  public static Line getLine( String host, int hour,int day, int month, int year, String remoteIp, String uri, String referer,
                              String userAgent, String status, long size, String user, int time, String webServer) {
    INSTANCE.release();
    INSTANCE._host       = Config.siteConfig.getRealHostName( host );
    if (INSTANCE._host == null){
      return null;
    }
    INSTANCE._date.setHours ( hour );
    INSTANCE._date.setDate( day );
    INSTANCE._date.setMonth( month );
    if ( year >1900)
        year = year - 1900;
    INSTANCE._date.setYear( year );
/*    if ( Config.dnsEnable){
      INSTANCE._remoteIP   = Config.dnsDumpFile.gethostName( remoteIp );
    } else {
    }
 */
    INSTANCE._remoteIP   = remoteIp;
    INSTANCE._fullUri = uri;
    int i = uri.indexOf('?');
    // suppress query string if any
    if ( i>=0){
      uri = uri.substring(0, i);
    }

    if (uri.endsWith("/") ) {
      uri += Config.indexFileName;
    }
    INSTANCE._uri = uri;
    if (referer != null)  {
      if (referer .startsWith( "http://" )) {
        if  (referer.toLowerCase().startsWith("http://"+host) ){
          referer = null;
        } else { //check search engine
          i= referer.indexOf( '/', 7);
          if (i>0) {
            String[] re = SearchEngine.getKeywords( referer.substring(7, i) , referer.substring( i ));
            if (re!=null) {
              referer   = re[0];
              INSTANCE._keywords = re[1];
            }
          }
        }
      }
    }

    INSTANCE._referer    = referer;
    INSTANCE._userAgent  = userAgent;
    INSTANCE._status     = Integer.parseInt( status );
    INSTANCE._size       = size;
    INSTANCE._user       = user;
    INSTANCE.m_time      = time;
    INSTANCE.m_webServer = webServer;
    return INSTANCE;
  }



  public Date getLogDate(){
    return  _date;
  }

  public String getHost(){
    return _host;
  }

  public String getURI(){
    return _uri;
  }

  public int getMonth(){
    return _date.getMonth();
  }

  public String getReferer(){
    return this._referer;
  }

  public String getKeywords(){
    return this._keywords;
  }

  public String getVisitor(){
    return this._remoteIP;
  }

  public Integer getStatus(){
    return this._status;
  }

  public String getUserAgent() {
    return this._userAgent;
  }

  public long getSize() {
    return this._size;
  }

  public String getUser() {
    return this._user;
  }

  public int getTime()
  {
      return this.m_time;
  }
  
  public String getServer()
  {
      return this.m_webServer;
  }
  public boolean isLineEmpty(){
    return (this._host == null );
  }
  public String getFullURI(){
    return _fullUri;
  }

  public String toString(){
    StringBuffer sb = new StringBuffer();
    sb.append( "date==" + _date + "\n");
    sb.append( "host ==" + _host  + "\n");
    sb.append( "uri ==" + _uri  + "\n");
    sb.append( "remoteIP ==" + _remoteIP  + "\n");
    sb.append( "referer ==" + _referer  + "\n");
    sb.append( "status ==" + _status  + "\n");
    sb.append( "date ==" + _date+ "\n");
    sb.append( "agent ==" + _userAgent+ "\n");
    return sb.toString();
  }
}
