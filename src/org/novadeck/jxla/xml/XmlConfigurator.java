/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/xml/XmlConfigurator.java,v $
 * $Revision: 1.4 $
 * $Date: 2005/04/05 22:03:00 $
 * $Author: nioto $
 */
package org.novadeck.jxla.xml;


import java.util.StringTokenizer;
import org.w3c.dom.*;
import javax.xml.parsers.*;

import org.novadeck.jxla.config.Config;
import org.novadeck.jxla.tools.History;


public class XmlConfigurator {


  public static void configure( String filepath ) throws Exception {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(filepath);

    Node n = (Node)doc.getDocumentElement();
    if ( Constants.ROOT.equals( n.getNodeName() ) )
      configure( n );
    else
      throw new IllegalArgumentException(" check your config file, the root node is not jxla");
  }


  private static void configure( Node node ){
    NodeList list = node.getChildNodes();
    int len = list.getLength();
    for ( int i = 0; i < len ; i++ ){
      Node n = list.item( i );
      String name = n.getNodeName();
      if ( Constants.DNS_NODE.equals( name )) {
        configureDNS( n );
      } else
        if ( Constants.LOGFILES_NODE.equals(name)){
          configureLogFiles(n);
        } else
          if ( Constants.PAGESEXTENSIONS_NODE.equals(name)){
            configurePages(n);
          } else
            if ( Constants.MAXVALUES_NODE.equals(name)){
              configureMaxValues(n);
            } else
              if ( Constants.LOCALCONFIGCLASS_NODE.equals(name)){
                configureConfigClass(n);
              } else
                if ( Constants.SEARCHENGINES_NODE.equals(name)){
                  configureSearchEngines(n);
                } else
                  if ( Constants.SUMMARY_PAGE_NAME.equals(name)){
                    configureSummaryPageName(n);
                  } else
                    if ( Constants.HISTORY_FILEPATH.equals(name)){
                      configureHistoryFileName(n);
                    }
    }
  }


  private static void configureDNS( Node node ) {
    NodeList nlist = node.getChildNodes();
    int len = nlist.getLength();
    boolean dnsenable = false;
    String fileDNS    = null;
    for (int i=0;i<len;i++) {
      Node n = nlist.item( i );
      String name = n.getNodeName();
      if ( Constants.DNS_AVAILABLE.equals( name )) {
        dnsenable = "true".equalsIgnoreCase( n.getFirstChild().getNodeValue() );
      } else if ( Constants.DNS_FILECACHE.equals( name )) {
        fileDNS = n.getFirstChild().getNodeValue() ;
      } else if ( n.getNodeType() == Node.ELEMENT_NODE ) {
        System.err.println("wrong node for DNS parameters :" + name );
      }
    }
    Config.setDnsInfo( dnsenable, fileDNS );
  }

  private static void configureLogFiles( Node node ) {
    NodeList nlist = node.getChildNodes();
    int len = nlist.getLength();
    String directory    = null;
    String regexp       = null;
    for (int i=0;i<len;i++) {
      Node n = nlist.item( i );
      String name = n.getNodeName();
      if ( Constants.LOGFILES_DIRECTORY.equals( name )) {
        directory = n.getFirstChild().getNodeValue();
      } else if ( Constants.LOGFILENAMES_RE.equals( name )) {
        regexp  = n.getFirstChild().getNodeValue() ;
      } else if ( Constants.LOGLINE.equals( name )) {
        NodeList list = n.getChildNodes();
        int len0 = list.getLength();
        for (int i0=0;i0<len0;i0++) {
          Node n0 = list.item( i0 );
          String name0 = n0.getNodeName();
          if ( Constants.LOGLINE_RE.equals( name0 )) {
            Config.addLogLineFormat( n0.getFirstChild().getNodeValue() );
          } else if ( n0.getNodeType() == Node.ELEMENT_NODE ) {
            System.err.println("wrong node for Log line format parameters :" + name0 );
          }
        }
      } else if ( n.getNodeType() == Node.ELEMENT_NODE ) {
        System.err.println("wrong node for Log files parameters :" + name );
      }
    }
    Config.setLogFiles( directory, regexp);
  }

