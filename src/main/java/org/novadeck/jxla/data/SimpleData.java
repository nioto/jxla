package org.novadeck.jxla.data;


@SuppressWarnings("serial")
public class SimpleData extends SerializableSimpleData implements Comparable<SimpleData> {

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

  public int compareTo(SimpleData  obj) {
    return ( (int) ( obj.getCount() - getCount() ) );
  }

}
