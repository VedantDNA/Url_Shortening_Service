# syntax=docker/dockerfile:1

############################
# Stage 1 — Build
############################
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only pom.xml first (better caching)
COPY pom.xml .
RUN mvn dependency:go-offline -DskipTests

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests


############################
# Stage 2 — Run
############################
FROM eclipse-temurin:17-jre-jammy

# Create a non-root user for security
ARG UID=10001
RUN adduser \
  --disabled-password \
  --gecos "" \
  --home "/nonexistent" \
  --shell "/sbin/nologin" \
  --no-create-home \
  --uid "${UID}" \
  appuser
USER appuser

WORKDIR /app

# Copy only the built jar from the previous stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
