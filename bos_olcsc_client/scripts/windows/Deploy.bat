@echo off
call Maven-Location.bat
call Environment.bat
cd ../../../
call mvn install -P%ENVIRONMENT%,deploy -DskipTests
echo.
echo.
pause 3