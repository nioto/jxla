#!/bin/sh

# * * * * * *
# need  to set output dir and hostname
# ------------------------------------
OUTPUTDIR=
JAVA_HOME=
CONF=
HOSTNAME=

# 
# don"t change following lines
#

ERROR=0

if [ "$OUTPUTDIR" = "" ] ; then 
  echo "Error: No output dir specified, edit $0 to set OUTPUTDIR !!"
  ERROR=1
fi

if [ "$CONF" = "" ] ; then 
  echo "Error: No configuration file specified, edit $0 to set CONF !!"
  ERROR=1
fi

if [ "$JAVA_HOME" = "" ] ; then
  echo "Error: JAVA_HOME must be set, edit $0 to set it !!""
  exit 1;
fi

if [ "$HOSTNAME" = "" ] ; then
  echo "Error: no hostname found must be set, edit $0 to set HOSTNAME !!""
  exit 1;
fi


if [ "$ERROR" != "0" ] ; then 
  echo "CORRECT ERRORS AND TRY AGAIN !"
  exit 1;
fi




CLASSPATH=lib/xerces.jar
CLASSPATH=$CLASSPATH:lib/jakarta-oro.jar
CLASSPATH=$CLASSPATH:lib/jxla.jar
CLASSPATH=$CLASSPATH:lib/org.nioto.browser.jar
CLASSPATH=$CLASSPATH:lib/InetAddressLocator.jar

export CLASSPATH

$JAVA_HOME/bin/java -Dhostname=$HOSTNAME -Doutputdir=$OUTPUTDIR org.novadeck.jxla.Main $CONF 
