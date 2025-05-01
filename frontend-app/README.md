# Backend Health Dashboard - Frontend Application

This React application provides a dashboard to interact with various backend services implemented in different programming languages. It allows you to:

1. Select from multiple backend implementations (Java, Node.js, Python, Rust, C++, Ruby, C#, and Clojure)
2. See the health status of each backend service (green/red indicator)
3. Make API calls to the selected backend
4. View the response and completion time of each API call

## Features

- Visual selection of backend language
- Real-time health status monitoring
- API endpoints for common CRUD operations
- Response display with JSON formatting
- API call completion time tracking

## Prerequisites

- Node.js and npm installed on your system
- Backend services running on their respective ports (see below)

## Port Assignments for Backend Services

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

## Running the Frontend Application

1. Navigate to the frontend directory:
   ```
   cd frontend-app
   ```

2. Install dependencies:
   ```
   npm install
   ```

3. Start the development server:
   ```
   npm start
   ```

4. Open your browser and visit:
   ```
   http://localhost:3000
   ```

## Usage Instructions

1. First, ensure that at least one of the backend services is running
2. The dashboard will automatically check the health status of each backend
3. Select a backend service by clicking on its card
4. Use the API operation buttons to interact with the selected backend
5. View the API response in the results section
6. The completion time of each API call will be displayed at the bottom of the page

## Troubleshooting

- If a backend shows as "red" (offline), make sure the corresponding service is running
- Each backend may implement the health endpoint differently, so check their documentation
- For CORS issues, ensure your backend services have CORS enabled for localhost:3000

## Additional Notes

- The dashboard automatically checks backend health every 30 seconds
- API calls to offline backends will fail with an error message
- The API response section shows formatted JSON for easy reading