package org.novadeck.jxla.addon;

import java.io.File;
import org.novadeck.jxla.tools.History;
import org.novadeck.jxla.data.*;

/**
 *   This class illustrates how to modify the site cache history, to remove or modify
 *  the site information stores in the history-site file*
 *
 */

public class HistorySiteManagement
{
  public static void main (String[] args)
  {
    if ( args == null || args.length!=1 )
    {
      System.out.println ("Warning! usage java org.novadeck.jxla.addon.HistorySiteManagement file.hist" );
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
    String[]  sites = Site.getSiteHosts ();
    // here you can do what you want about sites , remove , update name , etc ... 
    for ( int i=0; i< sites.length; i++)
    {
      Site site = Site.getSite ( sites[i] );
      String name = site.getName ();
      System.out.println("name=" + name);
    }
    // store to another file for safety
    History.setHistoryFile ( "newsite.hist" );
    History.saveHistory ();
  }
  
}
