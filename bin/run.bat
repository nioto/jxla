@echo off

if NOT "%JAVA_HOME%"=="" goto ok

:set_java_home
echo "Notice: JAVA_HOME must be set" 
goto end

:ok
set CLASSPATH=../lib/xerces.jar
set CLASSPATH=%CLASSPATH%;../lib/jakarta-oro.jar
set CLASSPATH=%CLASSPATH%;../lib/jxla.jar

%JAVA_HOME%\bin\java  org.novadeck.jxla.Main ../conf/conf.xml

:end
pause
