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
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/tools/History.java,v $
 * $Revision: 1.1 $
 * $Date: 2002/02/10 15:05:06 $
 * $Author: nioto $
 */

package org.novadeck.jxla.tools;

import org.novadeck.jxla.data.Site;
import org.novadeck.jxla.data.SiteHistory;

import java.io.*;

public class History {
  
  private static String fileHistory;
  
  public static void setHistoryFile( String f ) {
    fileHistory = f ;
    loadHistory();
  }
  public static String getHistoryFile( ) {
    return fileHistory ;
  }
  
  public static void loadHistory  ( ){
    if ( fileHistory == null) return ;
    try{
      File f = new File( fileHistory );
      if ( f.exists() ){
        FileInputStream ostream   = new FileInputStream( f );
        ObjectInputStream objIn   = new ObjectInputStream(ostream);
        SiteHistory site;
        System.out.println("available == "+objIn.available());
        try{
          while (true) {
            site = (SiteHistory)objIn.readObject();
            Site.addSite( site );
            System.out.println("loading " + site.getName() );
          }
        } catch ( java.io.EOFException e ){
        }
        objIn.close();
        ostream.close();
      } else {
        System.out.println("file doesnt exist");
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("something wrong happens when reading file " + fileHistory + " ex=" +e.getMessage()  );
    }
  }
  public static void saveHistory  ( ){
    String[]  list = Site.getSiteHosts();
    try{
      File f = new File( fileHistory );
      FileOutputStream ostream   = new FileOutputStream( fileHistory );
      ObjectOutputStream objIn   = new ObjectOutputStream(ostream);
      for ( int k=0; k < list.length; k++ ) {
        Site s = Site.getSite( list[k] );
        objIn.writeObject( s.getHistory() );
      }
      objIn.close();
      ostream.close();
    } catch (Exception e) {
      System.out.println("cannot write to file " + fileHistory + " ex=" +e.getMessage()  );
    }
  }
}