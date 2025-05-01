FROM node:18-alpine

WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the app
COPY . .

# Environment variables
ENV PORT=8081

# Expose port
EXPOSE ${PORT}

# Command to run the application
CMD ["node", "server.js"]