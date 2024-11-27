plugins {
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
    implementation("com.h2database:h2")
    implementation(project(":survey-core"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

tasks.test {
    outputs.dir(rootProject.extra["snippetsDir"] ?: "build/generated-snippets")
}

tasks.asciidoctor {
    inputs.dir(rootProject.extra["snippetsDir"] ?: "build/generated-snippets")
    dependsOn(tasks.test)
}