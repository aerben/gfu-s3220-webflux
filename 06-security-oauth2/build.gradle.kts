plugins {
    id("java")
}

group = "digital.erben.gfu"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("net.datafaker:datafaker")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.webjars:jquery:3.7.1")
    implementation("org.webjars:js-cookie:2.1.0")
    implementation("org.webjars:bootstrap:4.3.1")
    implementation("org.webjars:webjars-locator-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.mock-server:mockserver-client-java")
    testImplementation("org.testcontainers:mockserver")
    testImplementation("org.testcontainers:testcontainers")
}
