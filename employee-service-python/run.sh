#!/bin/bash

echo "Setting up Python virtual environment..."
python -m venv venv
source venv/bin/activate

echo "Installing dependencies..."
pip install -r requirements.txt

echo "Starting Employee Service Python..."
python app.py
