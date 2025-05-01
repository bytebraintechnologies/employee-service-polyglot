@echo off
echo Starting the Employee Service application...
echo.
cd /d %~dp0
call mvnw.cmd spring-boot:run
pause
