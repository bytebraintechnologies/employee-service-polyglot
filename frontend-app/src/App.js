import React, { useState, useEffect } from 'react';
import './App.css';
import BackendCard from './components/BackendCard';
import ApiResponse from './components/ApiResponse';
import CompletionTime from './components/CompletionTime';
import WelcomeOverlay from './components/WelcomeOverlay';
import LoadingIndicator from './components/LoadingIndicator';
import BackendStats from './components/BackendStats';
import apiService from './services/apiService';
import BACKENDS from './constants/backends';
import { HEALTH_CHECK_INTERVAL, DEFAULT_EMPLOYEE } from './config';

function App() {
  const [backends, setBackends] = useState(BACKENDS.map(backend => ({
    ...backend,
    status: 'checking',
    selected: false
  })));
  
  const [selectedBackend, setSelectedBackend] = useState(null);
  const [apiResponse, setApiResponse] = useState(null);
  const [completionTime, setCompletionTime] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [initialLoading, setInitialLoading] = useState(true);

  const [showWelcome, setShowWelcome] = useState(true);

  // Check the health of all backends
  useEffect(() => {
    const checkHealth = async () => {
      const updatedBackends = [...backends];
      
      for (let i = 0; i < updatedBackends.length; i++) {
        const backend = updatedBackends[i];
        const baseUrl = `http://localhost:${backend.port}`;
        const isUp = await apiService.checkHealth(baseUrl, backend.healthEndpoint);
        updatedBackends[i] = { ...backend, status: isUp ? 'up' : 'down' };
      }
      
      setBackends(updatedBackends);
      setInitialLoading(false);
    };
    
    checkHealth();
    
    // Set up interval to check health every 30 seconds
    const intervalId = setInterval(checkHealth, HEALTH_CHECK_INTERVAL);
    
    return () => clearInterval(intervalId);
  }, []);
  
  const handleBackendSelect = (backendId) => {
    const updatedBackends = backends.map(backend => ({
      ...backend,
      selected: backend.id === backendId
    }));
    
    setBackends(updatedBackends);
    setSelectedBackend(backends.find(backend => backend.id === backendId));
    setApiResponse(null);
  };
  
  const callApi = async (endpoint) => {
    if (!selectedBackend) return;
    
    setIsLoading(true);
    setApiResponse(null);
    
    const baseUrl = `http://localhost:${selectedBackend.port}`;
    const apiEndpoint = selectedBackend.apiEndpoint;
    let result;
    
    if (endpoint === 'GET_ALL') {
      result = await apiService.getAllEmployees(baseUrl, apiEndpoint);
    } else if (isNaN(endpoint)) {
      // Handle non-numeric endpoints like 'CREATE'
      return;
    } else {
      // Assume it's a GET by ID or DELETE by ID depending on the method
      if (endpoint.startsWith('DELETE_')) {
        const id = endpoint.replace('DELETE_', '');
        result = await apiService.deleteEmployee(baseUrl, apiEndpoint, id);
      } else {
        result = await apiService.getEmployeeById(baseUrl, apiEndpoint, endpoint);
      }
    }
    
    setApiResponse(result.data || result.error);
    setCompletionTime(`API call ${result.success ? 'completed' : 'failed'} in: ${result.timeInMs}ms`);
    setIsLoading(false);
  };
  
  const getNewEmployee = () => {
    return DEFAULT_EMPLOYEE;
  };
  
  const createEmployee = async () => {
    if (!selectedBackend) return;
    
    setIsLoading(true);
    setApiResponse(null);
    
    const baseUrl = `http://localhost:${selectedBackend.port}`;
    const apiEndpoint = selectedBackend.apiEndpoint;
    const newEmployee = getNewEmployee();
    
    const result = await apiService.createEmployee(baseUrl, apiEndpoint, newEmployee);
    
    setApiResponse(result.data || result.error);
    setCompletionTime(`API call ${result.success ? 'completed' : 'failed'} in: ${result.timeInMs}ms`);
    setIsLoading(false);
  };
  
  const updateEmployee = async () => {
    if (!selectedBackend) return;
    
    setIsLoading(true);
    setApiResponse(null);
    
    const baseUrl = `http://localhost:${selectedBackend.port}`;
    const apiEndpoint = selectedBackend.apiEndpoint;
    
    try {
      // First get all employees to find one to update
      const getAllResult = await apiService.getAllEmployees(baseUrl, apiEndpoint);
      
      if (getAllResult.success && getAllResult.data && getAllResult.data.length > 0) {
        const employeeToUpdate = getAllResult.data[0];
        const updatedEmployee = {
          ...employeeToUpdate,
          title: "Senior Software Engineer",
          salary: employeeToUpdate.salary ? employeeToUpdate.salary + 10000 : 85000
        };
        
        const updateResult = await apiService.updateEmployee(
          baseUrl, 
          apiEndpoint, 
          employeeToUpdate.id, 
          updatedEmployee
        );
        
        setApiResponse(updateResult.data || updateResult.error);
        setCompletionTime(`API call ${updateResult.success ? 'completed' : 'failed'} in: ${updateResult.timeInMs}ms`);
      } else {
        setApiResponse({ error: "No employees found to update" });
        setCompletionTime(`API call failed in: ${getAllResult.timeInMs}ms`);
      }
    } catch (error) {
      setApiResponse({ error: error.message });
      setCompletionTime(`API call failed`);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="App">
      {/* Welcome Overlay */}
      {showWelcome && <WelcomeOverlay onClose={() => setShowWelcome(false)} />}
      
      <div className="header">
        <h1>Backend Health Dashboard</h1>
      </div>
      
      <div className="backend-selector">
        <h2>Select Backend Service</h2>
        {initialLoading ? (
          <LoadingIndicator message="Checking backend health status..." />
        ) : (
        <div className="language-grid">
          {backends.map(backend => (
            <BackendCard 
              key={backend.id}
              backend={backend}
              selected={backend.selected}
              status={backend.status}
              onSelect={handleBackendSelect}
            />
          ))}
        </div>
        )}
      </div>
      
      {/* Backend Stats */}
      {!initialLoading && <BackendStats backends={backends} />}
      
      <div className="api-section">
        <h2>API Operations</h2>
        <p>
          {selectedBackend 
            ? `Selected Backend: ${selectedBackend.name} (${selectedBackend.status === 'up' ? 'Online' : 'Offline'})`
            : 'Please select a backend service'}
        </p>
        
        <div className="api-buttons">
          <button 
            className="api-button"
            onClick={() => callApi('GET_ALL')}
            disabled={!selectedBackend || selectedBackend.status !== 'up' || isLoading}
          >
            Get All Employees
          </button>
          
          <button 
            className="api-button"
            onClick={() => callApi('1')}
            disabled={!selectedBackend || selectedBackend.status !== 'up' || isLoading}
          >
            Get Employee #1
          </button>
          
          <button 
            className="api-button"
            onClick={createEmployee}
            disabled={!selectedBackend || selectedBackend.status !== 'up' || isLoading}
          >
            Create Employee
          </button>
          
          <button 
            className="api-button"
            onClick={updateEmployee}
            disabled={!selectedBackend || selectedBackend.status !== 'up' || isLoading}
          >
            Update Employee
          </button>
          
          <button 
            className="api-button"
            onClick={() => callApi('DELETE_1')}
            disabled={!selectedBackend || selectedBackend.status !== 'up' || isLoading}
          >
            Delete Employee #1
          </button>
        </div>
      </div>
      
      <ApiResponse 
        response={apiResponse} 
        isLoading={isLoading} 
      />
      
      <CompletionTime 
        time={completionTime} 
        success={apiResponse && !apiResponse.error}
      />
    </div>
  );
}

export default App;