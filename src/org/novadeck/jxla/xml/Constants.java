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
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/xml/Constants.java,v $
 * $Revision: 1.1 $
 * $Date: 2002/01/21 21:39:11 $
 * $Author: nioto $
 */
package org.novadeck.jxla.xml;


public class Constants {

  public static final String  ROOT                = "jxla";

  public static final String  LOGFILES_NODE       = "logfiles";
  public static final String  LOGFILES_DIRECTORY  = "directory";
  public static final String  LOGFILENAMES_RE     = "filenameregexp";
  public static final String  LOGLINE             = "format";
  public static final String  LOGLINE_RE          = "regexp";

  public static final String  DNS_NODE            = "dns";
  public static final String  DNS_AVAILABLE       = "available";
  public static final String  DNS_FILECACHE       = "filecache";

  public static final String  LOCALCONFIGCLASS_NODE = "localconfigclass";

  public static final String  SEARCHENGINES_NODE  = "searchengines";
  public static final String  SE_ENGINE           = "engine";
  public static final String  SE_NAME             = "name";
  public static final String  SE_PARAMETER        = "requestparameter";
  public static final String  SE_DOMAIN           = "domain";

  public static final String  PAGESEXTENSIONS_NODE= "pages";
  public static final String  PAGESEXTENSIONS_EXT = "extensions";

  public static final String  MAXVALUES_NODE      = "max-values";
  public static final String  MAXVALUES_REFERERS  = "referers";
  public static final String  MAXVALUES_KEYWORDS  = "keywords";
  public static final String  MAXVALUES_USERAGENTS= "user-agent";
  public static final String  MAXVALUES_REMOTE    = "remote-hosts";
  public static final String  MAXVALUES_URI       = "uri";
  public static final String  MAXVALUES_404       = "notfound";

  public static final String  SUMMARY_PAGE_NAME   = "summary-name";
}
