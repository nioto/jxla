package org.novadeck.jxla.data;

import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.novadeck.jxla.Constants;


@SuppressWarnings("serial")
public class SiteHistory implements java.io.Serializable {

  String    siteName ;
  List<MonthHistory>      months ;
  Date      lastRecord ;
  Date      firstRecord ;
  MonthData lastMonth ;


  @SuppressWarnings("unused")
	private SiteHistory() {
  }

  public  SiteHistory( String name_site, Date last_record, Date first_record ) {
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
  public void updateLastRecordDate( Date d ) {
    this.lastRecord = d;
  }
  public Date getLastRecordDate( ) {
    return  this.lastRecord;
  }
  public Date getFirstRecordDate( ) {
    return  this.firstRecord;
  }
  public String getName( ) {
    return this.siteName;
  }
  public boolean allReadyParse( Date d) {
    return d.before( lastRecord ) || d.equals( lastRecord ) ;
  }
  public StringBuffer getSummary() {
    StringBuffer sb = new StringBuffer();
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
    public StringBuffer getData() {
      StringBuffer output = new StringBuffer();
      output.append( "<month>\n" );
      output.append( "<name>" + Constants.getMonth ( month ) + "</name>\n");
      output.append( "<number>" + (1+ month) + "</number>\n");
      output.append( "<year>" + (1900+ year) + "</year>");
      String filename =  "usage_" + ( year + 1900 ) + "" + ( month+1<10 ? "0" : "" ) + (month+1) + ".xml";
      output.append( "<url>" +   filename + "</url>\n");
      output.append( "<hits>"    + totalHits    + "</hits>\n"   );
      output.append( "<files>"   + totalFiles   + "</files>\n"  );
      output.append( "<pages>"   + totalPages   + "</pages>\n"  );
      output.append( "<traffic>" + totalTraffic + "</traffic>\n"  );
      output.append( "</month>\n" );
      return output;
    }

  }
}
