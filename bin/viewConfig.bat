@echo off

if NOT "%JAVA_HOME%"=="" goto ok

:set_java_home
echo "Notice: JAVA_HOME must be set" 
goto end

:ok
set CLASSPATH=lib/xerces.jar
set CLASSPATH=%CLASSPATH%;lib/jakarta-oro.jar
set CLASSPATH=%CLASSPATH%;lib/jxla.jar

set CONF=conf/conf.xml

if NOT "%1"==""	set CONF=%1


%JAVA_HOME%\bin\java  org.novadeck.jxla.Main %CONF% viewConfig

:end

