#!/bin/bash

# Set your Docker Hub username
username="maksimpegov"

# Build and push images for all microservices
microservices=("api-gateway" "todos" "users" "eureka-server")

# Loop through each microservice and build/push its image
for service in "${microservices[@]}"; do
  service_dir="$service"
  cd "$service_dir"

  # Build the Docker image
  docker buildx build --platform linux/amd64 -t "$service" .

  # Tag and push the Docker image
  docker tag "$service" "$username"/todo-service-"$service":latest
  docker push "$username"/todo-service-"$service":latest

  cd ..
done