  private static void configurePages( Node node ) {
    NodeList nlist = node.getChildNodes();
    int len = nlist.getLength();
    for (int i=0;i<len;i++) {
      Node n = nlist.item( i );
      String name = n.getNodeName();
      if ( Constants.DEFAUL_INDEX.equals( name )) {
        String index = n.getFirstChild().getNodeValue();
        Config.setDefaultIndex ( index );
      } else if ( Constants.PAGESEXTENSIONS_EXT.equals( name )) {
        String ext = n.getFirstChild().getNodeValue();
        StringTokenizer st = new StringTokenizer( ext, ",");
        while (st.hasMoreTokens() ){
          Config.addPageExtension( st.nextToken().trim() );
        }
      } else if ( n.getNodeType() == Node.ELEMENT_NODE ) {
        System.err.println("wrong node for extensions parameters :" + name );
      }
    }
  }

  private static void configureMaxValues( Node node ) {
    NodeList nlist = node.getChildNodes();
    int len = nlist.getLength();
    for (int i=0;i<len;i++) {
      Node n = nlist.item( i );
      String name = n.getNodeName();
      if ( Constants.MAXVALUES_404.equals( name )) {
        String val = n.getFirstChild().getNodeValue();
        Config.setMaxNotFound( Integer.parseInt( val ));
      } else if ( Constants.MAXVALUES_KEYWORDS.equals( name )) {
        String val = n.getFirstChild().getNodeValue();
        Config.setMaxKeywords( Integer.parseInt( val ));
      } else if ( Constants.MAXVALUES_REFERERS.equals( name )) {
        String val = n.getFirstChild().getNodeValue();
        Config.setMaxRefers( Integer.parseInt( val ));
      } else if ( Constants.MAXVALUES_REMOTE.equals( name )) {
        String val = n.getFirstChild().getNodeValue();
        Config.setMaxRemotehosts( Integer.parseInt( val ));
      } else if ( Constants.MAXVALUES_URI.equals( name )) {
        String val = n.getFirstChild().getNodeValue();
        Config.setMaxUris( Integer.parseInt( val ));
      } else if ( Constants.MAXVALUES_COUNTRIES.equals( name )) {
        String val = n.getFirstChild().getNodeValue();
        Config.setMaxCountries ( Integer.parseInt( val ) );
      } else if ( Constants.MAXVALUES_BROWSERS.equals( name )) {
        String val = n.getFirstChild().getNodeValue();
        Config.setMaxBrowsers ( Integer.parseInt( val ) );
      } else if ( Constants.MAXVALUES_OSES.equals( name )) {
        String val = n.getFirstChild().getNodeValue();
        Config.setMaxOS ( Integer.parseInt( val ) );
      } else if ( n.getNodeType() == Node.ELEMENT_NODE ) {
        System.err.println("wrong node for max values parameters :" + name );
      }
    }
  }


  private static void configureConfigClass( Node node ) {
    Config.setConfigClass( node.getFirstChild().getNodeValue() );
  }


  private static void   configureSearchEngines( Node node ) {
    NodeList nlist = node.getChildNodes();
    String nameSE   = null;
    String paramSE  = null;
    String domainSE = null;
    int len = nlist.getLength();
    for (int i=0; i<len; i++) {
      Node n = nlist.item( i );
      String name = n.getNodeName();
      if ( Constants.SE_ENGINE.equals( name )) {
        NodeList list = n.getChildNodes();
        int len0 = list.getLength();
        for (int i0=0;i0<len0;i0++) {
          Node n0 = list.item( i0 );
          String name0 = n0.getNodeName();
          if ( Constants.SE_DOMAIN.equals( name0 )) {
            domainSE = n0.getFirstChild().getNodeValue();
          } else if ( Constants.SE_NAME.equals( name0 )) {
            nameSE   = n0.getFirstChild().getNodeValue();
          } else if ( Constants.SE_PARAMETER.equals( name0 )) {
            paramSE  = n0.getFirstChild().getNodeValue();
          } else if ( n0.getNodeType() == Node.ELEMENT_NODE ) {
            System.err.println("wrong node for search engines parameters :" + name0 );
          }
        }
        if ( domainSE !=null && nameSE !=null && paramSE !=null)
          Config.addSearchEngine(nameSE, domainSE, paramSE );
      } else if ( n.getNodeType() == Node.ELEMENT_NODE ) {
        System.err.println("wrong node for extensions parameters :" + name );
      }
    }
  }

  private static void configureSummaryPageName( Node node ) {
    Config.setSummaryPageName( node.getFirstChild().getNodeValue() );
  }

  private static void configureHistoryFileName( Node node ) {
    History.setHistoryFile(node.getFirstChild().getNodeValue() );
  }


  public static void main( String args[] ){
    try{
      configure( args[0] );
    }catch ( Throwable t ){
      t.printStackTrace();
      System.exit(1);
    }
  }
}
