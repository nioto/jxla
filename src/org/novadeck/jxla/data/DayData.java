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
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/data/DayData.java,v $
 * $Revision: 1.1 $
 * $Date: 2002/01/21 21:39:10 $
 * $Author: nioto $
 */
package org.novadeck.jxla.data;


import org.novadeck.jxla.tools.*;

import java.util.*;


public class DayData extends GeneralLogData {

  // day of the month
  private int       _day;
  // month
  private MonthData     _father;

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
      addReferer( tmp );
      _father.addReferer( tmp );
    }

    tmp = l.getKeywords();
    if (tmp!=null) {
      addKeywords( tmp );
      _father.addKeywords( tmp );
    }

    tmp = l.getVisitor();
    if (tmp!=null) {
      addRemoteIP( tmp );
      _father.addRemoteIP( tmp );
    }

    tmp = l.getURI();
    if ( Utils.isPageView( tmp ) ){
      addPageView( tmp );
      _father.addPageView( tmp );
    }

    addHit( tmp );
    _father.addHit( tmp );

    incStatus( l.getStatus(), _status );
    long status = l.getStatus().longValue();
    if ( status == 200 ){
      addFile( tmp );
      _father.addFile( tmp );
    }
    else if (status == 404) {
      _father.add404( tmp );
    }
    _father.addStatus( l.getStatus().toString() );

    tmp = l.getUserAgent();
    if (!Utils.isEmpty(tmp)){
      addUserAgent( tmp );
      _father.addUserAgent( tmp );
    }
    tmp = l.getUser();
    if (!Utils.isEmpty(tmp)){
      addUser( tmp );
      _father.addUser( tmp );
    }
    addTraffic( l.getSize() );
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
    out.writeln( "<hits>"       + getCount( _hits       ) + "</hits>"   );
    out.writeln( "<files>"      + getCount( _files      ) + "</files>"  );
    out.writeln( "<pages>"      + getCount( _pagesView  ) + "</pages>"  );
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

  public long getHits(){
    return getCount( _hits );
  }

  public long getFiles(){
    return getCount( _files );
  }

  public long getPages(){
    return getCount( _pagesView );
  }



}
