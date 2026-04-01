#!/bin/bash

# Install Java 18 if not available
if ! command -v java &> /dev/null; then
    echo "Installing Java 18..."
    apt-get update
    apt-get install -y openjdk-18-jdk
    export JAVA_HOME=/usr/lib/jvm/java-18-openjdk-amd64
    export PATH=$JAVA_HOME/bin:$PATH
fi

# Navigate to backend directory
cd backend

# Make gradlew executable
chmod +x gradlew

# Build the application
./gradlew build -x test

# Start the application with production profile
java -jar -Dspring.profiles.active=prod build/libs/*.jar