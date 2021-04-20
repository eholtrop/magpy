import com.dpal.Config
import com.dpal.Dependencies

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("MagpyDatabase") {
        packageName = "com.dpal.persistance"
        sourceFolders = listOf("sqldelight2")
    }
}

android {
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("androidAndroidTestRelease")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}
kotlin {
    android()
//    ios {
//        binaries {
//            framework {
//                baseName = "sqldelight"
//            }
//        }
//    }
    sourceSets {
        val commonMain by getting
        val androidMain by getting
//        val iosMain by getting


        androidMain.dependencies {
            implementation(project(":data:tag"))
            implementation(project(":libs:optional-rx"))
            implementation(Dependencies.Rx.java)
            implementation(Dependencies.SqlDelight.android)
            implementation(Dependencies.SqlDelight.rxjava3)
        }

//        iosMain.dependencies {
//            implementation(Dependencies.SqlDelight.native)
//        }

        commonMain.dependencies {
            implementation(Dependencies.SqlDelight.runtime)
        }
    }
}

android {
    compileSdkVersion(Config.compileSdk)
    defaultConfig {
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.targetSdk)
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}