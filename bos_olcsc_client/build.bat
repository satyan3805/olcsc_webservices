rem Only uncomment the next line if you do not have
rem maven installed and setup in your PATH
set PATH=\\etccfiler02\Shared\test-maven-2.0.10\bin;%PATH%
set MAVEN_OPTS=-Xmx512m -Xms128m -XX:PermSize=128m -XX:MaxPermSize=256m
mvn clean install -Pprod -Dmaven.test.skip=true
pause