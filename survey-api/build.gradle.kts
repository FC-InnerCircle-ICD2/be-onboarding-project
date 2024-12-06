val kotlinJacksonDateTimeVersion by properties
val swaggerOpenApiVersion by properties
val swaggerAnnotationVersion by properties

dependencies {
    implementation(project(":survey-data"))
    implementation(project(":survey-core"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework:spring-tx")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerOpenApiVersion")
    implementation("io.swagger.core.v3:swagger-annotations:$swaggerAnnotationVersion")

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
