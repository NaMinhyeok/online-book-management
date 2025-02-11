FROM gradle:jdk21-jammy AS builder
WORKDIR /build

COPY gradlew .
COPY gradle ./gradle
RUN chmod +x gradlew

COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY src ./src

RUN ./gradlew dependencies --no-daemon
RUN gradle --no-daemon clean bootJar -x test

ARG SPRING_PROFILES_ACTIVE
ARG DATABASE_HOST
ARG DATABASE_NAME
ARG DATABASE_USER
ARG DATABASE_PASSWORD

ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE
ENV DATABASE_HOST=$DATABASE_HOST
ENV DATABASE_NAME=$DATABASE_NAME
ENV DATABASE_USER=$DATABASE_USER
ENV DATABASE_PASSWORD=$DATABASE_PASSWORD

FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=builder /build/build/libs/*.jar book-management.jar
ENTRYPOINT ["java", "-jar", "/app/book-management.jar"]