plugins {
    id("java")
}

group = "digital.erben.gfu"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("net.datafaker:datafaker")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.mock-server:mockserver-client-java")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:mongodb")
}
