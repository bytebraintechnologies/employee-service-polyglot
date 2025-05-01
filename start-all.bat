@echo off
echo Starting all services with Docker Compose...
docker-compose up -d
echo.
echo All services started. You can access the dashboard at:
echo http://localhost
echo.
echo To stop all services, run: docker-compose down
echo.
pause