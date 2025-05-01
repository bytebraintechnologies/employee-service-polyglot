import React from 'react';

/**
 * Simple loading indicator component
 */
const LoadingIndicator = ({ message = 'Loading...' }) => {
  return (
    <div className="loading-container">
      <div className="spinner"></div>
      <p className="loading-message">{message}</p>
    </div>
  );
};

export default LoadingIndicator;