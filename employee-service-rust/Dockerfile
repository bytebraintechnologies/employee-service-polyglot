FROM rust:1.69 as build

WORKDIR /app

# Copy Cargo files
COPY Cargo.toml Cargo.lock ./

# Create a dummy source file
RUN mkdir -p src && echo "fn main() {}" > src/main.rs

# Build dependencies
RUN cargo build --release

# Remove the dummy build artifacts
RUN rm -rf src target/release/deps/employee_service_rust*

# Copy the actual source code
COPY . .

# Build the application
RUN cargo build --release

# Runtime stage
FROM debian:bullseye-slim

WORKDIR /app

# Install required dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
    ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# Copy the binary from the build stage
COPY --from=build /app/target/release/employee-service-rust .

# Environment variables
ENV PORT=8083

# Expose port
EXPOSE ${PORT}

# Command to run the application
CMD ["./employee-service-rust"]