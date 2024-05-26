FROM openjdk:23-oraclelinux9

# Set the working directory
WORKDIR /httpserverselect

# Download the MySQL JDBC driver
RUN curl -L -o mysql-connector-java.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.29/mysql-connector-java-8.0.29.jar

# Copy the application JAR file
COPY httpserverselect.jar /httpserverselect/

# Expose the port
EXPOSE 4000/tcp

# Set the classpath to include the JDBC driver and the application JAR
ENV CLASSPATH=/httpserverselect/mysql-connector-java.jar:/httpserverselect/httpserverselect.jar

# Define the command to run the application
CMD ["java", "httpserverselect"]
