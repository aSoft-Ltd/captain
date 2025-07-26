import org.jetbrains.compose.compose
//import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile

import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    kotlin("plugin.compose")
}


kotlin {
//    target {
//        compilations.all {
////            kotlinOptions.jvmTarget = "11"
//        }
//    }
    sourceSets {
        val main by getting {
            dependencies {
                implementation(projects.captainRouterComposeCore)
                implementation(compose.dependencies.ui)
                implementation(compose.dependencies.desktop.currentOs)
            }
        }
        val test by getting {
            dependencies {
                implementation(libs.kommander.core)
            }
        }
    }
}

compose {
    kotlinCompilerPlugin.set(kotlinz.versions.compose.compiler)

    desktop {
        application {
            mainClass = "MainKt"
            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageName = "captain-demo"
                packageVersion = "1.0.0"
            }
        }
    }
}

//tasks.withType(KotlinCompile::class).configureEach {
//    kotlinOptions {
//        val v = kotlinz.versions.kotlin.get()
//        freeCompilerArgs += listOf(
//            "-P", "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=$v"
//        )
//    }
//}