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
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/data/SiteHistory.java,v $
 * $Revision: 1.1 $
 * $Date: 2002/02/10 15:05:06 $
 * $Author: nioto $
 */
package org.novadeck.jxla.data;

import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.novadeck.jxla.Statics;


public class SiteHistory implements java.io.Serializable {
  
  String    siteName ;
  List      months ;
  Date      lastRecord ;
  Date      firstRecord ;
  MonthData lastMonth ;
  
  
  private SiteHistory() {
  }
  
  public  SiteHistory( String name_site, Date last_record, Date first_record ) {
    siteName        = name_site;
    lastRecord      = last_record;
    firstRecord     = first_record;
    this.months     = new ArrayList();
    this.lastMonth  = null;
  }
  
  public void addMonthSummary( MonthData m ) {
    MonthHistory  mh = new MonthHistory( m.getMonth(), m.getYear(), m.getHits()
    , m.getFiles(), m.getPages(), m.getTraffic() );
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
    Iterator ite = months.iterator();
    while (ite.hasNext()) {
      sb.append( ((MonthHistory)ite.next()).getData() );
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
      output.append( "<name>" + Statics.MONTH[ month ] + "</name>\n");
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
