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
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/config/Config.java,v $
 * $Revision: 1.1 $
 * $Date: 2002/01/21 21:39:10 $
 * $Author: nioto $
 */
package org.novadeck.jxla.config;

import java.util.ArrayList;
import java.util.Collection;
import java.io.File;
import org.apache.oro.text.regex.Pattern;

import org.novadeck.jxla.tools.DNSCache;
import org.novadeck.jxla.tools.SearchEngine;
import org.novadeck.jxla.tools.Utils;
import org.novadeck.jxla.data.RegexpData;


public class Config {

  public static AbstractSiteConfig siteConfig ;
  // pages extension
  public  static String[] pagesEntensions = null;
  // dns data
  public static boolean  dnsEnable;
  public static DNSCache dnsDumpFile;
  //log files to analyze
  public static String[] logsFiles;
  // max data to dump
  public static int       maxRefers;
  public static int       maxKeywords;
  public static int       maxAgents;
  public static int       maxRemoteHosts;
  public static int       maxUris;
  public static int       maxNotFound;
  // summary page name
  public static String    summaryPageName = "index.xml";

  public static String    indexFileName  = "index.xml";

  // initialisze DNS informations
  public static void setDnsInfo( boolean  enable, String filename){
    dnsEnable = enable;
    if (dnsEnable && filename!=null){
      dnsDumpFile = DNSCache.load( filename );
    }
  }

  public static void dumpDnsCache(){
    if ( dnsEnable )
      dnsDumpFile.dump();
  }
  // set files to parse
  public static void setLogFiles( String directory, String regexp) {
    if ( directory == null )
      throw new IllegalArgumentException("directory of log files is null");
    File f = new File( directory );
    if (!f.exists() || !f.canRead()){
      throw new IllegalArgumentException("directory of log files idoen't not existes, or is not readable");
    }
    String[] list = f.list();
    Pattern pFiles = Utils.compileRE( regexp ) ;
    if ( pFiles == null)
      throw new IllegalArgumentException("regexp for log files can't compile check it ");
    Collection col = new ArrayList();
    for (int i=0; i<list.length; i++) {
      if ( Utils.match( list[i], pFiles ) )
        col.add( directory + (directory.endsWith("/")?  "":"/") + list[i] );
      else {
        System.out.println( " file "+ list[i] +" doesnt match "   + regexp );
      }
    }
    logsFiles = (String[])col.toArray( new String[0]);
  }

  // set extensions for pages
  public static void addPageExtension( String s ) {
    if ( pagesEntensions == null) {
      pagesEntensions = new String[1];
      pagesEntensions [0] = s;
    } else {
      String tmp[] = new String[ pagesEntensions.length +1 ];
      System.arraycopy(pagesEntensions,0, tmp,0, pagesEntensions.length);
      tmp[ pagesEntensions.length ] = s;
      pagesEntensions = tmp;
    }
  }

  public static void setMaxRefers( int max ){
    if (max >0)   maxRefers = max;
    else          throw new IllegalArgumentException("max referers is negative");
  }
  public static void setMaxKeywords( int max ){
    if (max >0)   maxKeywords = max;
    else          throw new IllegalArgumentException("max keywords is negative");
  }
  public static void setMaxAgents( int max ){
    if (max >0)   maxAgents = max;
    else          throw new IllegalArgumentException("max agents is negative");
  }
  public static void setMaxRemotehosts( int max ){
    if (max >0)   maxRemoteHosts = max;
    else          throw new IllegalArgumentException("max remote hosts negative");
  }
  public static void setMaxUris( int max ){
    if (max >0)   maxUris  = max;
    else          throw new IllegalArgumentException("max URIs is negative");
  }
  public static void setMaxNotFound( int max ){
    if (max >0)   maxNotFound  = max;
    else          throw new IllegalArgumentException("max not found is negative");
  }

  public static void setConfigClass( String s ) {
    try{
      Class c = Class.forName( s );
      siteConfig = (AbstractSiteConfig)c.newInstance();
    } catch ( Exception e ){
      e.printStackTrace();
    }
  }

  public static void addLogLineFormat( String s )  {
    RegexpData.addRegexp( new RegexpData( s ) );
  }

  public static void setSummaryPageName( String s )  {
    System.out.println("setSummaryPageName( " + s +" )" );
    summaryPageName = s;
  }

  public static void addSearchEngine( String name, String domain, String param )  {
    SearchEngine se = new SearchEngine( domain, param, name);
    SearchEngine.addSearchEngine( se );
  }
}
