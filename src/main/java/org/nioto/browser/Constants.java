package org.nioto.browser;


public class Constants {
  public static final String RE_WINDOWS_STR   = "([^dar]win[dows]*)[\\s]?([0-9a-z]*)[\\w\\s]?([a-z0-9.]*)";
  public static final String RE_MAC_STR       = "(68[k0]{1,3})|(ppc mac os x)|([p\\S]{1,5}pc)|(darwin)";
  public static final String RE_OS2_STR       = "os\\/2|ibm-webexplorer";
  public static final String RE_SUNOS_STR     = "(sun|i86)[os\\s]*([0-9]*)";
  public static final String RE_IRIX_STR      = "(irix)[\\s]*([0-9]*)";
  public static final String RE_HPUX_STR      = "(hp-ux)[\\s]*([0-9]*)";
  public static final String RE_AIX_STR       = "aix([0-9]*)";
  public static final String RE_DEC_STR       = "dec|osfl|alphaserver|ultrix|alphastation";
  public static final String RE_VMS_STR       = "vax|openvms";
  public static final String RE_SCO_STR       = "sco|unix_sv";
  public static final String RE_LINUX_STR     = "x11|inux";
  public static final String RE_BSD_STR       = "(free)?(bsd)";
  public static final String RE_AMIGA_STR     = "amiga[os]?";
  public static final String RE_AMIGA_EXTRA_STR   = "(AmigaOS [\\.1-9]?)";
  
  public static final String RE_GECKO_1_STR  = "gecko/([0-9]+)";
  public static final String RE_GECKO_2_STR  = "rv[: ]?([0-9a-z.+]+)";
  public static final String RE_GECKO_3_STR  = "(m[0-9]+)";
  public static final String RE_GECKO_4_STR  = "([0-9]+)([\\.0-9]+)([a-z0-9+]?)";
  
  public static final String RE_MINVERSION   = "([\\.0-9]+)?([\\.a-z0-9]+)?";
  //"[/\\sa-z(]*([0-9]+)([\\.0-9a-z]+)?";
}