import os
from app import create_app
from loguru import logger

app = create_app()

if __name__ == '__main__':
    logger.info("Starting Employee Service Python Application")
    port = int(os.environ.get('PORT', 5000))
    app.run(host='0.0.0.0', port=port, debug=True)
