/**
 * Backend service definitions
 */
const BACKENDS = [
  { 
    id: 'java', 
    name: 'Java (Spring Boot)', 
    port: 8080, 
    icon: '☕',
    healthEndpoint: '/actuator/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'nodejs', 
    name: 'Node.js (Express)', 
    port: 8081, 
    icon: '🟢',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'python', 
    name: 'Python (Flask)', 
    port: 8082, 
    icon: '🐍',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'rust', 
    name: 'Rust', 
    port: 8083, 
    icon: '🦀',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'cpp', 
    name: 'C++', 
    port: 8084, 
    icon: '⚙️',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'ruby', 
    name: 'Ruby (Sinatra)', 
    port: 8085, 
    icon: '💎',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'csharp', 
    name: 'C# (.NET)', 
    port: 8086, 
    icon: '🔷',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'clojure', 
    name: 'Clojure', 
    port: 8087, 
    icon: '🔄',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  }
];

export default BACKENDS;