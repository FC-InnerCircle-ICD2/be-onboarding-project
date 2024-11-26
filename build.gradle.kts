plugins {
    java
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
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

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.0")
        implementation("org.springframework.boot:spring-boot-starter-web:3.4.0")
        implementation("org.projectlombok:lombok:1.18.36")

        implementation("org.apache.commons:commons-lang3:3.17.0")
        implementation("org.apache.commons:commons-collections4:4.4")

        annotationProcessor("org.projectlombok:lombok:1.18.36")

        runtimeOnly("com.h2database:h2:2.3.232")

        testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.0")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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
