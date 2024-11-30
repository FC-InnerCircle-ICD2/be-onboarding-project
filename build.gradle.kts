import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.jlleitschuh.gradle.ktlint").version("12.1.2")
}

val javaVersion: String by properties

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(javaVersion)
    }
}

repositories {
    mavenCentral()
}

subprojects {
    group = "com.ic"
    version = "0.0.1-SNAPSHOT"
    val kotlinJacksonDateTimeVersion by properties

    repositories {
        mavenCentral()
    }

    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    if (project.name != "survey-core") {
        apply(plugin = "org.springframework.boot")
        apply(plugin = "io.spring.dependency-management")

        dependencies {
            testImplementation("org.springframework.boot:spring-boot-starter-test")
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
        // 꼭 전체 모듈에 전체 적용이 필요할까 .. ?!
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$kotlinJacksonDateTimeVersion")
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        testImplementation("io.kotest:kotest-runner-junit5:5.5.0")
        testImplementation("io.kotest:kotest-assertions-core:5.5.0")
        testImplementation("io.kotest:kotest-property:5.5.0")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<BootJar> {
    enabled = false
}

tasks.withType<Test> {
    useJUnitPlatform()
}
