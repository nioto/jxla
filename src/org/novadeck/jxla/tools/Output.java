/*
 * JXLA is released under an Apache-style license.
 * ==============================================
 *
 * The JXLA License, Version 1.0
 *
 * Copyright (c) 2002 JXLA. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by
 *        The JXLA Project Team (http://jxla.novadeck.org/)
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The name "JXLA" must not be used to endorse or promote
 *    products derived from this software without
 *    prior written permission. For written permission,
 *    please contact nioto@users.sourceforge.net.
 *
 * 5. Products derived from this software may not be called "JXLA",
 *    nor may "JXLA" appear in their name, without prior written
 *    permission of the JXLA Team.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE TM4J PROJECT OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 */


/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/tools/Output.java,v $
 * $Revision: 1.1 $
 * $Date: 2002/01/21 21:39:11 $
 * $Author: nioto $
 */
package org.novadeck.jxla.tools;


import org.novadeck.jxla.Statics;

import java.io.*;



public class Output{


  private BufferedOutputStream  _out;
  private boolean               _isDevNull;

  private final static byte[]   LINE_SEPARATOR = System.getProperty("line.separator").getBytes();

  //============================================================================
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
    _out.write( s.getBytes(Statics.ENCODING) );
    //    _out.write( LINE_SEPARATOR );
  }
  public void writeln( String s ) throws IOException {
    if (_isDevNull)
      return;
    if (Utils.isEmpty(s))
      return;
    _out.write( s.getBytes(Statics.ENCODING) );
    _out.write( LINE_SEPARATOR );
  }

  //============================================================================
  public void close( ) throws IOException {
    if (_isDevNull)
      return;
    _out.close();
  }

}
