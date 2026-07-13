set tns=rchq-hctrabosdbd01-phli.etcchostedservices.local:1521/HCTRACD1
set usernamepwd=OL_OWNER/OL_OWNERHCTRACD1

set JDEV_HOME_1021=C:\jdevstudio10135
set JAVA_HOME=%JDEV_HOME_1021%\jdk

set classpath=%JDEV_HOME_1021%\jdbc\lib\ojdbc6.jar;%JDEV_HOME_1021%\sqlj\lib\runtime12.jar;%JDEV_HOME_1021%\sqlj\lib\translator.jar;%JDEV_HOME_1021%\sqlj\lib\runtime12ee.jar;


%JDEV_HOME_1021%\bin\jpub.exe -input=config.text -user=%usernamepwd% -case=upper -methods=true -builtintypes=jdbc  -numbertypes=jdbc -usertypes=oracle  -dir=. -package=com.etcc.csc.plsql  -url=jdbc:oracle:thin:@%tns%
jar cvfM plsql.jar com

pause