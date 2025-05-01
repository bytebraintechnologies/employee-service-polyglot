FROM maven:3.8-openjdk-17 as build

WORKDIR /app

# Copy the POM file
COPY pom.xml .

# Copy the source code
COPY src/ ./src/

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-slim

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Environment variables
ENV PORT=8080

# Expose port
EXPOSE ${PORT}

# Command to run the application
CMD ["java", "-jar", "app.jar"]