FROM eclipse-temurin:17-jdk-alpine

# Install wget for health checks
RUN apk add --no-cache wget

WORKDIR /app

# Create logs directory
RUN mkdir -p /app/logs

COPY target/*.jar app.jar

# Configure logging
COPY src/main/resources/logback-spring.xml /app/logback-spring.xml
ENV LOGGING_CONFIG=/app/logback-spring.xml

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]
