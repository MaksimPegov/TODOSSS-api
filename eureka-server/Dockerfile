# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file into the container
COPY target/eureka-server-1.0-SNAPSHOT.jar app.jar

# Expose the port that Eureka Server runs on
EXPOSE 8761

# Command to run the application
CMD ["java", "-jar", "app.jar"]
