/**
 * Backend service definitions for Docker environment
 * This uses container names as hostnames instead of localhost
 */
const BACKENDS = [
  { 
    id: 'java', 
    name: 'Java (Spring Boot)', 
    port: 8080, 
    hostname: 'java-service',
    icon: '☕',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'nodejs', 
    name: 'Node.js (Express)', 
    port: 8081,
    hostname: 'nodejs-service', 
    icon: '🟢',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'python', 
    name: 'Python (Flask)', 
    port: 8082,
    hostname: 'python-service', 
    icon: '🐍',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'go', 
    name: 'Go (Gin)', 
    port: 8088,
    hostname: 'go-service', 
    icon: '🦫',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'cpp', 
    name: 'C++', 
    port: 8084,
    hostname: 'cpp-service', 
    icon: '⚙️',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'rust', 
    name: 'Rust', 
    port: 8083,
    hostname: 'rust-service', 
    icon: '🦀',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'ruby', 
    name: 'Ruby (Sinatra)', 
    port: 8085,
    hostname: 'ruby-service', 
    icon: '💎',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'csharp', 
    name: 'C# (.NET)', 
    port: 8086,
    hostname: 'csharp-service', 
    icon: '🔷',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'clojure', 
    name: 'Clojure', 
    port: 8087,
    hostname: 'clojure-service', 
    icon: '🔄',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  },
  { 
    id: 'scala', 
    name: 'Scala (Akka)', 
    port: 8089,
    hostname: 'scala-service', 
    icon: '🔥',
    healthEndpoint: '/health',
    apiEndpoint: '/api/employees'
  }
];

export default BACKENDS;
