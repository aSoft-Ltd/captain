plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.compose")
}

description = "A compose-html router that follows any type of navigator"

kotlin {
    if (Targeting.JS) js(IR) { library() }
    sourceSets {
        if (Targeting.JS) jsMain.dependencies {
            api(projects.captainNavigatorBrowser)
            api(projects.captainRouterComposeCore)
            api(compose.html.core)
        }

        if (Targeting.JS) jsTest.dependencies {
            implementation(libs.kommander.core)
        }
    }
}