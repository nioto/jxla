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
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/tools/SearchEngine.java,v $
 * $Revision: 1.1 $
 * $Date: 2002/01/21 21:39:11 $
 * $Author: nioto $
 */
package org.novadeck.jxla.tools;


import org.novadeck.jxla.data.SimpleData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SearchEngine  extends SimpleData {


  private String _domain;
  private String _parameter;
  private String _name;



  //============================================================================
  //----------------
  private SearchEngine() {
  }

  //----------------
  public SearchEngine( String domain, String parametername, String familiarName){
    _domain     = domain;
    _parameter  = parametername + "=";
    _name       = familiarName;
  }


  //============================================================================
  //----------------
  public boolean match( String host ){
    if ( host == null )   return false;
    host = host.toLowerCase();
    boolean bool = host.endsWith( _domain ) ;
    if ( bool )           inc();
    return bool;
  }

  //----------------
  public String getDomain(){
    return _domain;
  }
  //----------------
  public String getName(){
    return _name;
  }

  //----------------
  /**
   *   return the keeyword(s) urlencode, warning, think to decode keyword by
   *  java.net.URLDecoder.decode( xxx );
   *
   */
  public String getKeyword( String fullUri ) {
    int i = fullUri.indexOf('?');
    if ( i < 0 ) return null; // no query
    String keyword = null;
    fullUri = fullUri.substring( i + 1 );
    while ( (i>0) && (keyword == null) ) {
      i = fullUri.indexOf('&', 1 );
      if ( fullUri.startsWith( _parameter ) ) {
        if (i<0)
          keyword = fullUri.substring( _parameter.length());
        else
          keyword = fullUri.substring( _parameter.length(),i);
      }
      else
        fullUri = fullUri.substring(1+i);
    }
    if (keyword != null)
      keyword = java.net.URLDecoder.decode(keyword);

    return keyword;
  }
  //----------------
  public boolean equals( Object obj ){
    if (obj ==null)
      return false;
    SearchEngine se = (SearchEngine)obj;
    return (_domain.equals( se.getDomain() ) );
  }




  //============================================================================
  //----------------
  private static ArrayList      _searchEngines    = new ArrayList();
  private static int            _counter          = 0;
  private static SearchEngine[] _arrayEngines     = null;

  //----------------
  public static void addSearchEngine( SearchEngine se ) {
    if (! _searchEngines.contains( se ) ){
      _searchEngines.add( se );
    }
  }

  //----------------
  public static void updateList() {
    Collections.sort( _searchEngines );
    _arrayEngines  = (SearchEngine[]) _searchEngines.toArray( new SearchEngine[0] );
  }

  public static String[] getKeywords( String host, String uri ) {
    if ( Utils.isComment( host ) ) return null;

    // need to update list for first use
    if ( (_counter % 1000) ==0 )  updateList();
    _counter  ++;

    String[] res  = null;

    int i=0;
    while ( ( i < _arrayEngines.length) && ( !_arrayEngines[i].match( host ) ) ) {
      i++;
    }
    if (i < _arrayEngines.length) {
      SearchEngine se = _arrayEngines[i];
      res = new String[2];
      res[0]  = se.getName();
      res[1]  = se.getKeyword( uri );
    }

    if ( (res != null) && (res[1] != null))
      return res;
    else
      return null;
  }

}
