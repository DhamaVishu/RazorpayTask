
buildscript {
    repositories {
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
        maven {
            url = uri("https://storage.googleapis.com/r8-releases/raw/master")
        }

    }

    dependencies {
        // Add the dependency for the Google services Gradle plugin
        classpath ("com.google.gms:google-services:4.4.2")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
        classpath ("com.android.tools.build:gradle:8.7.3")
//        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.22")
        // Must be before the Gradle Plugin for Android.
    }

}
plugins {
id("com.android.library") version "7.2.2" apply false
id("com.android.application") version "7.2.2" apply false
id("org.jetbrains.kotlin.android") version "2.0.0" apply false

}