# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file into the container
COPY target/api-gateway-1.0-SNAPSHOT.jar app.jar

# Expose the port that the API Gateway service runs on
EXPOSE 1001

# Command to run the application
CMD ["java", "-jar", "app.jar"]
