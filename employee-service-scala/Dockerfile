FROM hseeberger/scala-sbt:11.0.12_1.5.5_2.13.6

WORKDIR /app

# Copy only our simplified files
COPY build.sbt ./
COPY src/main/resources/ ./src/main/resources/
COPY src/main/scala/com/example/employeeservice/EmployeeServiceApp.scala ./src/main/scala/com/example/employeeservice/

# Copy project directory (for plugins)
COPY project ./project

# Build the application
RUN sbt compile

# Expose port
EXPOSE 8089

# Command to run the application
CMD ["sbt", "run"]
