plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.2") // for JVM platform
    implementation("no.tornado:tornadofx:1.7.20")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}