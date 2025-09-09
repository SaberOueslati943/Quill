# Use OpenJDK 21 as base image
FROM openjdk:21-jdk-slim

# Install Playwright and browsers
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Install Node.js and Playwright
RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
    && apt-get install -y nodejs \
    && npm install -g @playwright/test@1.44.0 \
    && npx playwright install --with-deps

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy source code
COPY src src

# Make gradlew executable
RUN chmod +x ./gradlew

# Install dependencies and run tests
RUN ./gradlew test

# Default command to run tests
CMD ["./gradlew", "test"]
