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
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/Statics.java,v $
 * $Revision: 1.1 $
 * $Date: 2002/01/21 21:39:10 $
 * $Author: nioto $
 */
package org.novadeck.jxla;

import java.text.*;
import java.util.*;
import java.io.*;

import org.novadeck.jxla.tools.*;
import org.novadeck.jxla.config.*;
import org.novadeck.jxla.data.*;


public class Statics {

  public static final String[] MONTH  = new String[12];
  public static final String[] DAYS   = new String[7];
  static {
    MONTH[0]		= "January";
    MONTH[1]		= "February";
    MONTH[2]		= "March";
    MONTH[3]		= "April";
    MONTH[4]		= "May";
    MONTH[5]		= "June";
    MONTH[6]		= "July";
    MONTH[7]		= "August";
    MONTH[8]		= "September";
    MONTH[9]		= "October";
    MONTH[10]		= "November";
    MONTH[11]		= "December";


    DAYS[0]     = "Sunday";
    DAYS[1]     = "Monday";
    DAYS[2]     = "Tuesday";
    DAYS[3]     = "Wednesday";
    DAYS[4]     = "Thursday";
    DAYS[5]     = "Friday";
    DAYS[6]     = "Saturday";
  }
  public static final String ENCODING     = "ISO-8859-1";

  public static final String HEADER_XML   = "<?xml version=\"1.0\" encoding=\""+ ENCODING +"\"?>";

}