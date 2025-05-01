FROM ubuntu:22.04 as build

# Install dependencies
RUN apt-get update && apt-get install -y \
    g++ \
    cmake \
    git \
    libboost-all-dev \
    nlohmann-json3-dev \
    libasio-dev \
    && rm -rf /var/lib/apt/lists/*

# Install Crow C++ web framework
WORKDIR /tmp
RUN git clone https://github.com/CrowCpp/Crow.git \
    && cd Crow \
    && mkdir build \
    && cd build \
    && cmake .. -DCROW_BUILD_EXAMPLES=OFF -DCROW_BUILD_TESTS=OFF -DCROW_FEATURES="cookies;ssl;cors" \
    && make install

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
    nlohmann-json3-dev \
    libasio-dev \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy the binary from the build stage
COPY --from=build /app/build/employee_service_cpp .

# Copy shared libraries for Crow (if installed to non-standard locations)
COPY --from=build /usr/local/lib/ /usr/local/lib/
COPY --from=build /usr/local/include/ /usr/local/include/

# Update shared library cache
RUN ldconfig

# Environment variables
ENV PORT=8084

# Expose port
EXPOSE ${PORT}

# Command to run the application
CMD ["./employee_service_cpp"]
