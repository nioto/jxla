/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/data/StringData.java,v $
 * $Revision: 1.2 $
 * $Date: 2005/01/06 13:18:54 $
 * $Author: nioto $
 */
package org.novadeck.jxla.data;


public class StringData extends SimpleData {

  String _uri;

  public StringData(String uri) {
    super();
    _uri = uri;
  }

  public StringData(String uri, long l) {
    super( l );
    _uri = uri;
  }

  public String getData(){
    return "<![CDATA["+_uri+"]]>";
  }

}