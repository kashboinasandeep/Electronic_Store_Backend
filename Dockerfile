# Use an official OpenJDK image as the base image
FROM openjdk:18-alpine

ARG JAR_FILE=target/*.jar
	

# Copy the built JAR file into the container
COPY ${JAR_FILE}  app.jar


# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
