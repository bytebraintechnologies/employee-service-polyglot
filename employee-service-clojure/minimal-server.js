const http = require('http');

const server = http.createServer((req, res) => {
  // Set CORS headers
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type');
  
  // Handle OPTIONS for CORS preflight
  if (req.method === 'OPTIONS') {
    res.statusCode = 200;
    res.end();
    return;
  }

  // Set response header
  res.setHeader('Content-Type', 'application/json');
  
  // Handle routes
  if (req.url === '/health') {
    console.log('Health check requested');
    res.statusCode = 200;
    res.end(JSON.stringify({ status: 'UP', service: 'employee-service-clojure-fallback' }));
  } else if (req.url === '/') {
    res.statusCode = 200;
    res.end(JSON.stringify({ message: 'Clojure Service Fallback' }));
  } else {
    res.statusCode = 404;
    res.end(JSON.stringify({ error: 'Not Found' }));
  }
});

const port = 8087;
server.listen(port, () => {
  console.log(`Server running on port ${port}`);
});