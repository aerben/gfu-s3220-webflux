group = "digital.erben.gfu"
version = "1.0-SNAPSHOT"
plugins {
    id("com.diffplug.spotless") version "6.25.0" apply false
}
repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
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
