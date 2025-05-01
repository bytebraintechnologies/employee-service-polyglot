import React, { useState, useEffect } from 'react';

/**
 * Component to display a welcome overlay with getting started information
 * when the user first loads the application
 */
const WelcomeOverlay = ({ onClose }) => {
  const [isVisible, setIsVisible] = useState(true);
  const [showAgain, setShowAgain] = useState(true);

  useEffect(() => {
    // Check if the user has dismissed this overlay before
    const hasSeenWelcome = localStorage.getItem('hasSeenWelcome') === 'true';
    if (hasSeenWelcome) {
      setIsVisible(false);
    }
  }, []);

  const handleClose = () => {
    setIsVisible(false);
    if (!showAgain) {
      localStorage.setItem('hasSeenWelcome', 'true');
    }
    if (onClose) {
      onClose();
    }
  };

  if (!isVisible) {
    return null;
  }

  return (
    <div className="welcome-overlay">
      <div className="welcome-content">
        <h2>Welcome to Backend Health Dashboard</h2>
        
        <div className="welcome-body">
          <p>
            This dashboard allows you to interact with and compare the performance
            of backend services implemented in different programming languages.
          </p>
          
          <h3>Getting Started</h3>
          <ol>
            <li>Ensure your backend services are running</li>
            <li>Select a backend language by clicking on its card</li>
            <li>Make API calls using the buttons in the API Operations section</li>
            <li>View responses and completion times</li>
          </ol>
          
          <p>
            The health status indicator shows which backends are online (green) 
            and which are offline (red).
          </p>
        </div>
        
        <div className="welcome-footer">
          <label className="show-again-label">
            <input 
              type="checkbox" 
              checked={showAgain}
              onChange={(e) => setShowAgain(e.target.checked)}
            />
            Show this welcome screen again
          </label>
          
          <button className="welcome-close-button" onClick={handleClose}>
            Get Started
          </button>
        </div>
      </div>
    </div>
  );
};

export default WelcomeOverlay;