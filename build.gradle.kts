import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    java
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    id("io.freefair.lombok").version("8.6").apply(false)
}

allprojects {
    group = "com.innercircle"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java")
    apply(plugin = "io.freefair.lombok")

    configure<DependencyManagementExtension> {
        dependencies {
            dependency("org.springframework.boot:spring-boot-starter-data-jpa:3.4.0")
            dependency("org.springframework.boot:spring-boot-starter-web:3.4.0")
            dependency("org.springframework.boot:spring-boot-starter-integration:3.4.0")

            dependency("org.projectlombok:lombok:1.18.36")

            dependency("org.apache.commons:commons-lang3:3.17.0")
            dependency("org.apache.commons:commons-collections4:4.4")

            dependency("com.h2database:h2:2.3.232")
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
