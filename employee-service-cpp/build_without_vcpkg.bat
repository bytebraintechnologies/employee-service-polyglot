@echo off
echo Starting the Employee Service C++ application build (without vcpkg)...
echo.

REM Check if CMake is installed
cmake --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo CMake is not installed or not in your PATH.
    echo Please install CMake from https://cmake.org/download/
    echo.
    pause
    exit /b 1
)

REM First, let's install the required libraries manually
echo This script assumes you have these libraries installed manually:
echo - nlohmann-json
echo - Crow framework
echo.
echo If you don't have them installed, you'll need to do that first.
echo.
echo Press any key to continue with the build...
pause > nul

REM Configure and build the project
if not exist "build_manual" mkdir build_manual
cd build_manual

echo Configuring CMake...
cmake .. -DCMAKE_BUILD_TYPE=Release

echo Building project...
cmake --build . --config Release

if %ERRORLEVEL% NEQ 0 (
    echo Build failed.
    cd ..
    pause
    exit /b 1
)

echo Build successful!
echo.

REM Set environment variables
set PORT=8080

echo Running application...
Release\employee_service_cpp.exe

cd ..
pause
