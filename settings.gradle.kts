pluginManagement {
    includeBuild("../build-logic")
}

plugins {
    id("multimodule")
}

fun includeSubs(base: String, path: String = base, vararg subs: String) {
    subs.forEach {
        include(":$base-$it")
        project(":$base-$it").projectDir = File("$path/$it")
    }
}

listOf(
    "kommander", "kollections", "kevlar", "cinematic",
    "kase","kiota"
).forEach { includeBuild("../$it") }

rootProject.name = "captain"

// submodules
includeSubs("captain-navigator", "navigator", "api", "basic", "browser", "test")
includeSubs("captain-router", "router", "core") // , "generic")
includeSubs("captain-router-react", "router/react", "core", "dom")
includeSubs("captain-router-compose", "router/compose", "core", "html")

// samples
includeSubs("samples-router", "samples/router", "react", "compose")
