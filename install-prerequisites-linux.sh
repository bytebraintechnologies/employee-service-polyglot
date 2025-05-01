#!/bin/bash

echo "==============================================================="
echo "Installing prerequisites for all Employee Service implementations"
echo "==============================================================="
echo

# Check for root/sudo privileges
if [ "$EUID" -ne 0 ]; then
  echo "Please run this script with sudo or as root"
  exit 1
fi

# Update package lists
echo "Updating package lists..."
apt-get update
echo "Package lists updated."

echo
echo "==============================================================="
echo "Installing language runtimes and tools"
echo "==============================================================="
echo

# Install Java (for Java and Clojure implementations)
echo "Installing Java..."
apt-get install -y openjdk-17-jdk
echo "Java installed."

# Install NVM and Node.js (for Node.js implementation)
echo "Installing NVM (Node Version Manager)..."
apt-get install -y curl
export NVM_DIR="$HOME/.nvm"
if [ ! -d "$NVM_DIR" ]; then
  curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.3/install.sh | bash
  echo 'export NVM_DIR="$HOME/.nvm"' >> ~/.bashrc
  echo '[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"' >> ~/.bashrc
  echo '[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"' >> ~/.bashrc
  source ~/.bashrc
fi
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"

echo "NVM installed."

echo "Installing Node.js using NVM..."
nvm install 16.20.0
nvm use 16.20.0
nvm alias default 16.20.0
echo "Node.js installed via NVM."

# Install Python (for Python implementation)
echo "Installing Python..."
apt-get install -y python3 python3-pip python3-venv
ln -sf /usr/bin/python3 /usr/bin/python
pip3 install --upgrade pip
echo "Python installed."

# Install Go (for Go implementation)
echo "Installing Go..."
apt-get install -y golang
echo "Go installed."

# Install Ruby (for Ruby implementation)
echo "Installing Ruby..."
apt-get install -y ruby ruby-dev build-essential
gem update --system
gem install bundler
echo "Ruby installed."

# Install .NET SDK (for C# implementation)
echo "Installing .NET SDK..."
apt-get install -y wget
wget https://packages.microsoft.com/config/ubuntu/22.04/packages-microsoft-prod.deb -O packages-microsoft-prod.deb
dpkg -i packages-microsoft-prod.deb
rm packages-microsoft-prod.deb
apt-get update
apt-get install -y dotnet-sdk-8.0
echo ".NET SDK installed."

# Install Leiningen (for Clojure implementation)
echo "Installing Leiningen for Clojure..."
apt-get install -y leiningen
echo "Leiningen installed."

# Install Rust (for Rust implementation if needed)
echo "Installing Rust..."
apt-get install -y rustc cargo
echo "Rust installed."

# Install C++ build tools (for C++ implementation if needed)
echo "Installing C++ build tools..."
apt-get install -y build-essential cmake
echo "C++ build tools installed."

echo
echo "==============================================================="
echo "Installing development tools"
echo "==============================================================="
echo

# Install Git
echo "Installing Git..."
apt-get install -y git
echo "Git installed."

# Install curl for API testing
echo "Installing curl..."
# curl already installed above
echo "curl installed."

# Install Postman for API testing
echo "Postman is a GUI app that needs to be installed manually."
echo "You can download it from: https://www.postman.com/downloads/"
echo "Or use an alternative like 'httpie':"
apt-get install -y httpie
echo "httpie installed as an alternative to Postman."

echo
echo "==============================================================="
echo "Verifying installations"
echo "==============================================================="
echo

echo "Checking Java version:"
java -version
echo

echo "Checking NVM version:"
nvm --version
echo

echo "Checking Node.js version:"
node --version
echo

echo "Checking Python version:"
python --version
echo

echo "Checking Go version:"
go version
echo

echo "Checking Ruby version:"
ruby --version
echo

echo "Checking .NET SDK version:"
dotnet --version
echo

echo "Checking Leiningen version:"
lein version
echo

echo "Checking Rust version:"
rustc --version
echo

echo "Checking Git version:"
git --version
echo

echo "Checking curl version:"
curl --version
echo

echo
echo "==============================================================="
echo "All prerequisites have been installed!"
echo "==============================================================="
echo
echo "You can now run any of the Employee Service implementations."
echo "Navigate to the specific implementation directory and run the run script."
echo
echo "For example:"
echo "cd employee-service-nodejs"
echo "chmod +x run.sh"
echo "./run.sh"
echo
