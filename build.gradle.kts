buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")
    }
    repositories{
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.google.gms:google-services:4.4.1")
        classpath ("com.google.firebase:perf-plugin:1.4.2")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
}