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
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/Main.java,v $
 * $Revision: 1.3 $
 * $Date: 2002/02/10 15:05:06 $
 * $Author: nioto $
 */



package org.novadeck.jxla;


import java.io.*;
import java.util.*;
import java.text.*;
import java.util.zip.*;

import org.novadeck.jxla.tools.*;
import org.novadeck.jxla.config.*;
import org.novadeck.jxla.data.*;
import org.novadeck.jxla.xml.XmlConfigurator;


public class Main {
  
  
  public static void main(String args[]) {
    try{
      XmlConfigurator.configure( args[0] );
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    if ( ( args.length > 1 ) && ( "viewConfig".equals( args[1] ) ) ) {
      Config.displayConfig();
      System.exit(1);
    }
    Config.siteConfig.getMainDirForGeneralStat()     ;
    long  current   = 0;
    long  badlines  = 0;
    
    long  beginTime = System.currentTimeMillis();
    Date NOW = new Date();
    NOW = new Date(NOW.getYear(), NOW.getMonth(), NOW.getDate() );
    
    System.out.println("BEGINNING !! @ " + NOW );
    try {
      String files[] = Config.logsFiles ;
      Output errors = new Output( null  );
      for (int file=0; file < files.length; file ++) {
        System.out.println("i = " + file);
        File f = new File( files[file] );
        if ( ! f.exists() ) {
          System.out.println("file does not exist " + files[file] );
          continue;
        }
        
        BufferedReader in = null;
        if ( files[file].endsWith( ".gz" )){
          GZIPInputStream gzin = new GZIPInputStream( new FileInputStream( f ) );
          InputStreamReader inStream = new InputStreamReader( gzin );
          in = new BufferedReader( inStream );
        }
        else{
          in = new BufferedReader( new FileReader( f ) );
        }
        
        //        BufferedReader in = new BufferedReader( new FileReader( files[file] ) );
        String currentLine;
        System.out.println("Parsing file " + files[file] );
        boolean flag = true;
        while ( ( (currentLine = in.readLine()) != null) && flag ) {
          current++;
          Line l = RegexpData.getLine( currentLine );
          if ( l == null ) {
            badlines ++;
            errors.writeln( currentLine );
          }
          else{
            if (! l.isLineEmpty() ) {
              //System.out.println( l );
              boolean isOK = (l.getDate().compareTo( NOW )<0) ;
              if ( isOK )
                Site.addLine( l );
              else
                System.out.println("is == false ");
            }
          }
        }
        in.close();
      }
      errors.close();
    }
    catch (Throwable t) {
      t.printStackTrace();
    }
    
    double time= ( (System.currentTimeMillis() - beginTime ) /100) /10;
    System.out.println( time + " seconds to parse " + current + " lines ");
    
    System.out.println("dumping data");
    beginTime = System.currentTimeMillis();
    
    String[]  list = Site.getSiteHosts();
    System.out.println(" nb of sites = "  +list.length);
    System.out.println("dumping data");
    for ( int k=0; k < list.length; k++ ) {
      StringBuffer sb = new StringBuffer();
      Site s = Site.getSite( list[k] );
      System.out.println("dumping for site = " + s.getName());
      try {
        String statsDirectory = Config.siteConfig.getStatsDirectory( list[k] );
        File f = new File( statsDirectory );
        if ( ! f.exists() ) {
          if ( !f.mkdirs() ) {
            System.out.println(" ERROR CAN MAKE DIRECTORY " + statsDirectory );
            continue;
          }
        }
        
        System.out.println("dumping summary for site = " + s.getName());
        Output out  = new Output(  statsDirectory + "/"  + Config.summaryPageName) ;
        sb = s.getData( statsDirectory );
        out.write( sb.toString() );
        out.close();
      }
      catch (Throwable t) {
        t.printStackTrace();
      }
    }
    /*
     *Dont use global information
    // search the first date to dump
    Date _begin = new Date();
    for ( int k=0; k < list.length; k++ ) {
      Site s = Site.getSite( list[k] );
      if ( s.beginLogDate.compareTo( _begin ) < 0 ) _begin = s.beginLogDate;
    }
    _begin.setHours(0);
    _begin.setMinutes(0);
    _begin.setSeconds(0);
     
    Date _prev = new Date(1);
     
    if ( Config.siteConfig.getMainDirForGeneralStat() != null ) {
      String currentDir = Config.siteConfig.getMainDirForGeneralStat() + "/" + (1+_begin.getMonth()) +"/";
      try{
        while ( _begin.compareTo( NOW ) < 0) {
          System.out.println( _begin.getTime() + " -- " + NOW.getTime() );
          //new month
          if (_begin.getMonth() != _prev.getMonth() ){
            currentDir = Config.siteConfig.getMainDirForGeneralStat() + "/" + (1+_begin.getMonth()) +"/";
            File f = new File( currentDir );
            if ( ! f.exists() ) {
              if (!f.mkdirs())
                System.out.println("ERROR creating " + f.getPath() );
            }
            Output _month = new Output( currentDir + Config.summaryPageName );
            _month.writeln( Statics.HEADER_XML );
            _month.writeln( "<monthsummary>" );
            _month.writeln( "<name>"  + Statics.MONTH[_begin.getMonth()] + "</name>");
     
            for ( int k=0; k < list.length; k++ ) {
              Site s = Site.getSite( list[k] );
              // month dumped data
              StringBuffer sb = s.getMonthData( _begin.getMonth(), _begin.getYear() );
              if (sb != null)
                _month.writeln( sb.toString() );
            }
            _month.writeln( "</monthsummary>" );
            _month.close();
          }
          File f = new File( currentDir + _begin.getDate() +".xml" );
          if ( ! f.exists() ) {
            Output dayFile = new Output( f.getAbsolutePath() );
            dayFile.writeln( Statics.HEADER_XML );
            dayFile.writeln( "<daysummary>" );
            for ( int k=0; k < list.length; k++ ) {
              Site s = Site.getSite( list[k] );
              StringBuffer sb = s.getDayData( _begin.getDate()-1, _begin.getMonth(), _begin.getYear() );
              if (sb != null) {
                dayFile.writeln( sb.toString() );
              }
            }
            dayFile.writeln( "</daysummary>" );
            dayFile.close();
          }
          _prev   = _begin;
          _begin  = new Date( _prev.getTime()+24*60*60*1000 );
        }
      } catch (Exception e ) {
        e.printStackTrace();
      }
    }
     */
    time= ( (System.currentTimeMillis() - beginTime ) /100) /10;
    System.out.println("found "+ badlines + " bad lines ");
    System.out.println( time + " seconds to dimp data ");
    
    Config.dumpDnsCache();
    History.saveHistory();
    RegexpData.dumpCounters();
  }
  
}
