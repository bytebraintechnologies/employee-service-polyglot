# Backend Health Dashboard - Installation Guide

This document will guide you through setting up and running the Backend Health Dashboard application.

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- [Node.js](https://nodejs.org/) (version 14.x or higher)
- npm (usually comes with Node.js)

## Installation Steps

### Option 1: Using the Setup Script (Recommended)

1. Navigate to the frontend-app directory:
   ```
   cd frontend-app
   ```

2. Run the setup script:
   - On Windows: Double-click on `setup.bat` or run it from the command line
   ```
   setup.bat
   ```

3. Once the setup is complete, you can start the application using:
   - On Windows: Double-click on `start.bat` or run it from the command line
   ```
   start.bat
   ```

### Option 2: Manual Installation

1. Navigate to the frontend-app directory:
   ```
   cd frontend-app
   ```

2. Install the required dependencies:
   ```
   npm install
   ```

3. Start the development server:
   ```
   npm start
   ```

## Accessing the Application

Once the application is running, it will be available at:

```
http://localhost:3000
```

Your default web browser should open automatically with the dashboard.

## Connecting to Backend Services

The application is configured to connect to the following backend services on their respective ports:

| Implementation | Port | Base URL                |
|----------------|------|-------------------------|
| Java           | 8080 | http://localhost:8080   |
| Node.js        | 8081 | http://localhost:8081   |
| Python         | 8082 | http://localhost:8082   |
| Rust           | 8083 | http://localhost:8083   |
| C++            | 8084 | http://localhost:8084   |
| Ruby           | 8085 | http://localhost:8085   |
| C#/.NET        | 8086 | http://localhost:8086   |
| Clojure        | 8087 | http://localhost:8087   |

You'll need to start these backend services separately based on your testing needs.

## Troubleshooting

### CORS Issues

If you encounter CORS (Cross-Origin Resource Sharing) issues when accessing the backend APIs, make sure your backend services have CORS enabled to allow requests from `http://localhost:3000`.

### Connection Errors

If the health check for a backend service shows red (offline), verify that:

1. The backend service is running
2. It's running on the expected port
3. The health endpoint is correctly implemented and accessible

## Building for Production

To create a production build of the application, run:

```
npm run build
```

This will create optimized files in the `build` folder that can be deployed to a web server.