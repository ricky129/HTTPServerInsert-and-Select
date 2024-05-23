# Use a Java runtime as base image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR files into the container
COPY httpserverinsert.jar /app/
COPY mysql-connector-j-8.0.32.jar /app/

# Expose port 8080
EXPOSE 8080

# Command to run the server
CMD ["java", "-cp", "mysql-connector-java-8.0.32.jar:httpserverinsert.jar", "httpserverinsert"]
