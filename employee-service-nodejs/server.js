const express = require('express');
const bodyParser = require('body-parser');
const morgan = require('morgan');
const cors = require('cors');
const logger = require('./config/logger');
const employeeRoutes = require('./routes/employee.routes');

// Create Express app
const app = express();

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// HTTP request logger
app.use(morgan('combined', { stream: { write: message => logger.info(message.trim()) } }));

// Custom middleware to log request body
app.use((req, res, next) => {
  if (req.body && Object.keys(req.body).length > 0) {
    logger.info(`Request Body: ${JSON.stringify(req.body)}`);
  }
  next();
});

// Routes
app.use('/api/employees', employeeRoutes);

// Root route
app.get('/', (req, res) => {
  res.json({ message: 'Welcome to Employee Service API' });
});

// Error handling middleware
app.use((err, req, res, next) => {
  logger.error(`Error: ${err.message}`);
  res.status(500).json({
    message: err.message || 'An unknown error occurred',
    timestamp: new Date()
  });
});

// Set port and start server
const PORT = process.env.PORT || 8081;
app.listen(PORT, () => {
  logger.info(`Server is running on port ${PORT}`);
  console.log(`Server is running on port ${PORT}`);
});
