/*
 * $Source: /tmp/cvs/jxla/jxla/src/org/novadeck/jxla/tools/Constants.java,v $
 * $Revision: 1.2 $
 * $Date: 2005/01/06 13:18:54 $
 * $Author: nioto $
 */
package org.novadeck.jxla.tools;

import org.apache.oro.text.regex.*;
import org.apache.oro.text.perl.*;

public class Constants {

  public static PatternCompiler COMPILER  = new Perl5Compiler();
  public static PatternMatcher  MATCHER   = new Perl5Matcher();

}
