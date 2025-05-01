FROM mcr.microsoft.com/dotnet/sdk:7.0 AS build

WORKDIR /app

# Copy csproj and restore dependencies
COPY *.csproj ./
RUN dotnet restore

# Copy the rest of the app
COPY . ./

# Build the application
RUN dotnet publish -c Release -o out

# Runtime stage
FROM mcr.microsoft.com/dotnet/aspnet:7.0

WORKDIR /app

# Copy the build files from the build stage
COPY --from=build /app/out ./

# Environment variables
ENV PORT=8086
ENV ASPNETCORE_URLS=http://+:${PORT}

# Expose port
EXPOSE ${PORT}

# Command to run the application
CMD ["dotnet", "employee-service-csharp.dll"]