FROM golang:1.19-alpine as build

WORKDIR /app

# Copy go mod and sum files
COPY go.mod go.sum ./

# Download dependencies
RUN go mod download

# Copy the source code
COPY . .

# Build the application
RUN go build -o employee-service-go

# Runtime stage
FROM alpine:latest

WORKDIR /app

# Copy the binary from the build stage
COPY --from=build /app/employee-service-go .

# Environment variables
ENV PORT=7070

# Expose port
EXPOSE ${PORT}

# Command to run the application
CMD ["./employee-service-go"]