# Use a lightweight Java 21 base image
FROM eclipse-temurin:21-jre-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Spring Boot JAR file into the container
# The JAR is created by the bootJar task in build/libs/
COPY build/libs/*.jar app.jar

# Expose the port Spring Boot runs on (default is 8080)
EXPOSE 8080

# The command to run your application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]