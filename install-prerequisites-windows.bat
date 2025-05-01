@echo off
echo ===============================================================
echo Installing prerequisites for all Employee Service implementations
echo ===============================================================
echo.

:: Check if Scoop is installed
where scoop >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo Scoop is not installed. Installing Scoop...
    powershell -Command "Set-ExecutionPolicy RemoteSigned -Scope CurrentUser; [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; iwr -useb get.scoop.sh | iex"
    
    if %ERRORLEVEL% neq 0 (
        echo Failed to install Scoop. Please install Scoop manually from https://scoop.sh/
        pause
        exit /b 1
    )
    
    echo Scoop installed successfully.
) else (
    echo Scoop is already installed.
)

:: Add necessary buckets
echo Adding necessary Scoop buckets...
call scoop bucket add extras
call scoop bucket add java
call scoop bucket add versions

:: Update Scoop
echo Updating Scoop...
call scoop update

echo.
echo ===============================================================
echo Installing language runtimes and tools
echo ===============================================================
echo.

:: Install Java (for Java and Clojure implementations)
echo Installing Java...
call scoop install openjdk17
echo Java installed.

:: Install NVM and Node.js (for Node.js implementation)
echo Installing NVM (Node Version Manager)...
call scoop install nvm
echo NVM installed.

echo Installing Node.js using NVM...
call nvm install 16.20.0
call nvm use 16.20.0
echo Node.js installed via NVM.

:: Install Python (for Python implementation)
echo Installing Python...
call scoop install python
call pip install --upgrade pip
echo Python installed.

:: Install Go (for Go implementation)
echo Installing Go...
call scoop install go
echo Go installed.

:: Install Ruby (for Ruby implementation)
echo Installing Ruby...
call scoop install ruby
call gem update --system
call gem install bundler
echo Ruby installed.

:: Install .NET SDK (for C# implementation)
echo Installing .NET SDK...
call scoop install dotnet-sdk
echo .NET SDK installed.

:: Install Leiningen (for Clojure implementation)
echo Installing Leiningen for Clojure...
call scoop install leiningen
echo Leiningen installed.

:: Install Rust (for Rust implementation if needed)
echo Installing Rust...
call scoop install rust
echo Rust installed.

:: Install C++ build tools (for C++ implementation if needed)
echo Installing Visual Studio Build Tools for C++...
echo Note: This will open a separate installer that you need to complete manually.
echo In the installer, please select "Desktop development with C++" workload.
echo Press any key to continue...
pause > nul
start https://visualstudio.microsoft.com/visual-cpp-build-tools/
echo When the Visual Studio Build Tools installation is complete, please continue.
pause

echo.
echo ===============================================================
echo Installing development tools
echo ===============================================================
echo.

:: Install Git
echo Installing Git...
call scoop install git
echo Git installed.

:: Install curl for API testing
echo Installing curl...
call scoop install curl
echo curl installed.

:: Install Postman for API testing
echo Installing Postman...
call scoop install postman
echo Postman installed.

echo.
echo ===============================================================
echo Verifying installations
echo ===============================================================
echo.

echo Checking Java version:
java -version
echo.

echo Checking NVM version:
call nvm version
echo.

echo Checking Node.js version:
node --version
echo.

echo Checking Python version:
python --version
echo.

echo Checking Go version:
go version
echo.

echo Checking Ruby version:
ruby --version
echo.

echo Checking .NET SDK version:
dotnet --version
echo.

echo Checking Leiningen version:
lein version
echo.

echo Checking Rust version:
rustc --version
echo.

echo Checking Git version:
git --version
echo.

echo Checking curl version:
curl --version
echo.

echo.
echo ===============================================================
echo All prerequisites have been installed!
echo ===============================================================
echo.
echo You can now run any of the Employee Service implementations.
echo Navigate to the specific implementation directory and run the run.bat file.
echo.
echo For example:
echo cd employee-service-nodejs
echo run.bat
echo.
echo Press any key to exit...
pause > nul
