@echo off
echo Starting the Employee Service Rust application...
echo.

REM Check if Rust is installed
rustc --version > nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Rust is not installed or not in your PATH.
    echo Please install Rust from https://rustup.rs/
    pause
    exit /b 1
)

REM Set environment variables
set PORT=8083
set RUST_LOG=info

REM Build and run the application
echo Building and running the application...
cd %~dp0
cargo run

pause