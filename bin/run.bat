echo off
rem !/bin/sh

rem  * * * * * *
rem  need  to set output dir and hostname
rem  ------------------------------------
set OUTPUTDIR=
set CONF=


if "%OUTPUTDIR%"==""	goto noOutput
:check_host
if "%CONF%"==""	goto noConf

goto check_java_home


:noOutput
echo " No output dir specified, edit run.bat to set OUTPUTDIR !!"
goto check_host

:noConf
echo " No configuration file specified, edit run.bat to set CONF !!"
goto end


:check_java_home
if NOT "%JAVA_HOME%"=="" goto ok
echo "Notice: JAVA_HOME must be set" 
goto end






:ok
set CLASSPATH=lib/xerces.jar
set CLASSPATH=%CLASSPATH%;lib/jakarta-oro.jar
set CLASSPATH=%CLASSPATH%;lib/jxla.jar
set CLASSPATH=%CLASSPATH%;lib/org.nioto.browser.jar
set CLASSPATH=%CLASSPATH%;lib/InetAddressLocator.jar


%JAVA_HOME%\bin\java -Doutputdir=%OUTPUTDIR% org.novadeck.jxla.Main %CONF% 


:end
pause
