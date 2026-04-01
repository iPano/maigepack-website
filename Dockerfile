# Multi-stage build for better optimization
FROM eclipse-temurin:17-jdk-alpine AS builder

# Install bash for Gradle wrapper
RUN apk add --no-cache bash

# Set working directory for build
WORKDIR /build

# Copy Gradle files
COPY backend/gradlew ./
COPY backend/gradlew.bat ./
COPY backend/gradle/ ./gradle/
COPY backend/build.gradle ./
COPY backend/settings.gradle ./

# Make gradlew executable
RUN chmod +x gradlew

# Copy source code
COPY backend/src/ ./src/

# Build the application (use --no-daemon for Docker builds)
RUN ./gradlew build -x test --no-daemon

# Runtime stage - smaller image for production
FROM eclipse-temurin:17-jre-alpine

# Install bash for runtime
RUN apk add --no-cache bash

# Create app user for security
RUN addgroup -g 1001 -S appuser && adduser -S appuser -G appuser

# Set working directory
WORKDIR /app

# Copy built jar from builder stage
COPY --from=builder /build/build/libs/*.jar app.jar

# Change ownership to app user
RUN chown appuser:appuser app.jar

# Switch to non-root user
USER appuser

# Expose port
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]