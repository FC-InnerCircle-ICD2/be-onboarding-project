plugins {
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}
