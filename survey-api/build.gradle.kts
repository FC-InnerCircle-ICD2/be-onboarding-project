val kotlinJacksonDateTimeVersion by properties
val swaggerVersion by properties

dependencies {
    implementation(project(":survey-data"))
    implementation(project(":survey-core"))
    implementation("io.swagger.core.v3:swagger-annotations:$swaggerVersion")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$kotlinJacksonDateTimeVersion")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
