#!/bin/bash

echo "Making all run.sh scripts executable..."

# Find all run.sh files and make them executable
find . -name "run.sh" -type f -exec chmod +x {} \;

echo "All run.sh scripts are now executable."
