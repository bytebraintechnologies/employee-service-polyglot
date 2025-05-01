import axios from 'axios';
import { API_TIMEOUT } from '../config';

/**
 * Service for handling API calls to the different backend services
 */
class ApiService {
  /**
   * Check the health of a backend service
   * @param {string} baseUrl - Base URL of the backend service
   * @param {string} healthEndpoint - Health check endpoint
   * @returns {Promise<boolean>} - True if the service is up, false otherwise
   */
  async checkHealth(baseUrl, healthEndpoint) {
    try {
      const response = await axios.get(`${baseUrl}${healthEndpoint}`, {
        timeout: API_TIMEOUT // Timeout for health checks
      });
      return response.status >= 200 && response.status < 300;
    } catch (error) {
      console.error(`Health check failed for ${baseUrl}: ${error.message}`);
      return false;
    }
  }

  /**
   * Get all employees from the selected backend
   * @param {string} baseUrl - Base URL of the backend service
   * @param {string} apiEndpoint - API endpoint for employees
   * @returns {Promise<Object>} - API response with timing information
   */
  async getAllEmployees(baseUrl, apiEndpoint) {
    const startTime = new Date();
    try {
      const response = await axios.get(`${baseUrl}${apiEndpoint}`);
      const endTime = new Date();
      return {
        data: response.data,
        success: true,
        timeInMs: endTime - startTime
      };
    } catch (error) {
      const endTime = new Date();
      return {
        error: error.message,
        details: error.response ? error.response.data : 'No response received',
        success: false,
        timeInMs: endTime - startTime
      };
    }
  }

  /**
   * Get a single employee by ID
   * @param {string} baseUrl - Base URL of the backend service
   * @param {string} apiEndpoint - API endpoint for employees
   * @param {string|number} id - Employee ID
   * @returns {Promise<Object>} - API response with timing information
   */
  async getEmployeeById(baseUrl, apiEndpoint, id) {
    const startTime = new Date();
    try {
      const response = await axios.get(`${baseUrl}${apiEndpoint}/${id}`);
      const endTime = new Date();
      return {
        data: response.data,
        success: true,
        timeInMs: endTime - startTime
      };
    } catch (error) {
      const endTime = new Date();
      return {
        error: error.message,
        details: error.response ? error.response.data : 'No response received',
        success: false,
        timeInMs: endTime - startTime
      };
    }
  }

  /**
   * Create a new employee
   * @param {string} baseUrl - Base URL of the backend service
   * @param {string} apiEndpoint - API endpoint for employees
   * @param {Object} employee - Employee data to create
   * @returns {Promise<Object>} - API response with timing information
   */
  async createEmployee(baseUrl, apiEndpoint, employee) {
    const startTime = new Date();
    try {
      const response = await axios.post(`${baseUrl}${apiEndpoint}`, employee);
      const endTime = new Date();
      return {
        data: response.data,
        success: true,
        timeInMs: endTime - startTime
      };
    } catch (error) {
      const endTime = new Date();
      return {
        error: error.message,
        details: error.response ? error.response.data : 'No response received',
        success: false,
        timeInMs: endTime - startTime
      };
    }
  }

  /**
   * Update an existing employee
   * @param {string} baseUrl - Base URL of the backend service
   * @param {string} apiEndpoint - API endpoint for employees
   * @param {string|number} id - Employee ID to update
   * @param {Object} employee - Updated employee data
   * @returns {Promise<Object>} - API response with timing information
   */
  async updateEmployee(baseUrl, apiEndpoint, id, employee) {
    const startTime = new Date();
    try {
      const response = await axios.put(`${baseUrl}${apiEndpoint}/${id}`, employee);
      const endTime = new Date();
      return {
        data: response.data,
        success: true,
        timeInMs: endTime - startTime
      };
    } catch (error) {
      const endTime = new Date();
      return {
        error: error.message,
        details: error.response ? error.response.data : 'No response received',
        success: false,
        timeInMs: endTime - startTime
      };
    }
  }

  /**
   * Delete an employee
   * @param {string} baseUrl - Base URL of the backend service
   * @param {string} apiEndpoint - API endpoint for employees
   * @param {string|number} id - Employee ID to delete
   * @returns {Promise<Object>} - API response with timing information
   */
  async deleteEmployee(baseUrl, apiEndpoint, id) {
    const startTime = new Date();
    try {
      const response = await axios.delete(`${baseUrl}${apiEndpoint}/${id}`);
      const endTime = new Date();
      return {
        data: response.data,
        success: true,
        timeInMs: endTime - startTime
      };
    } catch (error) {
      const endTime = new Date();
      return {
        error: error.message,
        details: error.response ? error.response.data : 'No response received',
        success: false,
        timeInMs: endTime - startTime
      };
    }
  }
}

export default new ApiService();