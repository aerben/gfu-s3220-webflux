plugins {
    id("java")
}

group = "digital.erben.gfu"
version = "0.0.1-SNAPSHOT"


dependencies {
    implementation("net.datafaker:datafaker")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.github.resilience4j:resilience4j-ratelimiter:2.2.0")
    implementation("io.github.resilience4j:resilience4j-reactor:2.2.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.mock-server:mockserver-client-java")
    testImplementation("org.testcontainers:mockserver")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:testcontainers")
}
