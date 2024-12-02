plugins {
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

extra["snippetsDir"] = file("build/generated-snippets")

val asciidoctorExt = "asciidoctorExt"
configurations.create("asciidoctorExt") {
    extendsFrom(configurations.testImplementation.get())
}

dependencies {
    implementation("com.h2database:h2")
    implementation(project(":survey-core"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.3.1")
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
}

val snippetsDir by extra { file("build/generated-snippets") }

tasks {
    test {
        outputs.dir(snippetsDir)
        useJUnitPlatform()
    }

    asciidoctor {
        inputs.dir(snippetsDir)
        configurations("asciidoctorExt")
        dependsOn(test)
    }

    bootJar {
        dependsOn(asciidoctor)
        from ("build/docs/asciidoc") {
            into("static/docs")
        }
    }
}
