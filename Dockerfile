# Use an official OpenJDK image as the base image
FROM openjdk:18-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/*.jar app.jar

# Expose the application's port (change if necessary)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
