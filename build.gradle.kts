import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

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

    // Example of adding common repositories
    repositories {
        mavenCentral()
        // Add other repositories here
    }
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
            }
        }
    }

    configure<DependencyManagementExtension> {
        dependencies {
            dependency("org.testcontainers:junit-jupiter:1.19.6")
            dependency("org.mock-server:mockserver-client-java:5.15.0")
            dependency("org.testcontainers:mockserver:1.19.6")
            dependency("org.testcontainers:testcontainers:1.19.6")
            dependency("org.testcontainers:mongodb:1.19.6")
            dependency("net.datafaker:datafaker:2.1.0")
        }
    }

    plugins.withType<JavaPlugin>().configureEach {
        val extension = the<JavaPluginExtension>()
        extension.sourceCompatibility = JavaVersion.VERSION_17
        extension.targetCompatibility = JavaVersion.VERSION_17
    }


    // Example of configuring the test task
    tasks.withType<Test> {
        useJUnitPlatform()
        // Configure other test settings here
    }
}
