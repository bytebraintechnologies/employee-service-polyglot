#!/bin/bash

echo "==============================================================="
echo "Installing prerequisites for all Employee Service implementations"
echo "==============================================================="
echo

# Check if Homebrew is installed
if ! command -v brew &> /dev/null; then
    echo "Homebrew is not installed. Installing Homebrew..."
    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
    
    if [ $? -ne 0 ]; then
        echo "Failed to install Homebrew. Please install Homebrew manually from https://brew.sh/"
        exit 1
    fi
    
    echo "Homebrew installed successfully."
else
    echo "Homebrew is already installed."
fi

# Update Homebrew
echo "Updating Homebrew..."
brew update
echo "Homebrew updated."

echo
echo "==============================================================="
echo "Installing language runtimes and tools"
echo "==============================================================="
echo

# Install Java (for Java and Clojure implementations)
echo "Installing Java..."
brew install openjdk@17
echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.bash_profile
echo "Java installed."

# Install NVM and Node.js (for Node.js implementation)
echo "Installing NVM (Node Version Manager)..."
brew install nvm
mkdir -p ~/.nvm
echo 'export NVM_DIR="$HOME/.nvm"' >> ~/.zshrc
echo '[ -s "/usr/local/opt/nvm/nvm.sh" ] && . "/usr/local/opt/nvm/nvm.sh"' >> ~/.zshrc
echo '[ -s "/usr/local/opt/nvm/etc/bash_completion.d/nvm" ] && . "/usr/local/opt/nvm/etc/bash_completion.d/nvm"' >> ~/.zshrc

echo 'export NVM_DIR="$HOME/.nvm"' >> ~/.bash_profile
echo '[ -s "/usr/local/opt/nvm/nvm.sh" ] && . "/usr/local/opt/nvm/nvm.sh"' >> ~/.bash_profile
echo '[ -s "/usr/local/opt/nvm/etc/bash_completion.d/nvm" ] && . "/usr/local/opt/nvm/etc/bash_completion.d/nvm"' >> ~/.bash_profile

export NVM_DIR="$HOME/.nvm"
[ -s "/usr/local/opt/nvm/nvm.sh" ] && . "/usr/local/opt/nvm/nvm.sh"
echo "NVM installed."

echo "Installing Node.js using NVM..."
nvm install 16.20.0
nvm use 16.20.0
nvm alias default 16.20.0
echo "Node.js installed via NVM."

# Install Python (for Python implementation)
echo "Installing Python..."
brew install python
pip3 install --upgrade pip
echo "Python installed."

# Install Go (for Go implementation)
echo "Installing Go..."
brew install go
echo "Go installed."

# Install Ruby (for Ruby implementation)
echo "Installing Ruby..."
brew install ruby
echo 'export PATH="/usr/local/opt/ruby/bin:$PATH"' >> ~/.zshrc
echo 'export PATH="/usr/local/opt/ruby/bin:$PATH"' >> ~/.bash_profile
source ~/.zshrc 2>/dev/null || source ~/.bash_profile 2>/dev/null
gem update --system
gem install bundler
echo "Ruby installed."

# Install .NET SDK (for C# implementation)
echo "Installing .NET SDK..."
brew install --cask dotnet-sdk
echo ".NET SDK installed."

# Install Leiningen (for Clojure implementation)
echo "Installing Leiningen for Clojure..."
brew install leiningen
echo "Leiningen installed."

# Install Rust (for Rust implementation if needed)
echo "Installing Rust..."
brew install rust
echo "Rust installed."

# Install C++ build tools (for C++ implementation if needed)
echo "Installing C++ build tools..."
brew install cmake
echo "C++ build tools installed."

echo
echo "==============================================================="
echo "Installing development tools"
echo "==============================================================="
echo

# Install Git
echo "Installing Git..."
brew install git
echo "Git installed."

# Install curl for API testing
echo "Installing curl..."
brew install curl
echo "curl installed."

# Install Postman for API testing
echo "Installing Postman..."
brew install --cask postman
echo "Postman installed."

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
python3 --version
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
echo "You may need to restart your terminal or run 'source ~/.zshrc' (or '~/.bash_profile')"
echo "to ensure all environment variables are properly set."
echo
echo "You can now run any of the Employee Service implementations."
echo "Navigate to the specific implementation directory and run the run script."
echo
echo "For example:"
echo "cd employee-service-nodejs"
echo "chmod +x run.sh"
echo "./run.sh"
echo
