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
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/config/AbstractSiteConfig.java,v $
 * $Revision: 1.1 $
 * $Date: 2002/01/21 21:39:10 $
 * $Author: nioto $
 */
package org.novadeck.jxla.config;


import org.novadeck.jxla.data.Line;
import org.novadeck.jxla.data.RegexpData;


/**
 * This class defines an interface to get information from
 * your WebSites configuration.
 *
 */

public interface AbstractSiteConfig {

  /**
   * Retrieve the internal name to use for log analyze,
   * many websites use different names for the same ressources
   * like www.novadeck.org and novadeck.org, so we need
   * only one name.
   * @hostname : the hostname in the lod file
   * @return String : the internal name for computing logs
   */
  public String getRealHostName(String hostname);

  /**
   * Convert any user info from log files to a 'readable'
   * form for the output. In log file we can store his login but
   * want to output his fullname.
   */
  public String getRealUserInfo(String user);

  /**
   * Method to check if the log line must be ignore,
   * may be a comment ( beginnnig with # ), or an URL we don't want to
   * output it ( like a url of developping next generation of website, or
   * an admin part of the site ). Can also work with the user info.
   */
  public boolean ignoreLine( RegexpData re ) ;

  /**
   * Retrieve the directory where to store the output files, depanding of
   * the website.
   */
  public String getStatsDirectory(String hostname);

  /**
   * Retrieve the directory where to store a summary of requests to the differents
   * websites in the platform.
   */
  public String getMainDirForGeneralStat() ;
}
