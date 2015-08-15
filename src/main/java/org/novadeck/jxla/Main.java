package org.novadeck.jxla;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.novadeck.jxla.config.Config;
import org.novadeck.jxla.data.GeneralLogData;
import org.novadeck.jxla.data.Line;
import org.novadeck.jxla.data.RegexpData;
import org.novadeck.jxla.data.Site;
import org.novadeck.jxla.tools.History;
import org.novadeck.jxla.tools.Output;
import org.novadeck.jxla.xml.XmlConfigurator;


public class Main
{
    
    
    public static void main (String args[])
    {
        try
        {
            XmlConfigurator.configure ( args[0] );
        } 
        catch (Exception e)
        {
            e.printStackTrace ();
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
        Date NOW = TODAY.getTime();
        
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
                    System.err.println ("file does not exist " + files[file] );
                    continue;
                }
                
                BufferedReader in = null;
                if ( files[file].endsWith ( ".gz" ))
                {
                    GZIPInputStream gzin = new GZIPInputStream ( new FileInputStream ( f ) );
                    InputStreamReader inStream = new InputStreamReader ( gzin );
                    in = new BufferedReader ( inStream );
                }
                else
                {
                    in = new BufferedReader ( new FileReader ( f ) );
                }
                String currentLine;
                
                if (Config.DEBUG) 
                  System.out.println ("Parsing file " + files[file] );
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
                if (Config.DEBUG) 
                {
                  System.out.println ("badlines = " + (badlines-tmpbadLines));
                  System.out.println ("current =" + current);
                }
                tmpbadLines = badlines;
                in.close ();
                if (Config.DEBUG) 
                {
                  System.out.println ("GeneralLogData " + GeneralLogData.total);
                  System.gc ();
                  System.out.println (Runtime.getRuntime ().freeMemory () + "/" + Runtime.getRuntime ().totalMemory () );
                }
            }
            errors.close ();
        }
        catch (Throwable t)
        {
            if ( t instanceof java.lang.OutOfMemoryError)
            {
                System.out.println (Runtime.getRuntime ().freeMemory () + "/" + Runtime.getRuntime ().totalMemory () );
            }
            System.err.println ("GeneralLogData " + GeneralLogData.total);
            t.printStackTrace ();
            System.exit (1);
        }
        
        
        double time= ( (System.currentTimeMillis () - beginTime ) /100) /10;
        System.out.println ( time + " seconds to parse " + current + " lines ");
        
        if (Config.DEBUG) 
          System.out.println ("dumping data");
        beginTime = System.currentTimeMillis ();
        
        String[]  list = Site.getSiteHosts ();
        if (Config.DEBUG) 
          System.out.println (" nb of sites = "  +list.length);
        for ( int k=0; k < list.length; k++ )
        {
            StringBuffer sb = new StringBuffer ();
            Site s = Site.getSite ( list[k] );
            if (Config.DEBUG) 
              System.out.println ("dumping for site = " + s.getName ());
            try
            {
                String statsDirectory = Config.siteConfig.getStatsDirectory ( list[k] );
                File f = new File ( statsDirectory );
                if ( ! f.exists () )
                {
                    if ( !f.mkdirs () )
                    {
                        System.err.println (" ERROR CAN MAKE DIRECTORY " + statsDirectory );
                        continue;
                    }
                }
                if (Config.DEBUG) 
                  System.out.println ("writing " +statsDirectory + "/"  + Config.summaryPageName);
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
        Date _beginTMP = new Date ();
        for ( int k=0; k < list.length; k++ )
        {
            Site s = Site.getSite ( list[k] );
            if ( s.getFirstLogDate ().compareTo ( _beginTMP ) < 0 ) _beginTMP = s.getFirstLogDate ();
        }
        _beginTMP.setHours (0);
        _beginTMP.setMinutes (0);
        _beginTMP.setSeconds (0);
        
        Date _prev ;
        Map<String, Output> files = new HashMap <String, Output>();
        for ( int k=0; k < list.length; k++ )
        {
            _prev = new Date (1);
            Date _begin = new Date ( _beginTMP.getTime () ) ;
            
            Site site = Site.getSite ( list[k] );
            String path = Config.siteConfig.getMainDirForGeneralStat ( site.getName () );
            if (Config.DEBUG) 
              System.out.println ("path == " + path);
            if ( path == null )   continue;
            
            try
            {
                while ( _begin.compareTo ( NOW ) < 0)
                {
                    //new month
                    if (_begin.getMonth () != _prev.getMonth () )
                    {
                        StringBuffer sb  = site.getMonthData ( _begin.getMonth (), _begin.getYear () );
                        if (sb != null)
                        {
                            String currentDir = path + "/" + (1900+_begin.getYear ()) + "/" +(1+_begin.getMonth ()) +"/";
                            String summaryPage = currentDir + Config.summaryPageName;
                            Output _month ;
                            if ( ! files.containsKey ( summaryPage ) )
                            {
                                File f = new File ( currentDir );
                                if ( ! f.exists () )
                                {
                                    if (!f.mkdirs ())
                                        System.err.println ("ERROR creating " + f.getPath () );
                                }
                                _month = new Output ( summaryPage );
                                if (Config.DEBUG) 
                                  System.out.println ("GLOBAL = " + summaryPage);
                                _month.writeln ( Constants.HEADER_XML );
                                _month.writeln ( "<monthsummary>" );
                                _month.writeln ( "<name>"  + Constants.getMonth(_begin.getMonth ()) + "</name>");
                                files.put ( summaryPage , _month );
                            }
                            else
                            {
                                _month = files.get ( summaryPage );
                            }
                            _month.writeln ( sb.toString () );
                        }
                    }
                    _prev   = _begin;
                    _begin  = new Date ( _prev.getTime ()+24*60*60*1000 );
                }
            }
            catch (Exception e )
            {
                e.printStackTrace ();
            }
        }
        
        Set<String> keys = files.keySet ();
        for ( Iterator<String> ite = keys.iterator (); ite.hasNext (); )
        {
            try
            {
                Output _month = files.get ( ite.next () );
                _month.writeln ( "</monthsummary>" );
                _month.close ();
            }
            catch (Exception e)
            {
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
        if (Config.DEBUG) 
        {
          System.out.println ("found "+ badlines + " bad lines ");
          System.out.println ( time + " seconds to dimp data ");
        }
        History.saveHistory ();
        Config.dumpDnsCache ();
        RegexpData.dumpCounters ();
        System.exit (0);
    }
    
}
