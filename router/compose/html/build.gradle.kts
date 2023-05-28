import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile

import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

description = "A compose-html router that follows any type of navigator"

kotlin {
    js(IR) { library() }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.captainRouterComposeCore)
//                api(compose.dependencies.html)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(projects.kommanderCore)
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

tasks.withType(org.jetbrains.kotlin.gradle.dsl.KotlinCompile::class).configureEach {
    kotlinOptions {
        val v = kotlinz.versions.kotlin.get()
        freeCompilerArgs += listOf(
            "-P", "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=$v"
        )
    }
}