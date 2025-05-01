# Backend Health Dashboard - User Guide

This document will help you understand how to use the Backend Health Dashboard application.

## Dashboard Overview

The Backend Health Dashboard provides a unified interface to:

1. Monitor the health status of various backend services
2. Select a specific backend language for testing
3. Perform API operations against the selected backend
4. View API responses and completion times

## Main Features

### Backend Selection

- The dashboard displays cards for each available backend implementation
- Each card shows:
  - The backend language/framework name
  - A health status indicator (green for online, red for offline)
- Click on a card to select that backend for API operations

### Health Status Monitoring

- The dashboard automatically checks the health of all backend services every 30 seconds
- A green indicator means the service is running and responding to health checks
- A red indicator means the service is offline or not responding
- A yellow indicator means the health status is being checked

### API Operations

The following API operations are available once you select a backend:

- **GET All Employees**: Retrieves a list of all employees
- **GET Employee #1**: Retrieves details of a specific employee
- **Create Employee**: Creates a new employee record
- **Update Employee**: Updates an existing employee record
- **Delete Employee**: Deletes an employee record

These buttons will be disabled if:
- No backend is selected
- The selected backend is offline
- An API call is currently in progress

### Response Display

- After making an API call, the response data is displayed in a formatted JSON view
- During API calls, a loading spinner indicates that the request is in progress
- At the bottom of the page, a completion time indicator shows how long the API call took

## Using the Dashboard

### Step 1: Start the Backend Services

Before using the dashboard, ensure that at least one of the backend services is running. The dashboard will automatically detect which services are online.

### Step 2: Select a Backend

Click on one of the backend cards to select it for testing. The selected card will be highlighted with a blue border.

### Step 3: Perform API Operations

Once a backend is selected, use the API operation buttons to interact with the backend service:

1. Click "GET All Employees" to retrieve all employee records
2. Click "GET Employee #1" to retrieve a specific employee
3. Click "Create Employee" to add a new employee
4. Click "Update Employee" to modify an existing employee
5. Click "Delete Employee" to remove an employee

### Step 4: View Results

After each API operation:
- The API response will be displayed in the results section
- The completion time will be shown at the bottom of the page
- You can perform additional operations or switch to a different backend

## Understanding Response Times

The completion time indicator at the bottom of the page shows the total time taken for an API call, including:
- Network latency
- Backend processing time
- Response parsing time

This allows you to compare the performance of different backend implementations for the same API operations.

## Best Practices

1. **Compare similar operations**: When comparing performance between backends, ensure you're testing the same operation types.
2. **Run multiple tests**: Response times can vary due to system load, so run multiple tests to get a representative sample.
3. **Check health status**: Always verify that a backend is online before testing it.
4. **Clear system resources**: For the most accurate comparison, ensure no unnecessary applications are running on your system.

## Troubleshooting

- If all backends show as offline, check your network connection and ensure the backend services are running
- If an API call fails, check the error message in the response section for details
- If the dashboard is not updating, try refreshing the page