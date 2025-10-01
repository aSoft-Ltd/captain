plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A react router that follows any type of navigator"

kotlin {
    if (Targeting.JS) js(IR) { browserLib() }

    sourceSets {
        if (Targeting.JS) jsMain.dependencies {
            api(project.dependencies.platform(kotlinw.bom))
            api(projects.captainRouterCore)
            api(projects.captainNavigatorBrowser)
            api(libs.cinematic.live.react)
            api(kotlinw.react)
        }
    }
}