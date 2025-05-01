from flask import Flask
from flask_cors import CORS
from app.utils.logging_config import setup_logging

# Create and initialize the app
def create_app():
    # Initialize Flask app
    app = Flask(__name__)
    
    # Setup CORS
    CORS(app)
    
    # Setup logging
    setup_logging()
    
    # Register blueprints
    from app.routes.employee_routes import employee_bp
    app.register_blueprint(employee_bp, url_prefix='/api/employees')
    
    # Home route
    @app.route('/')
    def home():
        return {'message': 'Welcome to Employee Service API'}
    
    return app
