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
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/data/Line.java,v $
 * $Revision: 1.2 $
 * $Date: 2002/02/10 15:05:06 $
 * $Author: nioto $
 */
package org.novadeck.jxla.data;


import java.util.Date;
import org.novadeck.jxla.*;
import org.novadeck.jxla.config.*;
import org.novadeck.jxla.tools.SearchEngine;

public class Line {
  private Date    _date;
  private String  _host;
  private String  _remoteIP;
  private String  _uri;
  private String  _referer;
  private String  _userAgent;
  private Long    _status;
  private long    _size;
  private String  _user;
  
  private String  _keywords = null;
  
  public static final Line EMPTY_LINE = new Line();
  
  private Line() {
    _host = null;
  }
  
  public Line( String host, Date date, String remoteIp, String uri, String referer, String userAgent, String status, long size, String user) {
    _host       = Config.siteConfig.getRealHostName( host );
    if (_host == null){
      //System.out.println("hostName =" + host );
      throw new RuntimeException("host not found");
    }
    _date       = date;
    if ( Config.dnsEnable){
      _remoteIP   = Config.dnsDumpFile.gethostName( remoteIp );
    } else {
      _remoteIP   = remoteIp;
    }
    int i = uri.indexOf('?');
    // suppress query string
    if ( i>=0){
      uri = uri.substring(0, i);
    }
    else {
      uri        = uri;
    }
    if (uri.endsWith("/") ) {
      uri += Config.indexFileName;
    }
    _uri = uri;
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
              _keywords = re[1];
            }
          }
        }
      }
    }
    
    _referer    = referer;
    _userAgent  = userAgent;
    _status     = new Long(Integer.parseInt( status ));
    _size       = size;
    _user       = user;
  }
  
  
  
  public Date getDate(){
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
  
/*  private void parseReferer( String referer ){
    _referer = referer;
    if (referer.indexOf( "http://"+_host + "/")<0)
      _keywords = RegExp.getKeyword( referer );
  }
 */
  public String getReferer(){
    return this._referer;
  }
  
  public String getKeywords(){
    return this._keywords;
  }
  
  public String getVisitor(){
    return this._remoteIP;
  }
  
  public Long getStatus(){
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
  
  
/*    public static final boolean isComment( String s ) {
      return ( s ==null || s.length()==0 || s.charAt(0) == '#' );
    }
 */
  
  public boolean isLineEmpty(){
    return (this._host == null );
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
