echo off
rem !/bin/sh

rem  * * * * * *
rem  need  to set output dir and hostname
rem  ------------------------------------
set OUTPUTDIR=
set JAVA_HOME=
set CONF=

rem #
rem # don"t change following lines
rem #
set ERROR= 
if "%OUTPUTDIR%" == "" (
 set ERROR=1
 echo no output dir, set OUTPUTDIR
)
if "%CONF%" == "" (
  set ERROR=1
 echo no configuration file, set CONF
)
if "%JAVA_HOME%"=="" (
  set ERROR=1
  echo no java home found, set JAVA_HOME
)
  
if "%ERROR%"=="" goto ok
echo CORRECT ERRORS AND TRY AGAIN ;-)
goto end


:ok
set CLASSPATH=lib/xerces.jar
set CLASSPATH=%CLASSPATH%;lib/jakarta-oro.jar
set CLASSPATH=%CLASSPATH%;lib/jxla.jar
set CLASSPATH=%CLASSPATH%;lib/org.nioto.browser.jar
set CLASSPATH=%CLASSPATH%;lib/InetAddressLocator.jar


%JAVA_HOME%\bin\java -Doutputdir=%OUTPUTDIR% org.novadeck.jxla.Main %CONF% 

:end