@echo off

if NOT "%JAVA_HOME%"=="" goto ok

:set_java_home
echo "Notice: JAVA_HOME must be set" 
goto end

:ok
set CLASSPATH=lib/ant.jar;lib/xerces.jar;%JAVA_HOME%\lib\tools.jar

%JAVA_HOME%\bin\java org.apache.tools.ant.Main %1 %2 %3 %4 %5 %6 %7 %8 %9

:end
pause

