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
                api(projects.captainRouterCore)
                api(projects.captainNavigatorBrowser)
                api(projects.cinematicLiveReact)
                api(kotlinw.react)
            }
        }
    }
}