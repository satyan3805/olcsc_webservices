@echo off
call Maven-Location.bat
call Environment.bat
cd ../../../
call mvn clean install -P%ENVIRONMENT% -DskipTests -U
echo.
echo.
pause 3