plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A kotlin multiplatform library for representing headless collection based ui such as lists, tables and grids"

kotlin {
    jvm { library() }
    if (Targeting.JS) js(IR) { library() }
//    if (Targeting.WASM) wasm { library() }
    val osxTargets = if (Targeting.OSX) osxTargets() else listOf()
//    val ndkTargets = if (Targeting.NDK) ndkTargets() else listOf()
    val linuxTargets = if (Targeting.LINUX) linuxTargets() else listOf()
    val mingwTargets = if (Targeting.MINGW) mingwTargets() else listOf()

    sourceSets {

        val commonMain by getting {
            dependencies {
                api(libs.kase.optional)
                api(libs.kollections.interoperable)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kommander.core)
            }
        }
    }
}