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
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.5"
        const val material = "com.google.android.material:material:1.3.0"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val unit = "androidx.test.ext:junit:1.1.2"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val core = "com.squareup.retrofit2:retrofit:${version}"
        const val gson = "com.squareup.retrofit2:converter-gson:${version}"
        const val rx = "com.squareup.retrofit2:adapter-rxjava3:${version}"
    }

    object Rx {
        const val two = "io.reactivex.rxjava2:rxjava:2.2.20"
        const val java = "io.reactivex.rxjava3:rxjava:3.0.0"
        const val kotlin = "io.reactivex.rxjava3:rxkotlin:3.0.0"
        const val android = "io.reactivex.rxjava3:rxandroid:3.0.0"

        object ReplayingShare {
            private const val version = "3.0.0"
            const val core = "com.jakewharton.rx3:replaying-share:$version"
            const val kotlin = "com.jakewharton.rx3:replaying-share-kotlin:$version"
        }

        const val bridge = "com.github.akarnokd:rxjava3-bridge:3.0.0"

        object Binding {
            const val version = "4.0.0"
            const val core = "com.jakewharton.rxbinding4:rxbinding:${version}"

            const val recyclerview = "com.jakewharton.rxbinding4:rxbinding-recyclerview:${version}"
        }
    }

    val gradle = "com.android.tools.build:gradle:4.1.2"

    const val coil = "io.coil-kt:coil:1.1.1"

    const val drivable = "com.github.eholtrop:drivable:1.0.1"

    const val junit = "junit:junit:4.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
}