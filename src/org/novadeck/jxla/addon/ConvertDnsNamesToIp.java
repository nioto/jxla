/*
 * ConvertDnsNamesToIp.java
 *
 * Created on 3 février 2004, 02:03
 */

package org.novadeck.jxla.addon;

import java.io.File;
import java.util.Map;
import org.novadeck.jxla.tools.History;
import org.novadeck.jxla.data.*;
import org.novadeck.jxla.tools.*;


public class ConvertDnsNamesToIp {
  
  public static void main (String[] args)
  {
    if ( args == null || args.length!=2 )
    {
      System.out.println ("Warning! usage java org.novadeck.jxla.addon.HistorySiteManagement file.hist dns.cache" );
      System.exit (1);
    }
    File history  = new File ( args[0] );
    if ( !history.exists () )
    {
      System.out.println ("file "+ history.getAbsolutePath () + " doesn't exist" );
      System.exit (1 );
    }
    History.setHistoryFile ( args[0] );
    System.out.println ("listing of sites in " + history.getName () +" :");
    DNSCache cache = DNSCache.load ( args[1] );
    String[]  sites = Site.getSiteHosts ();
    Map namesToIp = cache.getReverseMap ();
    // here you can do what you want about sites , remove , update name , etc ... 
    for ( int i=0; i< sites.length; i++)
    {
      Site site = Site.getSite ( sites[i] );
      String name = site.getName ();
      System.out.println("name=" + name);
      SiteHistory h = site.getHistory ();
      MonthData month = h.getLastMonth ();
      month.convertDnsNamesToIp (namesToIp);
    }
    // store to another file for safety
    History.setHistoryFile ( "newsite.hist" );
    History.saveHistory ();
  }
    
}
