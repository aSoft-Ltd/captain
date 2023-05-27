import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("js")
    id("tz.co.asoft.library")
}

description = "A sample for react router"

kotlin {
    js(IR) {
//        browserApp { }
        moduleName = project.name
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
                outputFileName = "main.bundle.js"
//                devServer = devServer?.copy(open = true)
//
//                    KotlinWebpackConfig.DevServer(
//                    open = true,
//                    static = mutableListOf(file("build/processedResources/js/main").absolutePath)
//                )
            }
        }
        binaries.executable()
    }

    sourceSets {
        val main by getting {
            dependencies {
                implementation(projects.captainNavigatorBrowser)
                implementation(projects.captainRouterReact)
                implementation(kotlinw.react.dom.new)
            }
        }
    }
}