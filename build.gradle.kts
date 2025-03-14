plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1" // Add Shadow plugin for a fat JAR
}

group = "com.mills"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.github.kwhat:jnativehook:2.2.2")
}

tasks.test {
    useJUnitPlatform()
}

// Ensure the JAR has the correct Main-Class attribute
tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.mills.Main"
    }
}

// Use Shadow JAR to package dependencies into one JAR
tasks.shadowJar {
    archiveClassifier.set("") // Ensures the output JAR has a clean name
}

application {
    mainClass.set("com.mills.Main")
}