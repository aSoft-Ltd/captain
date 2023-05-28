package captain

import kotlin.jvm.JvmName

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

@JvmName("routeConfigBestMatch")
fun <C> Collection<RouteConfig<C>>.bestMatch(url: Url) = matches(url).bestMatch(url)

fun <C> Collection<RouteConfig<C>>.matches(url: Url) = mapNotNull { rc ->
    val route = rc.route
    val params = url.matches(route) ?: return@mapNotNull null
    RouteInfo(params, route, url, rc.content)
}

@JvmName("routeInfoBestMatch")
fun <C> Collection<RouteInfo<C>>.bestMatch(url: Url): RouteInfo<C>? {
    if (isEmpty()) return null
    val exact = firstOrNull { it.config.trail() == url.trail() }
    if (exact != null) return exact
    return maxBy { it.config.trail().length }
}