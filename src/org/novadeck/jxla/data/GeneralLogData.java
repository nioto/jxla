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
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/data/GeneralLogData.java,v $
 * $Revision: 1.2 $
 * $Date: 2002/02/10 15:05:06 $
 * $Author: nioto $
 */
package org.novadeck.jxla.data;

import java.util.*;

import java.io.Serializable;

public class GeneralLogData implements Serializable  {


  // differents counters
  protected HashMap   _referers;
  protected HashMap   _keywords;
  protected HashMap   _remote_ip;
  protected HashMap   _hits;
  protected HashMap   _pagesView;
  protected HashMap   _files;
  protected HashMap   _status;
  protected HashMap   _userAgents;
  protected HashMap   _users;
  protected long      _traffic;

  public GeneralLogData() {
    _referers   = new HashMap();
    _keywords   = new HashMap();
    _remote_ip  = new HashMap();
    _hits       = new HashMap();
    _pagesView  = new HashMap();
    _files      = new HashMap();
    _status     = new HashMap();
    _userAgents = new HashMap();
    _users      = new HashMap();
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
  protected void inc( String key, HashMap map ){
    Object obj = map.get( key );
    if (obj == null) {
      obj = new SimpleData();
      map.put( key, obj );
    }
    ((SimpleData)obj).inc();
  }

  //--------
  protected long getCount( HashMap map ) {
    Set set = map.keySet();
    long total = 0;
    for (Iterator ite = set.iterator(); ite.hasNext(); ) {
      Object obj = map.get( ite.next() );
      total += ((SimpleData)obj).getCount();
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
