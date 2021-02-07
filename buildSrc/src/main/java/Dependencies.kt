package com.dpal

object Config {
    const val minSdk = 24
    const val targetSdk = 30
    const val compileSdk = 30

    const val buildTools = "30.0.2"
}

object Dependencies {


    object Klint {
        const val gradle = "org.jlleitschuh.gradle:ktlint-gradle:9.4.1"
        const val plugin = "org.jlleitschuh.gradle.ktlint"
    }

    object Kotlin {
        private const val kotlinVersion = "1.4.10"
        const val gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.3.2"
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val material = "com.google.android.material:material:1.2.1"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val unit = "androidx.test.ext:junit:1.1.2"
    }

    val gradle = "com.android.tools.build:gradle:4.1.2"

    const val coil = "io.coil-kt:coil:1.1.1"

    const val junit = "junit:junit:4.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
}