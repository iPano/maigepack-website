# Use OpenJDK 18 as base image
FROM openjdk:18-jdk-slim

# Set working directory
WORKDIR /app

# Copy backend source code
COPY backend/ ./backend/

# Copy start script
COPY start.sh ./

# Make scripts executable
RUN chmod +x start.sh
RUN chmod +x backend/gradlew

# Set Java home
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Build the application
RUN cd backend && ./gradlew build -x test

# Expose port
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "backend/build/libs/backend-0.0.1-SNAPSHOT.jar"]