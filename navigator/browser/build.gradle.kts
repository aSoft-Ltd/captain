import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "An implementation of the captain-navigator for browser"

kotlin {
    if (Targeting.JS) js(IR) { browserLib(testTimeout = 10) } //
    if (Targeting.WASM) wasmJs { browser() }

    sourceSets {
        commonMain.dependencies {
            api(projects.captainNavigatorApi)
        }

        commonTest.dependencies {
            implementation(projects.captainNavigatorTest)
        }
    }
}