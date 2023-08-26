#!/bin/bash

# Build all Spring Boot applications and Docker containers

echo "Building all Spring Boot applications and Docker containers..."

# Build and package Spring Boot applications
for module in "api-gateway" "eureka-server" "todos" "users"; do
  echo "Building $module..."
  (cd "$module" && mvn clean package spring-boot:repackage)
done

# Build Docker containers
for module in "api-gateway" "eureka-server" "todos" "users"; do
  echo "Building Docker image for $module..."
  (cd "$module" && docker build -t "$module" .)
done

echo "Build process completed!"
