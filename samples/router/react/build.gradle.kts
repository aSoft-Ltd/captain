plugins {
    kotlin("multiplatform")
    id("tz.co.asoft.library")
}

description = "A sample for react router"

kotlin {
    js(IR) {
        outputModuleName.set(project.name)
//        moduleName = project.name
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
                outputFileName = "main.bundle.js"
            }
        }
        generateTypeScriptDefinitions()
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(projects.captainRouterReactDom)
                implementation(projects.captainNavigatorBrowser)
            }
        }
    }
}