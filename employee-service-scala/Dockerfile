FROM hseeberger/scala-sbt:11.0.12_1.5.5_2.13.6 as build

WORKDIR /app

# Copy build.sbt and project files
COPY build.sbt ./
COPY project ./project

# Copy the source code
COPY src/ ./src/

# Build the application
RUN sbt assembly

# Runtime stage
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/scala-*/employee-service-scala-assembly-*.jar app.jar

# Environment variables
ENV PORT=8089

# Expose port
EXPOSE ${PORT}

# Command to run the application
CMD ["java", "-jar", "app.jar"]
