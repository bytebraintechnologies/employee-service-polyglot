#!/bin/bash

echo "Installing dependencies..."
rm -f Gemfile.lock
bundle install --without development

echo "Starting Employee Service Ruby..."
bundle exec ruby app.rb
