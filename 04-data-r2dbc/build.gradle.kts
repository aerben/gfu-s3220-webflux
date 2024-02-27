plugins {
    id("java")
}

group = "digital.erben.gfu"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("net.datafaker:datafaker")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("io.r2dbc:r2dbc-h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.mock-server:mockserver-client-java")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:mongodb")
}
