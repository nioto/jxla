package org.novadeck.jxla.data;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.novadeck.jxla.Constants;

public class Site {
    // all sites available
    private static Map<String, Site> _sites = new HashMap<String, Site>();
    
    // local variables
    //--------
    private String  _name;
    private List<MonthData> _months = new ArrayList<MonthData>();
    
    private MyDate lastLogDate  = null;
    private MyDate beginLogDate  = null;
    
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
    public MyDate getFirstLogDate() {
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
        MyDate currentLineDate = l.getLogDate();
        if (beginLogDate == null ||  beginLogDate.after( currentLineDate )) {
            beginLogDate = new MyDate( currentLineDate );
        }
        
        if ( (_history != null) && _history.allReadyParse( currentLineDate ) ) {
            return ;
        }
        if ( (lastLogDate == null) || lastLogDate.before( currentLineDate ) ) {
            lastLogDate = new MyDate( currentLineDate );
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
            MonthData tmp = _months.get( i );
            if ( ( month == tmp.getMonth()) && ( year == tmp.getYear() ) ) {
                return tmp;
            }
        }
        return null;
    }
    
    
    //============================================================================
    public StringBuilder getMonthData( int month, int year ) {
        MonthData monthData = getMonth( month, year );
        if (monthData == null) return null;
        StringBuilder sb = new StringBuilder();
        sb.append( "<site>\n" );
        sb.append( "<name>" ).append( _name ).append( "</name>\n") ;
        sb.append( "<hits>" ).append( monthData.getHits() ).append( "</hits>\n");
        sb.append( "<bandwidth>" ).append( monthData.getTraffic() ).append( "</bandwidth>\n");
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
        Iterator<MonthData> ite = _months.iterator();
        MonthData current = null;
        while ( ite.hasNext() ) {
            current = ite.next();
            if ( ite.hasNext() ) {
                _history.addMonthSummary( current );
            } else {
                _history.setLastMonth( current );
            }
        }
        _history.updateLastRecordDate( this.lastLogDate );
        return this._history;
    }
    
    //============================================================================
    // XML creation
    public StringBuilder getData(String statsDirectory) {
        Collections.sort( _months );
        StringBuilder output = new StringBuilder( Constants.HEADER_XML );
        output.append( "<site>\n" );
        output.append( "<name>"+_name + "</name>\n") ;
        MonthData m = _months.get( 0 );
        // summary data from history
        if ( _history != null ) {
            output.append( _history.getSummary() ) ;
        }
        for ( int i =0; i< _months.size(); i++) {
            m = _months.get( i );
            output.append( m.getData( ) );
             //-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*--*-*--*-*-*-*
            m.dumpDataToFile( statsDirectory, beginLogDate ) ;
        }
        output.append( "</site>\n" );
        return output;
    }
    
    
    //============================================================================
    //--------
    public static Site getSite( String host ){
        Site  s = _sites.get( host );
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
        return _sites.remove ( host );
    }
    //--------
    public static String[] getSiteHosts() {
        Set<String> list = _sites.keySet();
        String res [] = new String[list.size()];
        int current = 0;
        for (Iterator<String> ite= list.iterator(); ite.hasNext(); ){
            res[current++] = ite.next().toString();
        }
        Arrays.sort( res );
        return res;
    }
    //--------
    public static void addSite( SiteHistory hist ){
        Site  s = _sites.get( hist.getName() );
        if ( s ==null) {
            s = new Site( hist.getName() );
            _sites.put( hist.getName(), s);
        }
        s.setHistory( hist );
    }
    
    //////////////////////////////////////////////////////////////////////////////
    
}