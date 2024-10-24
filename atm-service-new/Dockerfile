# Use the official Java 21 image
FROM openjdk:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Copy the built JAR file to the image
COPY target/atm-service-0.0.1-SNAPSHOT.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expose the application port
EXPOSE 8080
