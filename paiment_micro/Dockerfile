FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the Maven wrapper and project files
COPY .mvn .mvn
COPY mvnw pom.xml ./

# Download dependencies (cached layer)
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Create logs directory
RUN mkdir -p /app/logs

# Copy the built jar
COPY target/*.jar app.jar

# Configure logging
COPY src/main/resources/logback-spring.xml /app/logback-spring.xml
ENV LOGGING_CONFIG=/app/logback-spring.xml

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
