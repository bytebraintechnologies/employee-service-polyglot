@echo off
echo Starting the Employee Service Scala application...
echo.
cd /d %~dp0

REM Check if SBT exists
where sbt >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
  echo SBT not found. Please install SBT first.
  echo You can download it from https://www.scala-sbt.org/download.html
  pause
  exit /b 1
)

REM Create logs directory if it doesn't exist
if not exist logs mkdir logs

echo Starting Scala application...
call sbt run
pause
