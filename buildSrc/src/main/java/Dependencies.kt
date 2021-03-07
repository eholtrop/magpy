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
        private const val kotlinVersion = "1.4.30"
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
        const val core = "com.squareup.retrofit2:retrofit:$version"
        const val gson = "com.squareup.retrofit2:converter-gson:$version"
        const val rx = "com.squareup.retrofit2:adapter-rxjava3:$version"
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
            const val core = "com.jakewharton.rxbinding4:rxbinding:$version"

            const val recyclerview = "com.jakewharton.rxbinding4:rxbinding-recyclerview:$version"
        }
    }

    object Compose {
        const val core =  "androidx.compose.ui:ui:1.0.0-beta01"
        const val runtime = "androidx.compose.runtime:runtime:1.0.0-beta-01"

        // Tooling support (Previews, etc.)
        const val tooling =  "androidx.compose.ui:ui-tooling:1.0.0-beta01"
        // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
        const val foundation =  "androidx.compose.foundation:foundation:1.0.0-beta01"
        // Material Design
        const val material =  "androidx.compose.material:material:1.0.0-beta01"
        // Material design icons
        const val designIcons =  "androidx.compose.material:material-icons-core:1.0.0-beta01"
        const val designIconsExtended =  "androidx.compose.material:material-icons-extended:1.0.0-beta01"
        // Integration with activities
        const val activity =  "androidx.activity:activity-compose:1.3.0-alpha03"
        // Integration with ViewModels
        const val viewModel =  "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha02"
        // Integration with observables
        const val liveData =  "androidx.compose.runtime:runtime-livedata:1.0.0-beta01"
        const val rxJava =  "androidx.compose.runtime:runtime-rxjava3:1.0.0-beta01"
    }

    val gradle = "com.android.tools.build:gradle:7.0.0-alpha08"

    object Coil {
        const val core = "io.coil-kt:coil:1.1.1"
        const val accompanist = "dev.chrisbanes.accompanist:accompanist-coil:0.6.1"

    }


    const val drivable = "com.github.eholtrop:drivable:1.0.1"

    const val junit = "junit:junit:4.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
}