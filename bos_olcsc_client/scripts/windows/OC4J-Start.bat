@echo off
call Maven-Location.bat
cd ../../
call mvn.bat com.etcc.ocj4.plugin:maven-oc4j-plugin:1.1-SNAPSHOT:start -N

