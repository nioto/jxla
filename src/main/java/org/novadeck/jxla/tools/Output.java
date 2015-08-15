package org.novadeck.jxla.tools;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.novadeck.jxla.Constants;



public class Output{


  private BufferedOutputStream  _out;
  private boolean               _isDevNull;

  private final static byte[]   LINE_SEPARATOR = System.getProperty("line.separator").getBytes();

  //============================================================================
  @SuppressWarnings("unused")
	private Output(){
  }

  public Output( String filename ) throws IOException {
    if ( Utils.isEmpty( filename ) )
      _isDevNull = true;
    else
      _out = new BufferedOutputStream( new FileOutputStream( filename ) );
  }


  //============================================================================
  public void write( String s ) throws IOException {
    if (_isDevNull)
      return;
    if (Utils.isEmpty(s))
      return;
    _out.write( s.getBytes(Constants.ENCODING) );
    //    _out.write( LINE_SEPARATOR );
  }
  public void writeln( String s ) throws IOException {
    if (_isDevNull)
      return;
    if (Utils.isEmpty(s))
      return;
    _out.write( s.getBytes(Constants.ENCODING) );
    _out.write( LINE_SEPARATOR );
  }

  //============================================================================
  public void flush() 
  throws IOException
  {
    if (_isDevNull)
      return;
  _out.flush ();
  }
  //============================================================================
  public void close( ) throws IOException {
    if (_isDevNull)
      return;
    _out.flush ();
    _out.close();
  }

}
