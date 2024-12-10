plugins {
    `kotlin-dsl`
}

object Versions {
    const val KOTLIN = "2.0.21"
    const val MANAGEMENT_PLUGIN = "1.1.6"
    const val SPRING_BOOT_GRADLE_PLUGIN = "3.4.0"
}

object Dependencies {
    const val KOTLIN_NO_ARG =
        "org.jetbrains.kotlin:kotlin-noarg:${Versions.KOTLIN}"
    const val KOTLIN_ALL_OPEN =
        "org.jetbrains.kotlin:kotlin-allopen:${Versions.KOTLIN}"
    const val KOTLIN_GRADLE_PLUGIN =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    const val MANAGEMENT_PLUGIN =
        "io.spring.gradle:dependency-management-plugin:${Versions.MANAGEMENT_PLUGIN}"
    const val SPRING_BOOT_GRADLE_PLUGIN =
        "org.springframework.boot:spring-boot-gradle-plugin:${Versions.SPRING_BOOT_GRADLE_PLUGIN}"
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(Dependencies.KOTLIN_NO_ARG)
    implementation(Dependencies.KOTLIN_ALL_OPEN)
    implementation(Dependencies.KOTLIN_GRADLE_PLUGIN)
    implementation(Dependencies.MANAGEMENT_PLUGIN)
    implementation(Dependencies.SPRING_BOOT_GRADLE_PLUGIN)
}
