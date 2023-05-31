package captain

import kollections.iMapOf
import kollections.toIList
import kotlin.jvm.JvmName

fun Url.matches(url: Url): RouteMatch? {
    val p = when {
        url.segments.size >= segments.size -> segments
        else -> url.segments
    }
    val pathMatches = mutableListOf<SegmentMatch>()
    for (i in p.indices) {
        val match = segments[i].matches(url.segments[i]) ?: return null
        pathMatches.add(match)
    }
    return RouteMatch(url, pathMatches)
}

private fun String.matches(configPath: String): SegmentMatch? {
    if (configPath == "*") return WildCardMatch(this)
    if (configPath.startsWith(":")) {
        val param = configPath.substringAfter(":")
        return DynamicParamMatch(this, param, this)
    }
    if (configPath.startsWith("{")) {
        val param = configPath.removePrefix("{").removeSuffix("}")
        return DynamicParamMatch(this, param, this)
    }
    if (configPath == this) return ExactMatch(this)
    return null
}

@JvmName("routeConfigBestMatch")
fun <C> Collection<RouteConfig<C>>.bestMatch(url: Url) = matches(url).bestMatch()

fun <C> Collection<RouteConfig<C>>.matches(url: String) = matches(Url(url))
fun <C> Collection<RouteConfig<C>>.matches(url: Url): List<RouteInfo<C>> {
    val options = map { it.route }.toIList()
    return mapNotNull { rc ->
        val route = rc.route
        val params = url.matches(route) ?: return@mapNotNull null
        RouteInfo(params, options, iMapOf(), url.trail(), rc.content)
    }
}

@JvmName("routeInfoBestMatch")
fun <C> Collection<RouteInfo<C>>.bestMatch(): RouteInfo<C>? {
    val topScore = maxOf { it.match.score() }
    return filter {
        it.match.score() == topScore
    }.minByOrNull {
        it.match.url.segments.size
    }
}