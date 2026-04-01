#!/bin/bash

# Navigate to backend directory
cd backend

# Build the application
./gradlew build -x test

# Start the application with production profile
java -jar -Dspring.profiles.active=prod build/libs/*.jar