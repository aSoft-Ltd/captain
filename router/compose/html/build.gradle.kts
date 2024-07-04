import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.compose")
}

description = "A compose-html router that follows any type of navigator"

kotlin {
    js(IR) { library() }
    sourceSets {
        val jsMain by getting {
            dependencies {
                api(projects.captainNavigatorBrowser)
                api(projects.captainRouterComposeCore)
                api(compose.html.core)
            }
        }
        val jsTest by getting {
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

tasks.withType(KotlinCompile::class).configureEach {
    kotlinOptions {
        val v = kotlinz.versions.kotlin.get()
        freeCompilerArgs += listOf(
            "-P", "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=$v"
        )
    }
}