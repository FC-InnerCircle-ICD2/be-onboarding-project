package gradle

plugins {
    kotlin("jvm")
}

fun getProperty(vararg names: String): String =
    names.firstNotNullOfOrNull {
        properties[it]?.toString()
    } ?: ""

dependencies {
    val kotestVersion: String = getProperty("kotest.version")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("kotest")
    }
}
