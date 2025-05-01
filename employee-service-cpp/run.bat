@echo off
echo Starting the Employee Service C++ application...
echo.

REM Check if vcpkg is installed and get its path
if exist "%VCPKG_ROOT%\vcpkg.exe" (
    set VCPKG_PATH=%VCPKG_ROOT%
) else (
    echo VCPKG_ROOT environment variable not set or vcpkg not found.
    echo Please install vcpkg from https://github.com/microsoft/vcpkg and set VCPKG_ROOT.
    echo.
    pause
    exit /b 1
)

REM Check if CMake is installed
cmake --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo CMake is not installed or not in your PATH.
    echo Please install CMake from https://cmake.org/download/
    echo.
    pause
    exit /b 1
)

REM Configure and build the project
if not exist "build" mkdir build
cd build

echo Configuring CMake...
cmake .. -DCMAKE_TOOLCHAIN_FILE="%VCPKG_PATH%\scripts\buildsystems\vcpkg.cmake" -DCMAKE_BUILD_TYPE=Release

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
set PORT=8084

echo Running application...
Release\employee_service_cpp.exe

cd ..
pause