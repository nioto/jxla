package org.novadeck.jxla.data;

/**
 * 
 * @author nioto
 *
 */
@SuppressWarnings("serial")
public class StringData extends SimpleData {

  private String text;

  public StringData(final String str) {
    super();
    text = str;
  }

  public StringData(final String str, final long l) {
    super( l );
    text = str;
  }

  public String getData(){
    return "<![CDATA["+text+"]]>";
  }
}