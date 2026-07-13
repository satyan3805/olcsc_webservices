@echo off
call Maven-Location.bat
call Environment.bat
cd ../../../
call mvn clean -P%ENVIRONMENT% -DskipTests
echo.
echo.
pause 3