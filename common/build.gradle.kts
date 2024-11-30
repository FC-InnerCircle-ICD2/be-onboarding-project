dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework:spring-web")

    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("org.apache.commons:commons-lang3")
    implementation("org.apache.commons:commons-collections4")
}
