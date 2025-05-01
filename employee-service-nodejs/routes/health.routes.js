const express = require('express');
const os = require('os');
const process = require('process');
const logger = require('../config/logger');

const router = express.Router();

/**
 * @route GET /health
 * @desc Health check endpoint
 * @access Public
 */
router.get('/', (req, res) => {
    logger.info('Health check requested');
    
    // Get system information
    const healthData = {
        status: 'UP',
        service: 'employee-service-nodejs',
        time: new Date().toISOString(),
        system: {
            platform: process.platform,
            nodeVersion: process.version,
            uptime: process.uptime(),
            hostname: os.hostname()
        },
        resources: {
            freeMemory: os.freemem(),
            totalMemory: os.totalmem(),
            memoryUsage: process.memoryUsage(),
            cpuUsage: process.cpuUsage(),
            loadAverage: os.loadavg()
        }
    };
    
    res.json(healthData);
});

module.exports = router;