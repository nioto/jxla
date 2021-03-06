package org.novadeck.jxla;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.novadeck.jxla.config.Config;
import org.novadeck.jxla.data.GeneralLogData;
import org.novadeck.jxla.data.Line;
import org.novadeck.jxla.data.MyDate;
import org.novadeck.jxla.data.RegexpData;
import org.novadeck.jxla.data.Site;
import org.novadeck.jxla.tools.History;
import org.novadeck.jxla.tools.Output;
import org.novadeck.jxla.xml.XmlConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main
{
    
	final static Logger logger = LoggerFactory.getLogger( Main.class );
    
    public static void main (String args[])
    {
        try
        {
            XmlConfigurator.configure ( args[0] );
        } 
        catch (Exception e)
        {
        	logger.error( "Unable to configure app ", e);
        	System.exit (1);
        }
        if ( ( args.length > 1 ) && ( "viewConfig".equals ( args[1] ) ) )
        {
            Config.displayConfig ();
            System.exit (1);
        }
        long  current   = 0;
        long  badlines  = 0;
        long  tmpbadLines =0;
        long  beginTime = System.currentTimeMillis ();
        
        Calendar TODAY  = Calendar.getInstance();
        TODAY.set( Calendar.HOUR, 0);
        TODAY.set( Calendar.MINUTE, 0);
        TODAY.set( Calendar.SECOND, 0);
        TODAY.set( Calendar.MILLISECOND, 0);
        MyDate NOW = new MyDate( TODAY );
        
        //Date NOW = new Date ();
       // NOW = new Date ( NOW.getYear (), NOW.getMonth (), NOW.getDate (), 0,0,0 );
        
        try
        {
            String files[] = Config.logsFiles ;
            Output errors = new Output ( null  );
            for (int file=0; file < files.length; file ++)
            {
                File f = new File ( files[file] );
                if ( ! f.exists () )
                {
                    logger.error( "file does not exist {}", files[file] );
                    continue;
                }
                
                Reader reader = null;
                if ( files[file].endsWith ( ".gz" ))
                {
                    reader = new InputStreamReader ( new GZIPInputStream ( new FileInputStream ( f ) ) );
                }
                else
                {
                    reader = new FileReader ( f );
                }
                BufferedReader in = new BufferedReader ( reader );
                String currentLine;
                
                logger.debug( "Parsing file {}", files[file] );
                while ( (currentLine = in.readLine ()) != null)
                {
                    current++;
                    Line l = RegexpData.getLine ( currentLine );
                    if ( l == null )
                    {
                        badlines ++;
                        errors.writeln ( currentLine );
                    }
                    else if ( !l.isLineEmpty () )
                    {
                        boolean isOK = ( l.getLogDate ().compareTo ( NOW ) < 0 ) ;
                        if ( isOK ) Site.addLine ( l );
                    }
                }
                logger.debug( "badlines = {}", (badlines-tmpbadLines));
                logger.debug( "current = {}", current);
                tmpbadLines = badlines;
                in.close ();
              	logger.debug( "GeneralLogData : {}", GeneralLogData.total);
            }
            errors.close ();
        }
        catch (Throwable t)
        {
            if ( t instanceof java.lang.OutOfMemoryError)
            {
                logger.debug( "OutOfMemryError  {} / {}", Runtime.getRuntime ().freeMemory (), Runtime.getRuntime ().totalMemory () );
            }
            logger.error( "GeneralLogData {}", GeneralLogData.total, t);
            System.exit (1);
        }
        
        
        double time= ( (System.currentTimeMillis () - beginTime ) /100) /10;
        System.out.println ( time + " seconds to parse " + current + " lines ");
        
        logger.debug( "dumping data");
        beginTime = System.currentTimeMillis ();
        
        String[]  list = Site.getSiteHosts ();
        logger.debug( " nb of sites = {}", list.length);
        for ( int k=0; k < list.length; k++ )
        {
            StringBuilder sb = new StringBuilder ();
            Site s = Site.getSite ( list[k] );
            logger.debug( "dumping for site = {}", s.getName ());
            try
            {
                String statsDirectory = Config.siteConfig.getStatsDirectory ( list[k] );
                File f = new File ( statsDirectory );
                if ( ! f.exists () ) {
                    if ( !f.mkdirs () ) {
                    	logger.error( " ERROR CAN MAKE DIRECTORY {}", statsDirectory );
                      continue;
                    }
                }
                logger.debug( "writing {} / {}", statsDirectory, Config.summaryPageName);
                Output out  = new Output (  statsDirectory + "/"  + Config.summaryPageName) ;
                sb = s.getData ( statsDirectory );
                out.write ( sb.toString () );
                out.close ();
            }
            catch (Throwable t)
            {
                t.printStackTrace ();
            }
        }
    /*
     *Dont use global information
     */
        // search the first date to dump
        MyDate _beginTMP = new MyDate ();
        for ( int k=0; k < list.length; k++ )
        {
            Site s = Site.getSite ( list[k] );
            if ( s.getFirstLogDate ().compareTo ( _beginTMP ) < 0 ) _beginTMP = s.getFirstLogDate ();
        }
        _beginTMP.setHours (0);
        _beginTMP.setMinutes (0);
        _beginTMP.setSeconds (0);
        
        MyDate _prev ;
        Map<String, Output> files = new HashMap <String, Output>();
        for ( int k=0; k < list.length; k++ )
        {
            _prev = new MyDate (1);
            MyDate _begin = new MyDate(_beginTMP );
            
            Site site = Site.getSite ( list[k] );
            String path = Config.siteConfig.getMainDirForGeneralStat ( site.getName () );
            logger.debug( "path = {}", path);
            if ( path == null )   continue;
            
            try
            {
                while ( _begin.compareTo ( NOW ) < 0)
                {
                    //new month
                    if (_begin.getMonth () != _prev.getMonth () )
                    {
                    		StringBuilder sb  = site.getMonthData ( _begin.getMonth (), _begin.getYear () );
                        if (sb != null) {
                            String currentDir = path + "/" + (_begin.getYear ()) + "/" +(1+_begin.getMonth ()) +"/";
                            String summaryPage = currentDir + Config.summaryPageName;
                            Output _month ;
                            if ( ! files.containsKey ( summaryPage ) ) {
                                File f = new File ( currentDir );
                                if ( ! f.exists () ) {
                                    if (!f.mkdirs ()) {
                                    	logger.error( "ERROR creating {}", f.getPath () );
                                    }
                                }
                                _month = new Output ( summaryPage );
                                logger.debug( "GLOBAL = {}", summaryPage);
                                _month.writeln ( Constants.HEADER_XML );
                                _month.writeln ( "<monthsummary>" );
                                _month.writeln ( "<name>"  + Constants.getMonth(_begin.getMonth ()) + "</name>");
                                files.put ( summaryPage , _month );
                            } else {
                                _month = files.get ( summaryPage );
                            }
                            _month.writeln ( sb.toString () );
                        }
                    }
                    _prev   = _begin;
                    _begin  = new MyDate ( _prev.getTime ()+24*60*60*1000 );
                }
            } catch (Exception e ) {
                e.printStackTrace ();
            }
        }
        
        Set<String> keys = files.keySet ();
        for ( Iterator<String> ite = keys.iterator (); ite.hasNext (); ) {
            try {
                Output _month = files.get ( ite.next () );
                _month.writeln ( "</monthsummary>" );
                _month.close ();
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }
        
        
    /*
    if ( Config.siteConfig.getMainDirForGeneralStat() != null ) {
      String currentDir = Config.siteConfig.getMainDirForGeneralStat() + "/" + (1+_begin.getMonth()) +"/";
      try{
        while ( _begin.compareTo( NOW ) < 0) {
          //new month
          if (_begin.getMonth() != _prev.getMonth() ){
            currentDir = Config.siteConfig.getMainDirForGeneralStat() + "/" + (1+_begin.getMonth()) +"/";
            File f = new File( currentDir );
            if ( ! f.exists() ) {
              if (!f.mkdirs())
                System.out.println("ERROR creating " + f.getPath() );
            }
            Output _month = new Output( currentDir + Config.summaryPageName );
            _month.writeln( Constants.HEADER_XML );
            _month.writeln( "<monthsummary>" );
            _month.writeln( "<name>"  + Constants.MONTH[_begin.getMonth()] + "</name>");
     
            StringBuffer sb;
            for ( int k=0; k < list.length; k++ ) {
              Site s = Site.getSite( list[k] );
              // month dumped data
              sb  = s.getMonthData( _begin.getMonth(), _begin.getYear() );
              if (sb != null)
                _month.writeln( sb.toString() );
            }
            _month.writeln( "</monthsummary>" );
            _month.close();
          }
          _prev   = _begin;
          _begin  = new Date( _prev.getTime()+24*60*60*1000 );
        }
      } catch (Exception e ) {
        e.printStackTrace();
      }
    }
     */
        /* */
        time= ( (System.currentTimeMillis () - beginTime ) /100) /10;
        logger.debug( "found {} bad lines ", badlines);
        logger.debug( " {} seconds to dimp data ", time);
        History.saveHistory ();
        Config.dumpDnsCache ();
        RegexpData.dumpCounters (System.out);
        System.exit (0);
    }
    
}
