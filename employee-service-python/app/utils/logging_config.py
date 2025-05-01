import os
import sys
from loguru import logger

def setup_logging():
    """Configure application logging"""
    
    # Clear any default handlers
    logger.remove()
    
    # Configure console logging
    logger.add(
        sys.stdout,
        level="INFO",
        format="<green>{time:YYYY-MM-DD HH:mm:ss}</green> | <level>{level: <8}</level> | <cyan>{name}</cyan>:<cyan>{function}</cyan>:<cyan>{line}</cyan> - <level>{message}</level>"
    )
    
    # Configure file logging
    logs_dir = os.path.join(os.path.dirname(os.path.dirname(os.path.dirname(__file__))), 'logs')
    
    # Ensure logs directory exists
    if not os.path.exists(logs_dir):
        os.makedirs(logs_dir)
    
    # Add file handler for all logs
    logger.add(
        os.path.join(logs_dir, "app.log"),
        rotation="10 MB",
        retention="1 month",
        level="DEBUG",
        format="{time:YYYY-MM-DD HH:mm:ss} | {level: <8} | {name}:{function}:{line} - {message}"
    )
    
    # Add file handler for error logs
    logger.add(
        os.path.join(logs_dir, "error.log"),
        rotation="10 MB",
        retention="1 month",
        level="ERROR",
        format="{time:YYYY-MM-DD HH:mm:ss} | {level: <8} | {name}:{function}:{line} - {message}"
    )
    
    return logger
