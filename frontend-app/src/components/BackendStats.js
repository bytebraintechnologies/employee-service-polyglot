import React, { useMemo } from 'react';

/**
 * Component to display backend health statistics
 */
const BackendStats = ({ backends }) => {
  const stats = useMemo(() => {
    if (!backends || backends.length === 0) {
      return {
        total: 0,
        online: 0,
        offline: 0,
        checking: 0,
        onlinePercentage: 0
      };
    }

    const total = backends.length;
    const online = backends.filter(b => b.status === 'up').length;
    const offline = backends.filter(b => b.status === 'down').length;
    const checking = backends.filter(b => b.status === 'checking').length;
    
    return {
      total,
      online,
      offline,
      checking,
      onlinePercentage: Math.round((online / total) * 100)
    };
  }, [backends]);

  return (
    <div className="backend-stats">
      <h3>Backend Health Overview</h3>
      <div className="stats-grid">
        <div className="stat-item">
          <div className="stat-value">{stats.total}</div>
          <div className="stat-label">Total Services</div>
        </div>
        <div className="stat-item">
          <div className="stat-value stat-online">{stats.online}</div>
          <div className="stat-label">Online</div>
        </div>
        <div className="stat-item">
          <div className="stat-value stat-offline">{stats.offline}</div>
          <div className="stat-label">Offline</div>
        </div>
        <div className="stat-item">
          <div className="stat-value">{stats.onlinePercentage}%</div>
          <div className="stat-label">Availability</div>
        </div>
      </div>
    </div>
  );
};

export default BackendStats;