package captain

fun Url.matches(url: Url): RouteMatchParams? {
    if (this == url) return RouteMatchParams(mapOf(), isExact = true)
    val p = when {
        url.paths.size >= paths.size -> paths
        else -> url.paths
    }
    val params = mutableMapOf<String, String>()
    for (i in p.indices) {
        if (!paths[i].matches(params, url.paths[i])) return null
    }
    return RouteMatchParams(params, isExact = false)
}

private fun String.matches(
    params: MutableMap<String, String>,
    configPath: String
): Boolean {
    if (configPath == "*") return true
    if (configPath.startsWith(":")) {
        val param = configPath.substringAfter(":")
        params[param] = this
        return true
    }
    if (configPath.startsWith("{")) {
        val param = configPath.removePrefix("{").removeSuffix("}")
        params[param] = this
        return true
    }

    return configPath == this
}