FROM node:18-alpine

WORKDIR /app

# Copy the Node.js fallback server
COPY minimal-server.js .

# Environment variables
ENV PORT=8087

# Expose port
EXPOSE ${PORT}

# Command to run the application
CMD ["node", "minimal-server.js"]