plugins {
    id("java")
}

group = "digital.erben.gfu"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("io.micrometer:micrometer-registry-prometheus")

    implementation("io.projectreactor:reactor-tools")

    implementation("io.projectreactor:reactor-core-micrometer")

    implementation("net.datafaker:datafaker")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")

    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.mock-server:mockserver-client-java")
    testImplementation("org.testcontainers:mockserver")
    testImplementation("org.testcontainers:testcontainers")
}
