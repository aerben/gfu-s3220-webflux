group = "digital.erben.gfu"
version = "1.0-SNAPSHOT"
plugins {
    id("com.diffplug.spotless") version "6.25.0" apply false
    id("org.springframework.boot") version "3.2.2" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false

}
repositories {
    mavenCentral()
}

subprojects {
    plugins.apply("com.diffplug.spotless")
    plugins.apply("java")
    plugins.apply("org.springframework.boot")
    plugins.apply("io.spring.dependency-management")
    plugins.withId("com.diffplug.spotless") {
        // Configure Spotless plugin here
        configure<com.diffplug.gradle.spotless.SpotlessExtension> {
            java {
                importOrder()
                removeUnusedImports()
                cleanthat()
                eclipse()
                formatAnnotations()
            }
        }
    }

    dependencies {

        "implementation"("org.springframework.boot:spring-boot-starter-webflux")
        "testImplementation"("org.springframework.boot:spring-boot-starter-test")
        "testImplementation"("io.projectreactor:reactor-test")
        "testImplementation"("org.testcontainers:junit-jupiter:1.19.6")
        "testImplementation"("org.mock-server:mockserver-client-java:5.15.0")
        "testImplementation"("org.testcontainers:mockserver:1.19.6")
        "testImplementation"("org.testcontainers:testcontainers:1.19.6")
        "implementation"("net.datafaker:datafaker:2.1.0")
    }

    plugins.withType<JavaPlugin>().configureEach {
        val extension = the<JavaPluginExtension>()
        extension.sourceCompatibility = JavaVersion.VERSION_17
        extension.targetCompatibility = JavaVersion.VERSION_17
    }

    // Example of adding common repositories
    repositories {
        mavenCentral()
        // Add other repositories here
    }

    // Example of configuring the test task
    tasks.withType<Test> {
        useJUnitPlatform()
        // Configure other test settings here
    }
}
