plugins {
    kotlin("js")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A react router that follows any type of navigator"

kotlin {
    js(IR) { browserLib() }

    sourceSets {
        val main by getting {
            dependencies {
                api(project.dependencies.platform(kotlinw.bom))
                api(libs.captain.router.core)
                api(libs.captain.navigator.browser)
                api(libs.cinematic.live.react)
                api(kotlinw.react)
            }
        }
    }
}