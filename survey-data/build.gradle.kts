import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("plugin.jpa") version "1.9.25"
    id("org.jetbrains.kotlin.kapt")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("com.querydsl:querydsl-apt:5.0.0")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    implementation("com.querydsl:querydsl-core:5.0.0")

    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    implementation(project(":survey-core"))
    runtimeOnly("com.h2database:h2")
}

val querydslDir = project.layout.buildDirectory.dir("generated/querydsl")
tasks.withType<JavaCompile> {
    options.annotationProcessorPath = configurations.kapt.get()
    options.compilerArgs.add("-Aquerydsl.generated=${querydslDir.get().asFile.absolutePath}")
}

sourceSets {
    main {
        java.srcDirs(querydslDir.get().asFile.absolutePath)
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<BootJar> {
    enabled = false
}
