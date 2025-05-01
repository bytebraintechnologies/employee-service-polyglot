FROM ruby:3.2-alpine

WORKDIR /app

# Install build dependencies
RUN apk add --no-cache build-base

# Copy Gemfile and Gemfile.lock
COPY Gemfile Gemfile.lock ./

# Install dependencies
RUN bundle install

# Copy the rest of the app
COPY . .

# Environment variables
ENV PORT=8085

# Expose port
EXPOSE ${PORT}

# Command to run the application
CMD ["ruby", "app.rb"]