@echo off
echo Starting the Employee Service Go application...
echo.

echo Getting dependencies...
go mod tidy

echo Building the application...
go build -o employee-service-go.exe

echo Starting the server...
employee-service-go.exe

pause
