package gradle

import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType

group = "study.crispin"
version = "0.0.1"

repositories {
    mavenCentral()
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
