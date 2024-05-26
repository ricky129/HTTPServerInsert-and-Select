FROM openjdk:23-oraclelinux9

# Set the working directory
WORKDIR /httpserverinsert

# Download the MySQL JDBC driver
RUN curl -L -o mysql-connector-java.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.29/mysql-connector-java-8.0.29.jar

# Copy the application JAR file
COPY httpserverinsert.jar /httpserverinsert/

# Expose the port
EXPOSE 4000/tcp

# Set the classpath to include the JDBC driver and the application JAR
ENV CLASSPATH=/httpserverinsert/mysql-connector-java.jar:/httpserverinsert/httpserverinsert.jar

# Define the command to run the application
CMD ["java", "httpserverinsert"]
