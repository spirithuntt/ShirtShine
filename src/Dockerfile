# Start with a base image containing Java runtime (stage 1)
FROM openjdk:8-jdk-alpine as build

WORKDIR /app

# Add Maven dependencies (keep in cache to avoid re-downloading when the source code changes)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src src

# Package the application
RUN mvn package -DskipTests

# Multi-stage build for the front-end (stage 2)
FROM node:14 as frontend

WORKDIR /app

# Copy package.json and package-lock.json
COPY frontend/package*.json ./

# Install dependencies
RUN npm install

# Copy source code
COPY frontend/ .

# Build the application
RUN npm run build

# Final stage (stage 3)
FROM openjdk:8-jdk-alpine

WORKDIR /app

# Copy the jar file from stage 1
COPY --from=build /app/target/*.jar app.jar

# Copy the built front-end application from stage 2
COPY --from=frontend /app/build/ /app/public/

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]