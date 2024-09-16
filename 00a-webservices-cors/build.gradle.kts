plugins {
    id("java")
}

group = "digital.erben.gfu"
version = "0.0.1-SNAPSHOT"


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.apache.httpcomponents.client5:httpclient5")
}
