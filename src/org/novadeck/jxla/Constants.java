/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/Constants.java,v $
 * $Revision: 1.1 $
 * $Date: 2005/01/06 13:18:53 $
 * $Author: nioto $
 */
package org.novadeck.jxla;

import java.text.*;
import java.util.*;
import java.io.*;

import org.novadeck.jxla.tools.*;
import org.novadeck.jxla.config.*;
import org.novadeck.jxla.data.*;


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