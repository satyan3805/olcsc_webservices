rem Only uncomment the next line if you do not have
rem maven installed and setup in your PATH
NET USE V: \\etccfiler04\Shared\software\platform
set PATH=V:\java\apache-maven-3.0.5\bin;%PATH%
set MAVEN_OPTS=-Xmx512m -Xms128m -XX:PermSize=128m -XX:MaxPermSize=256m
set JAVA_HOME=V:\java\jdk1.8.0_212
set PATH=%JAVA_HOME%\bin;%PATH%