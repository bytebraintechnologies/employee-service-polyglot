#!/bin/bash

echo "Building Employee Service C++..."
mkdir -p build
cd build
cmake ..
make

echo "Running Employee Service C++..."
./employee_service
