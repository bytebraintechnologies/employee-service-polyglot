/**
 * Application configuration
 */

// Default health check interval in milliseconds (30 seconds)
export const HEALTH_CHECK_INTERVAL = 30000;

// API request timeout in milliseconds (5 seconds)
export const API_TIMEOUT = 5000;

// Default employee data for create operation
export const DEFAULT_EMPLOYEE = {
  name: "New Employee",
  title: "Software Engineer",
  department: "Engineering",
  salary: 85000
};

// CORS configuration (if needed)
export const CORS_HEADERS = {
  'Access-Control-Allow-Origin': '*',
  'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
  'Access-Control-Allow-Headers': 'Content-Type, Authorization'
};

// Feature flags for experimental features
export const FEATURES = {
  enableAutoRefresh: true,
  showApiHeaders: false,
  enableBatchOperations: false,
  debugMode: false
};