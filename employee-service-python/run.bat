@echo off
echo Starting the Employee Service Python application...
echo.

REM Create virtual environment if it doesn't exist
if not exist venv (
    echo Creating virtual environment...
    python -m venv venv
)

REM Activate virtual environment
call venv\Scripts\activate.bat

REM Clean up potentially problematic packages first
echo Cleaning up existing packages...
pip uninstall -y pydantic pydantic-core typing-extensions annotated-types

REM Install dependencies without optional Rust-requiring packages
echo Installing dependencies...
pip install Flask==2.3.3 flask-cors==4.0.0 python-dotenv==1.0.0 uuid==1.30

REM Install loguru separately
pip install loguru==0.7.2

REM Install pydantic dependencies with correct versions
echo Installing pydantic and dependencies...
pip install annotated-types==0.6.0
pip install typing-extensions==4.8.0
pip install pydantic-core==2.10.1
pip install pydantic==2.4.2

REM Set the port to 8082 for Python
set PORT=8082

echo Starting server...
python run.py

pause