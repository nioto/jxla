/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/data/Site.java,v $
 * $Revision: 1.3 $
 * $Date: 2005/01/06 13:18:54 $
 * $Author: nioto $
 */
package org.novadeck.jxla.data;


import java.text.*;
import java.util.*;

import org.novadeck.jxla.data.*;
import org.novadeck.jxla.tools.Output;
import org.novadeck.jxla.Constants;

public class Site {
    // all sites available
    private static HashMap _sites = new HashMap();
    
    // local variables
    //--------
    private String  _name;
    private List _months = new ArrayList();
    
    private Date lastLogDate  = null;
    private Date beginLogDate  = null;
    
    SiteHistory _history      = null;
    //============================================================================
    //--------
    public Site( String str ) {
        _name      = str;
    }
    
    public String getName() {
        return _name;
    }
    public void setName(String s ) {
        _name= s;
    }
    public Date getFirstLogDate() {
        if ( _history != null)
            return _history.getFirstRecordDate();
        else
            return this.beginLogDate;
    }
    //============================================================================
    private void setHistory( SiteHistory history ) {
        this._history                 = history;
        this.lastLogDate              = history.getLastRecordDate();
        this.beginLogDate             = history.getFirstRecordDate();
        _months.add( history.getLastMonth() );
    }
    
    //============================================================================
    //--------
    public void addHit( Line l ) {
        Date currentLineDate = l.getLogDate();
        if (beginLogDate == null ||  beginLogDate.after( currentLineDate )) {
            beginLogDate = (Date)currentLineDate.clone();
        }
        
        if ( (_history != null) && _history.allReadyParse( currentLineDate ) ) {
            return ;
        }
        if ( (lastLogDate == null) || lastLogDate.before( currentLineDate ) ) {
            lastLogDate = (Date)currentLineDate.clone();;
        }
        int mm = currentLineDate.getMonth() ;
        int yy = currentLineDate.getYear()  ;
        
        MonthData month = getMonth( mm, yy );
        if (month == null) {
            month = new MonthData( mm , yy );
            _months.add( month );
        }
        month.addLine( l );
    }
    
    
    private MonthData getMonth( int month, int year ) {
        for (int i=_months.size()-1; i>=0  ; i--) {
            MonthData tmp = (MonthData)_months.get( i );
            if ( ( month == tmp.getMonth()) && ( year == tmp.getYear() ) ) {
                return tmp;
            }
        }
        return null;
    }
    
    
    //============================================================================
    public StringBuffer getMonthData( int month, int year ) {
        MonthData monthData = getMonth( month, year );
        if (monthData == null) return null;
        StringBuffer sb = new StringBuffer();
        sb.append( "<site>\n" );
        sb.append( "<name>"+_name + "</name>\n") ;
        sb.append( "<hits>" + monthData.getHits()+"</hits>\n");
        sb.append( "<bandwidth>" + monthData.getTraffic()+"</bandwidth>\n");
        sb.append( "</site>\n" );
        return sb;
    }
    //============================================================================
    //--------
    public static void addLine( Line l){
        Site s = getSite( l.getHost() );
        s.addHit( l );
    }
    
    //============================================================================
    public SiteHistory getHistory() {
        if ( _history == null ){
            _history = new SiteHistory( _name, null, beginLogDate );
        }
        Iterator ite = _months.iterator();
        Object current = null;
        while ( ite.hasNext() ) {
            current = ite.next();
            MonthData tmp = (MonthData) current ;
            if ( ite.hasNext() ) {
                _history.addMonthSummary( tmp );
            } else {
                _history.setLastMonth( tmp );
            }
        }
        _history.updateLastRecordDate( this.lastLogDate );
        return this._history;
    }
    
    //============================================================================
    // XML creation
    public StringBuffer getData(String statsDirectory) {
        Collections.sort( _months );
        StringBuffer output = new StringBuffer( Constants.HEADER_XML );
        output.append( "<site>\n" );
        output.append( "<name>"+_name + "</name>\n") ;
        MonthData m = (MonthData)_months.get( 0 );
        int currentMonth  = m.getMonth()  ;
        int currentYear   = m.getYear()   ;
        // summary data from history
        if ( _history != null ) {
            output.append( _history.getSummary() ) ;
        }
        for ( int i =0; i< _months.size(); i++) {
            m = (MonthData)_months.get( i );
            output.append( m.getData( ) );
             //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*--*-*--*-*-*-*
      m.dumpDataToFile( statsDirectory, beginLogDate ) ;
            currentMonth  = m.getMonth()  ;
            currentYear   = m.getYear()   ;
        }
        output.append( "</site>\n" );
        return output;
    }
    
    
    //============================================================================
    //--------
    public static Site getSite( String host ){
        Site  s = (Site)_sites.get( host );
        if ( s ==null) {
            s = new Site( host );
            _sites.put( host, s);
        }
        return s;
    }
    //============================================================================
    /**
     *  Remove a site from the site cache
     */
    public static Site removeSite( String host ){
        return (Site)_sites.remove ( host );
    }
    //--------
    public static String[] getSiteHosts() {
        Set list = _sites.keySet();
        String res [] = new String[list.size()];
        int current = 0;
        for (Iterator ite= list.iterator(); ite.hasNext(); ){
            res[current++] = ite.next().toString();
        }
        Arrays.sort( res );
        return res;
    }
    //--------
    public static void addSite( SiteHistory hist ){
        Site  s = (Site)_sites.get( hist.getName() );
        if ( s ==null) {
            s = new Site( hist.getName() );
            _sites.put( hist.getName(), s);
        }
        s.setHistory( hist );
    }
    
    //////////////////////////////////////////////////////////////////////////////
    
}