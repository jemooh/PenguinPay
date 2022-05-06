// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.library") version "7.1.2" apply false
    id("com.android.application") version "7.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
}


buildscript {
    val jacocoVersion by extra("0.2")
    val junit5Version by extra("1.7.1.1")

    dependencies {
        classpath("com.hiya:jacoco-android:$jacocoVersion")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:$junit5Version")
    }
}

/*
task clean(type: Delete) {
    delete rootProject.buildDir
}*/
