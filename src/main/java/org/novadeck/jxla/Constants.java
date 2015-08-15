package org.novadeck.jxla;


public class Constants {

  private static final String[] MONTH  = { "January", "February", "March", 
    "April", "May", "June", "July", "August", "September", "October",
    "November", "December" };
  private static final String[] DAYS = { "Sunday", "Monday", "Tuesday", 
    "Wednesday", "Thursday", "Friday", "Saturday" };

  public static final String ENCODING     = "ISO-8859-1";

  public static final String HEADER_XML   = "<?xml version=\"1.0\" encoding=\""+ ENCODING +"\"?>";

  public static final String getMonth ( int i ){
    return MONTH[i];
  }
  public static final String getDay( int i ){
    return DAYS[i];
  }
}