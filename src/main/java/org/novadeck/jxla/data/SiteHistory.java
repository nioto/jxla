package org.novadeck.jxla.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.novadeck.jxla.Constants;


@SuppressWarnings("serial")
public class SiteHistory implements java.io.Serializable {

  String    siteName ;
  List<MonthHistory>      months ;
  MyDate      lastRecord ;
  MyDate      firstRecord ;
  MonthData lastMonth ;


  @SuppressWarnings("unused")
	private SiteHistory() {
  }

  public  SiteHistory( String name_site, MyDate last_record, MyDate first_record ) {
    siteName        = name_site;
    lastRecord      = last_record;
    firstRecord     = first_record;
    this.months     = new ArrayList<MonthHistory>();
    this.lastMonth  = null;
  }

  public void addMonthSummary( MonthData m ) {
    MonthHistory  mh = new MonthHistory( m.getMonth(), m.getYear(), m.getHits() ,
        m.getFiles(), m.getPages(), m.getTraffic() );
    months.add( mh );
  }
  public void setLastMonth( MonthData m ) {
    this.lastMonth = m;
  }
  public MonthData getLastMonth( ) {
    return this.lastMonth ;
  }
  public void updateLastRecordDate( MyDate d ) {
    this.lastRecord = d;
  }
  public MyDate getLastRecordDate( ) {
    return  this.lastRecord;
  }
  public MyDate getFirstRecordDate( ) {
    return  this.firstRecord;
  }
  public String getName( ) {
    return this.siteName;
  }
  public boolean allReadyParse( MyDate d) {
    return d.before( lastRecord ) || d.equals( lastRecord ) ;
  }
  public StringBuilder getSummary() {
  	StringBuilder sb = new StringBuilder();
    if ( months.isEmpty() ) return sb;
    Iterator<MonthHistory> ite = months.iterator();
    while (ite.hasNext()) {
      sb.append( ite.next().getData() );
    }
    return sb;
  }


	private class MonthHistory implements java.io.Serializable {
    int month ;
    int year;
    long totalHits;
    long totalFiles;
    long totalPages;
    long totalTraffic;

    private MonthHistory( int mm, int yr, long hits, long files, long pages, long traffic ) {
      this.month        = mm;
      this.year         = yr;
      this.totalHits    = hits;
      this.totalFiles   = files;
      this.totalPages   = pages;
      this.totalTraffic = traffic;
    }
    public StringBuilder getData() {
    	StringBuilder output = new StringBuilder();
      output.append( "<month>\n" );
      output.append( "<name>" ).append( Constants.getMonth ( month ) ).append( "</name>\n" );
      output.append( "<number>" ).append( (1+ month) ).append( "</number>\n" );
      output.append( "<year>" ).append( year ).append( "</year>" );
      String filename =  "usage_" + year + "" + ( month+1<10 ? "0" : "" ) + (month+1) + ".xml";
      output.append( "<url>" ).append( filename ).append( "</url>\n" );
      output.append( "<hits>" ).append( totalHits ).append( "</hits>\n" );
      output.append( "<files>" ).append( totalFiles ).append( "</files>\n" );
      output.append( "<pages>" ).append( totalPages ).append( "</pages>\n" );
      output.append( "<traffic>" ).append( totalTraffic ).append( "</traffic>\n" );
      output.append( "</month>\n" );
      return output;
    }

  }
}
