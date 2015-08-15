#!/bin/sh

# * * * * * *
# need  to set output dir and hostname
# ------------------------------------
OUTPUTDIR=
JAVA_HOME=
CONF=


#
# don"t change following lines
#
ERROR=0

if [ "$OUTPUTDIR" = "" ] ; then 
  echo "Error: No output dir specified, edit run.sh to set OUTPUTDIR !!"
  ERROR=1
fi

if [ "$CONF" = "" ] ; then 
  echo "Error: No configuration file specified, edit run.sh to set CONF !!"
  ERROR=1
fi

if [ "$JAVA_HOME" = "" ] ; then
  echo "Error: JAVA_HOME must be set"
  exit 1;
fi


if [ "$ERROR" != "0" ] ; then 
  echo "CORRECT ERRORS AND TRY AGAIN !"
  exit 1;
fi




CLASSPATH=lib/xerces.jar
CLASSPATH=$CLASSPATH:lib/jakarta-oro.jar
CLASSPATH=$CLASSPATH:lib/jxla.jar
CLASSPATH=$CLASSPATH:lib/InetAddressLocator.jar

export CLASSPATH

$JAVA_HOME/bin/java -Doutputdir=$OUTPUTDIR org.novadeck.jxla.Main $CONF 
