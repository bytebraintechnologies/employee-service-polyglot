import React from 'react';

/**
 * Component to display a backend service card with health status
 */
const BackendCard = ({ backend, selected, status, onSelect }) => {
  return (
    <div 
      className={`language-card ${selected ? 'selected' : ''}`}
      onClick={() => onSelect(backend.id)}
    >
      <div className="language-icon">{backend.icon}</div>
      <div className="language-name">{backend.name}</div>
      <div 
        className={`status-indicator ${
          status === 'up' ? 'status-green' : 
          status === 'down' ? 'status-red' : 
          'status-checking'
        }`}
        title={status === 'up' ? 'Online' : status === 'down' ? 'Offline' : 'Checking...'}
      />
    </div>
  );
};

export default BackendCard;