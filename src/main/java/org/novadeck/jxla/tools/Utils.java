package org.novadeck.jxla.tools;


import org.novadeck.jxla.config.Config;


public class Utils {

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

