import React from 'react';

/**
 * Component to display API responses
 */
const ApiResponse = ({ response, isLoading }) => {
  if (isLoading) {
    return (
      <div className="results-section">
        <h2>API Response</h2>
        <div className="loading-indicator">
          <div className="spinner"></div>
          <p>Loading...</p>
        </div>
      </div>
    );
  }

  if (!response) {
    return null;
  }

  return (
    <div className="results-section">
      <h2>API Response</h2>
      <pre className="response-display">
        {JSON.stringify(response, null, 2)}
      </pre>
    </div>
  );
};

export default ApiResponse;