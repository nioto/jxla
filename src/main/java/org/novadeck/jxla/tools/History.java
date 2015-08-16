package org.novadeck.jxla.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.novadeck.jxla.data.Site;
import org.novadeck.jxla.data.SiteHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class History {

	final static Logger logger = LoggerFactory.getLogger( History.class );
	
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
        logger.debug( " available bytes = {}",objIn.available());
        try{
          while (true) {
            site = (SiteHistory)objIn.readObject();
            Site.addSite( site );
            logger.debug( "loading {}", site.getName() );
            logger.debug( "getFirstRecordDate = {}", site.getFirstRecordDate());
            logger.debug( "getLastRecordDate = {}", site.getLastRecordDate());
          }
        } catch ( java.io.EOFException e ){
        	logger.error( "Exception received from loading History file", e );
        }
        objIn.close();
        ostream.close();
      } else {
        logger.info("file doesn't exist, will be created at end of process");
      }
    } catch (Exception e) {
      logger.error( "something wrong happens when reading file {} ", fileHistory , e );
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
    	logger.error( "cannot write to file {} ", fileHistory , e );
    }
  }
}
