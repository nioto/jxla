#!/bin/sh

if [ "$JAVA_HOME" = "" ] ; then
  echo "Error: JAVA_HOME must be set"
  exit 1;
fi

CLASSPATH=lib/ant.jar:lib/xerces.jar:$JAVA_HOME/lib/tools.jar

$JAVA_HOME/bin/java org.apache.tools.ant.Main $1 


