import React from 'react';

/**
 * Component to display API call completion time
 */
const CompletionTime = ({ time, success }) => {
  if (!time) return null;

  return (
    <div className="completion-time" style={{ 
      backgroundColor: success ? '#2c3e50' : '#c0392b' 
    }}>
      {time}
    </div>
  );
};

export default CompletionTime;