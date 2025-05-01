FROM python:3.9-slim

WORKDIR /app

# Copy requirements
COPY requirements.txt .

# Install dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of the app
COPY . .

# Environment variables
ENV PORT=8082
ENV FLASK_APP=run.py
ENV PYTHONUNBUFFERED=1

# Expose port
EXPOSE ${PORT}

# Command to run the application
CMD ["python", "run.py"]