package org.novadeck.jxla.tools;

import org.novadeck.jxla.data.Site;
import org.novadeck.jxla.data.SiteHistory;
import org.novadeck.jxla.config.Config;

import java.io.*;

public class History {

  private static String fileHistory;

  public static void setHistoryFile( String f ) {
    fileHistory = f ;
    loadHistory();
  }
  public static String getHistoryFile( ) {
    return fileHistory ;
  }

  public static void loadHistory  ( ){
    if ( fileHistory == null) return ;
    try{
      File f = new File( fileHistory );
      
      if ( f.exists() ){
        FileInputStream ostream   = new FileInputStream( f );
        ObjectInputStream objIn   = new ObjectInputStream(ostream);
        SiteHistory site;
        if (Config.DEBUG) 
          System.out.println("available == "+objIn.available());
        try{
          while (true) {
            site = (SiteHistory)objIn.readObject();
            Site.addSite( site );
            if (Config.DEBUG) 
            {
              System.out.println("loading " + site.getName() );
              System.out.println("getFirstRecordDate" + site.getFirstRecordDate());
              System.out.println("getLastRecordDate" + site.getLastRecordDate());
            }
          }
        } catch ( java.io.EOFException e ){
        }
        objIn.close();
        ostream.close();
      } else {
        System.out.println("file doesn't exist, will be created at end of process");
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("something wrong happens when reading file " + fileHistory + " ex=" +e.getMessage()  );
    }
  }
  public static void saveHistory  ( ){
    String[]  list = Site.getSiteHosts();
    try{
      File f = new File( fileHistory );
      if (f.exists()) {
        File backup = new File( fileHistory +"."  + (System.currentTimeMillis() % (24*3600*1000) ));
        f.renameTo( backup );
      }
      f = new File( fileHistory );
        
      ObjectOutputStream objIn   = new ObjectOutputStream( new FileOutputStream( fileHistory ) );
      for ( int k=0; k < list.length; k++ ) {
        Site s = Site.getSite( list[k] );
        objIn.writeObject( s.getHistory() );
      }
      objIn.close();
    } catch (Exception e) {
        e.printStackTrace();
      System.err.println("cannot write to file " + fileHistory + " ex=" +e.getMessage()  );
    }
  }
}
