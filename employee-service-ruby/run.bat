@echo off
echo Installing dependencies...
del Gemfile.lock 2>nul
call bundle install --without development

echo Starting Employee Service Ruby...
call bundle exec ruby app.rb
