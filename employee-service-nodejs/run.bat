@echo off
echo Starting the Employee Service Node.js application...
echo.
cd /d %~dp0
if not exist node_modules (
  echo Installing dependencies...
  call npm install
)
echo Starting server...
call npm start
pause
