import java.io.ByteArrayOutputStream

plugins {
    java
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.epages.restdocs-api-spec") version "0.19.4"
}


group = "org.querypie"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("com.h2database:h2")

    // restdocs-api-spec
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.19.4")

    // spring-rest-docs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

openapi3 {
    this.setServer("http://localhost:8080")
    title = "Book Management API"
    description = "Book Management API description"
    version = "0.1.0"
    format = "yaml"
}

val containerName = "api-doc"

abstract class RunSwaggerTask @Inject constructor(private val execOperations: ExecOperations) : DefaultTask() {
    init {
        dependsOn("openapi3")
    }

    @TaskAction
    fun runSwagger() {
        val containerName = "api-doc"
        val outputStream = ByteArrayOutputStream()

        execOperations.exec {
            commandLine("/usr/local/bin/docker", "ps", "--filter", "name=$containerName", "-q")
            standardOutput = outputStream
        }

        val containerId = outputStream.toString().trim()

        if (containerId.isNotEmpty()) {
            execOperations.exec {
                commandLine("/usr/local/bin/docker", "rm", "-f", containerId)
            }
        }

        execOperations.exec {
            commandLine(
                "/usr/local/bin/docker",
                "run", "-d",
                "-p", "8088:8080",
                "--name", containerName,
                "-v", "./build/api-spec:/usr/share/nginx/html/docs",
                "-e", "URL=/docs/openapi3.yaml",
                "swaggerapi/swagger-ui:latest"
            )
        }
    }
}

tasks.register<RunSwaggerTask>("runSwagger")
