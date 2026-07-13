@echo off
call Maven-Location.bat
call Environment.bat
cd ../../../
call mvn eclipse:clean eclipse:eclipse -D%ENVIRONMENT% -U
echo.
echo.
pause 3