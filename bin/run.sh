#!/bin/sh

# * * * * * *
# need  to set output dir and hostname
# ------------------------------------
OUTPUTDIR=
HOSTNAME=
JAVA_HOME=

ERROR=0

if [ "$OUTPUTDIR" = "" ] ; then 
  echo "Error: No output dir specified, edit run.sh to set OUTPUTDIR !!"
  ERROR=1
fi

if [ "$HOSTNAME" = "" ] ; then 
  echo  "Error: No hostname specified, edit run.sh to set HOSTNAME !!"
  ERROR=1
fi

if [ "$JAVA_HOME" = "" ] ; then
  echo "Error: JAVA_HOME must be set"
  exit 1;
fi


if [ "$ERROR" != "0" ] ; then 
  exit 1;
fi




CLASSPATH=lib/xerces.jar
CLASSPATH=$CLASSPATH:lib/jakarta-oro.jar
CLASSPATH=$CLASSPATH:lib/jxla.jar
CLASSPATH=$CLASSPATH:lib/org.nioto.browser.jar
CLASSPATH=$CLASSPATH:lib/InetAddressLocator.jar


CONF=conf/conf.xml
if [ "$1" != "" ] ; then
  CONF=$1
fi

$JAVA_HOME/bin/java -Dhostname=$HOSTNAME -Doutputdir=$OUTPUTDIR  org.novadeck.jxla.Main $CONF 
