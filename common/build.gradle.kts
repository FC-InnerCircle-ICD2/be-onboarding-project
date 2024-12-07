dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework:spring-web")
    implementation("org.springframework.integration:spring-integration-core")
    implementation("org.springframework.boot:spring-boot-autoconfigure")

    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("org.apache.commons:commons-lang3")
    implementation("org.apache.commons:commons-collections4")
}
