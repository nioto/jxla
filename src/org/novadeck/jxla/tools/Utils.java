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
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/tools/Utils.java,v $
 * $Revision: 1.1 $
 * $Date: 2002/01/21 21:39:11 $
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
  public static final String UA_IE         = "msie";
  public static final String UA_OPERA      = "opera";
  public static final String UA_KONQUEROR  = "konqueror";
  public static final String UA_NETSCAPE   = "mozilla";
  public static final String UA_LYNX       = "lynx";
  public static final String UA_BOT        = "bot";
  public static final String UA_PHP        = "php";
  public static final String UA_NETBOX     = "netgem";


  public static String getUserAgent( String s ){
    if (Utils.isEmpty( s ) )
      return null;
    s= s.toLowerCase();
    if( s.indexOf( UA_BOT ) >=0)
      return "Bots";
    else if ( s.indexOf( UA_IE ) >=0)
      return "Internet Explorer";
    else if( s.indexOf( UA_OPERA ) >=0)
      return "Opera";
    else if( s.indexOf( UA_KONQUEROR ) >=0)
      return "Konqueror";
    else if( s.indexOf( UA_NETBOX ) >=0)
      return "NetBox";
    else if( s.indexOf( UA_NETSCAPE ) >=0)
      return "NetScape";
    else if( s.indexOf( UA_LYNX ) >=0)
      return "Lynx";
    else if( s.indexOf( UA_PHP ) >=0)
      return "PHP";
    else
      return "Unkown Browser";
  }

  public static final boolean canOutputHit( String uri ){
    if ( uri == null)
      return false;
    return ( !uri.endsWith(".jpg") && !uri.endsWith(".png") &&
    !uri.endsWith(".gif") &&!uri.endsWith(".jpeg"));

  }

}

