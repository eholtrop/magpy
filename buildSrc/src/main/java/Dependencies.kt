object Config {
    const val minSdk = 24
    const val targetSdk = 30
    const val compileSdk = 30

    const val buildTools = "30.0.2"
}

object Dependencies {
    private const val kotlinVersion = "1.4.10"
    const val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val kotlin_stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"

    val gradle = "com.android.tools.build:gradle:4.1.2"

    const val androidx_core = "androidx.core:core-ktx:1.3.2"
    const val androidx_appCompat = "androidx.appcompat:appcompat:1.2.0"
    const val androidx_material = "com.google.android.material:material:1.2.1"
    const val androidx_constraint = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val androidx_unit = "androidx.test.ext:junit:1.1.2"

    const val coil = "io.coil-kt:coil:1.1.1"

    const val junit = "junit:junit:4.0"
    const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
}