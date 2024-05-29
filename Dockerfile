# Docker multi-stage build

# 1. Building the App with Maven
FROM maven:3.8.7-eclipse-temurin-19-alpine AS builder

ADD . /java-springboot
WORKDIR /java-springboot

# Just echo so we can see, if everything is there :)
RUN ls -l

# Run Maven build
RUN mvn clean install

# 2. Just using the build artifact and then removing the build-container
FROM openjdk:19-alpine

# Install libstdc++6
RUN apk add --no-cache libstdc++

# Additional security updates
RUN apk add --upgrade libtasn1-progs && \
    apk update && apk upgrade zlib

# Create a new user with UID 10014
RUN addgroup -g 10014 choreo && \
    adduser --disabled-password --no-create-home --uid 10014 --ingroup choreo choreouser

VOLUME /tmp

USER 10014

# Add Spring Boot app.jar to Container
COPY --from=builder "/java-springboot/target/demo-0.0.1-SNAPSHOT*.jar" app.jar

# Fire up our Spring Boot app by default
CMD ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]
