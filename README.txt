Readme file for JXLA 1.1
=============


JXLA is a web logs analyzer, which generates reports in XML files.
You need to apply an XSL stylesheet to XML files to output HTML files and publish it.
Currently no XSL stylesheets are available in repository and distribution files.
JXLA was thunk to allow parsing of logs file containing data for various websites,
but it also workes on single site log files.

Installation
===========
You need JDK 1.3 and above installed to run JXLA.
JXLA needs a dom parser ( xerces included on the biggest archive ), 
InetAddressLocator for geo localisation -included-, jakarta-oro for 
line parsing -included- and nioto.Browser for browser parsing -included-.

Create a directory dedicated to jxla ( $JXLA_HOME ).
Untar jxla-{VERSION}.tgz  and move all files from jxla/ to $JXLA_HOME.

Copy $JXLA_HOME/conf/conf.xml to $JXLA_HOME/conf/myconf.xml, and configure
$JXLA_HOME/conf/myconf.xml for your needs.
See http://jxla.nvdcms.org/en/conf-file.xml for a description of this file.
You can test your configuration file by running :
java -cp libs/jxla.jar:libs/xerces.jar:libs/jakarta-oro.jar:libs/InetAddressLocator.jar:libs/org.nioto.browser.jar org.novadeck.jxla.Main conf/myconf.xml viewConfig
on unix, and 
java -cp libs/jxla.jar;libs/xerces.jar;libs/jakarta-oro.jar;libs/InetAddressLocator.jar;libs/org.nioto.browser.jar org.novadeck.jxla.Main conf/myconf.xml viewConfig
on Windows.


Run 
java -cp libs/jxla.jar;libs/xerces.jar;libs/jakarta-oro.jar;libs/InetAddressLocator.jar;libs/org.nioto.browser.jar org.novadeck.jxla.Main conf/myconf.xml 
to parse log and generate reports.





