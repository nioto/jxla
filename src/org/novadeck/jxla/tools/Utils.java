/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/tools/Utils.java,v $
 * $Revision: 1.2 $
 * $Date: 2005/01/06 13:18:54 $
 * $Author: nioto $
 */
package org.novadeck.jxla.tools;

import org.apache.oro.text.regex.*;


import org.novadeck.jxla.config.Config;

public class Utils {

  //============================================================================
  public static Pattern compileRE( String regexp ) {
    try{
      return Constants.COMPILER.compile( regexp );
    } catch (MalformedPatternException e){
      return null;
    }
  }

  //============================================================================
  public static boolean match( String str, Pattern p ){
    return Constants.MATCHER.matches( str, p );
  }

  //============================================================================
  public static final boolean isEmpty( String s ){
    return ( (s==null) || (s.length()==0)) ;
  }

  //============================================================================
  public static final boolean isComment( String s ) {
    return ( isEmpty(s) || s.charAt(0) == '#' );
  }

  //============================================================================
  public static boolean isPageView( String s ) {
    if ( isEmpty( s ) )   return false;
    int i=0;
    boolean isPage = false ;
    while (  ( i < Config.pagesEntensions.length ) && !isPage  ) {
      isPage = s.endsWith( Config.pagesEntensions[i] );
      i++;
    }
    return isPage;
  }

  public static final boolean canOutputHit( String uri ){
    if ( uri == null)
      return false;
    return ( !uri.endsWith(".jpg") && !uri.endsWith(".png") &&
    !uri.endsWith(".gif") &&!uri.endsWith(".jpeg"));

  }

}

