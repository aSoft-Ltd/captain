package captain

import kollections.iMapOf
import kollections.toIList
import kotlin.jvm.JvmName

@JvmName("routeConfigBestMatch")
fun <C> Collection<RouteConfig<C>>.bestMatch(url: Url) = matches(url).bestMatch()

fun <C> Collection<RouteConfig<C>>.matches(url: String) = matches(Url(url))

fun <C> Collection<RouteConfig<C>>.matches(url: Url): List<RouteInfo<C>> {
    val options = map { it.route }.toIList()
    return mapNotNull { rc ->
        val route = rc.route
        val match = url.matches(route) ?: return@mapNotNull null
        RouteInfo(match, options, iMapOf(), url.trail(), rc.content)
    }
}

@JvmName("routeInfoBestMatch")
fun <C> Collection<RouteInfo<C>>.bestMatch(): RouteInfo<C>? {
    val topScore = maxOf { it.match.score() }
    return filter {
        it.match.score() == topScore
    }.minByOrNull {
        it.match.config.segments.size
    }
}