/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/data/SimpleData.java,v $
 * $Revision: 1.3 $
 * $Date: 2005/01/06 13:18:54 $
 * $Author: nioto $
 */
package org.novadeck.jxla.data;

import java.lang.*;

public class SimpleData extends SerializableSimpleData implements Comparable {

  private long _counter;

  public SimpleData(){
    _counter = 0;
  }
  public SimpleData( long l ){
    _counter = l;
  }

  public long getCount() {
    return _counter;
  }

  public void inc(){
    _counter++;
  }
  public void add(long l){
    if (l>0) _counter = _counter + l;
  }

  public int compareTo(java.lang.Object obj) {
    SimpleData d= (SimpleData)obj;
    return ( (int) ( d.getCount() - getCount() ) );
  }

}
