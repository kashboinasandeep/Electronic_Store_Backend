# Use an official OpenJDK image as the base image
#FROM openjdk:17-alpine

#ARG JAR_FILE=target/*.jar
	

# Copy the built JAR file into the container
#COPY ${JAR_FILE}  app.jar


# Run the application
#ENTRYPOINT ["java", "-jar", "/app.jar"]


# Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]