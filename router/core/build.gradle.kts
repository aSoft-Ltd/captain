plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "An abstract router that can be used anywhere"

kotlin {
    if (Targeting.JVM) jvm { library() }
    if (Targeting.JS) js(IR) { library() }
    if (Targeting.WASM) wasmJs { library() }
    if (Targeting.WASM) wasmWasi { library() }
    val osxTargets = if (Targeting.OSX) osxTargets() else listOf()
//    val ndkTargets = if (Targeting.NDK) ndkTargets() else listOf()
    val linuxTargets = if (Targeting.LINUX) linuxTargets() else listOf()
//    val mingwTargets = if (Targeting.MINGW) mingwTargets() else listOf()

    sourceSets {
        commonMain.dependencies {
            api(projects.captainNavigatorApi)
            api(libs.kase.core)
        }

        commonTest.dependencies {
            implementation(libs.kommander.core)
            implementation(projects.captainNavigatorBasic)
        }

        if (Targeting.JVM) jvmMain.dependencies {
            implementation(kotlin("test-junit5"))
        }
    }
}