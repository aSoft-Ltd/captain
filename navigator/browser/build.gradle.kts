plugins {
    kotlin("js")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "An implementation of the captain-navigator for browser"

kotlin {
    js(IR) { browserLib(testTimeout = 10) }

    sourceSets {
        val main by getting {
            dependencies {
                api(projects.captainNavigatorApi)
            }
        }

        val test by getting {
            dependencies {
                implementation(projects.captainNavigatorTest)
            }
        }
    }
}