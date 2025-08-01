plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A react router that follows any type of navigator"

kotlin {
    js(IR) { browserLib() }

    sourceSets {
        val jsMain by getting {
            dependencies {
                api(projects.captainNavigatorBrowser)
                api(projects.captainRouterReactCore)
                api(project.dependencies.platform(kotlinw.bom))
                api(kotlinw.react.dom.new)
            }
        }
    }
}