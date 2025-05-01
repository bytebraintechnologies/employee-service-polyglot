FROM ubuntu:22.04 as build

# Install dependencies
RUN apt-get update && apt-get install -y \
    g++ \
    cmake \
    git \
    libboost-all-dev \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy CMakeLists.txt and source files
COPY CMakeLists.txt .
COPY src/ ./src/
COPY include/ ./include/

# Create build directory
RUN mkdir -p build

# Build the application
WORKDIR /app/build
RUN cmake .. && make

# Runtime stage
FROM ubuntu:22.04

# Install runtime dependencies
RUN apt-get update && apt-get install -y \
    libboost-all-dev \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy the binary from the build stage
COPY --from=build /app/build/employee-service-cpp .

# Environment variables
ENV PORT=8084

# Expose port
EXPOSE ${PORT}

# Command to run the application
CMD ["./employee-service-cpp"]