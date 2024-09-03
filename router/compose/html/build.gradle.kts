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