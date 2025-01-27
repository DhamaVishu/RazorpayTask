
buildscript {
    repositories {
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
        maven {
            url = uri("https://storage.googleapis.com/r8-releases/raw/master")
        }

    }

    dependencies {
        classpath ("com.google.gms:google-services:4.4.2")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
        classpath ("com.android.tools.build:gradle:8.7.3")
        classpath("com.google.firebase:perf-plugin:1.1.0") {
            exclude(group = "com.google.guava", module = "guava-jdk5")
        }
    }

}
plugins {
id("com.android.library") version "7.2.2" apply false
id("com.android.application") version "7.2.2" apply false
id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
}