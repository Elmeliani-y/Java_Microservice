

FROM openjdk:17-jdk-slim

# Install wget for health checks
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Create logs directory
RUN mkdir -p /app/logs

# Copy the JAR file
COPY target/*.jar app.jar

# Configure logging
COPY src/main/resources/logback-spring.xml /app/logback-spring.xml
ENV LOGGING_CONFIG=/app/logback-spring.xml

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
