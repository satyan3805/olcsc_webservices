
If not currently installed, JPUB can be obtained from;
http://www.oracle.com/technology/software/tech/java/sqlj_jdbc/index.html
This download will be needed if the runtime12 and translator jars are not already available.
To install into the local Maven repository;
      mvn install:install-file -DgroupId=oracle -DartifactId=runtime12 -Dversion=10.2 -Dpackaging=jar -Dfile=/path/to/runtime12.jar
      mvn install:install-file -DgroupId=oracle -DartifactId=translator -Dversion=10.2 -Dpackaging=jar -Dfile=/path/to/translator.jar

To build the PLSQL Objects as part of a Release, use the Release profile.  Ex.
mvn -PRelease install

Other notes:
java.lang.NullPointerException:
This is being generated when using that "Java" goal near/at the end of the run, during a "post translation" phase.  
According to initial research, it may be caused by a classpath conflict.

Error: invalid option "d" set from command-line: not a directory: C:\projects\OLCSC_HCTRA\PLSQL\target\classes
This is caused by the fact that the build/target directory is not yet created during the plsql run.  This is
not currently an issue, as jpub does not actually compile the Java source files.  This will need to be addressed
if jpub is actually used to compile the source files to class files.

