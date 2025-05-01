from flask import Blueprint, jsonify
from loguru import logger
import time
import platform
import psutil
import os

# Create a Blueprint for health routes
health_bp = Blueprint('health', __name__)

@health_bp.route('', methods=['GET'])
def health_check():
    """Health check endpoint"""
    logger.info("Health check requested")
    
    # Get system information
    system_info = {
        "status": "UP",
        "service": "employee-service-python",
        "time": time.strftime("%Y-%m-%d %H:%M:%S"),
        "system": {
            "os": platform.system(),
            "version": platform.version(),
            "python_version": platform.python_version(),
        },
        "resources": {
            "cpu_usage": psutil.cpu_percent(),
            "memory_usage": psutil.virtual_memory().percent,
            "pid": os.getpid()
        }
    }
    
    return jsonify(system_info)
