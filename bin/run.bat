echo off
rem !/bin/sh

rem  * * * * * *
rem  need  to set output dir and hostname
rem  ------------------------------------
set OUTPUTDIR=D:/tmp/logs/
rem #/tmp/report
set HOST=nioto.com


if "%OUTPUTDIR%"==""	goto noOutput
:check_host
if "%HOST%"==""	goto noHostname

goto check_java_home


:noOutput
echo " No output dir specified, edit run.bat to set OUTPUTDIR !!"
goto check_host

:noHostname
echo " No hostname specified, edit run.bat to set HOST !!"
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


set CONF=conf/conf.xml
if NOT "%1"==""	set CONF=%1

%JAVA_HOME%\bin\java -Dhostname=%HOST% -Doutputdir=%OUTPUTDIR% -DDEBUG=true org.novadeck.jxla.Main %CONF% 


:end
pause
