package org.novadeck.jxla.data;


@SuppressWarnings("serial")
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