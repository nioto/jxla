/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/tools/SearchEngine.java,v $
 * $Revision: 1.3 $
 * $Date: 2005/01/06 13:18:54 $
 * $Author: nioto $
 */
package org.novadeck.jxla.tools;


import org.novadeck.jxla.data.SimpleData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.novadeck.jxla.config.Config;

public class SearchEngine  extends SimpleData {


  private String _domain;
  private String[] _parameters;
  private String _name;



  //============================================================================
  //----------------
  private SearchEngine() {
  }

  //----------------
  private SearchEngine( String domain, String parametername, String familiarName){
    _domain       = domain;
    _parameters   = new String[1];
    _parameters[0]= parametername + "=";
    _name         = familiarName;
  }
  private void addParameter(String parameter){
    int length = _parameters.length;
    String tmp[] = new String[ length +1 ];
    System.arraycopy (_parameters, 0, tmp, 0, length);
    tmp[ length ] = parameter + "=";
    _parameters = tmp;
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
   *   return the keyword(s) urlencode, warning, think to decode keyword by
   *  java.net.URLDecoder.decode( xxx );
   *
   */
  public String getKeyword( String fullUri ) {
    int i = fullUri.indexOf('?');
    if ( i < 0 ) return null; // no query string
    String keyword = null;
    fullUri = fullUri.substring( i + 1 );
    for ( int k=0; keyword == null && k<_parameters.length; k++ )
    {
      String queryString = fullUri;
      i = 1;
      while ( (i>0) && (keyword == null) ) {
        i = queryString.indexOf('&', 1 );
        if ( queryString.startsWith( _parameters[k] ) ) {
          if (i<0)
            keyword = queryString.substring( _parameters[k].length() );
          else
            keyword = queryString.substring( _parameters[k].length(),i);
        }
        else if ( i >= 0)
          queryString = queryString.substring(1+i);
      }
    }
    if (keyword != null)
    {
      keyword = java.net.URLDecoder.decode(keyword);
    
        //remove cache: from google
        int index = keyword.indexOf ( "cache:" );
        if ( index >=0)
        {
            int endCache= keyword.indexOf ( ' ', index );
            if( endCache >=0 )
            {
                if( index ==0)
                    keyword = keyword.substring ( endCache ).trim ();
                else
                {
                    String tmp = keyword.substring (0, index).trim () + " " + keyword.substring ( endCache ).trim ();
                    keyword = tmp.trim ();
                }
                keyword += " ( via Google Cache )";
            }
            else
                if (Config.DEBUG) 
                  System.out.println( keyword );
        }
    }

    return keyword;
  }

  //============================================================================
  //----------------
  private static ArrayList      _searchEngines    = new ArrayList();
  private static int            _counter          = 0;
  private static SearchEngine[] _arrayEngines     = null;

  //----------------
  public static void addSearchEngine( String name, String domain, String param ) {
    SearchEngine se = null;
    for ( Iterator ite = _searchEngines.iterator (); se ==null && ite.hasNext (); ){
      SearchEngine tmp = (SearchEngine)ite.next ();
      if ( tmp.match ( domain) ){
        se = tmp;
      }
    }
    if (se != null){
      se.addParameter ( param );
    }
    else {
      _searchEngines.add( new SearchEngine ( domain, param, name ) );
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
