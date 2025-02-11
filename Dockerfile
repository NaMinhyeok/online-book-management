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

FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=builder /build/build/libs/*.jar book-management.jar
ENTRYPOINT ["java", "-jar", "/app/book-management.jar"]