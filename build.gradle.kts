plugins {
    kotlin("js") version "1.6.10"
}

group = "me.thorny"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.240-kotlin-1.5.30")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.240-kotlin-1.5.30")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.1-pre.240-kotlin-1.5.30")
    implementation("me.thorny:two-colored-range:0.9.2")
}

kotlin {
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}
