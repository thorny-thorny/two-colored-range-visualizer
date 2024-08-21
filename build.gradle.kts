plugins {
    kotlin("js") version "2.0.10"
}

group = "me.thorny"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.3.1-pre.793")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.3.1-pre.793")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.13.0-pre.793")
    implementation("me.thorny:two-colored-range:1.0.0")
}

kotlin {
    js {
        binaries.executable()
        browser()
    }
}
