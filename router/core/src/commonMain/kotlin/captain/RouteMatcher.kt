package captain

import kollections.iMapOf
import kollections.toIList
import kollections.toIMap
import kotlin.jvm.JvmName

@JvmName("routeConfigBestMatch")
fun <C> Collection<RouteConfig<C>>.bestMatch(url: Url) = matches(url).bestMatch()

fun <C> Collection<RouteConfig<C>>.matches(url: String) = matches(Url(url))

fun <C> Collection<RouteConfig<C>>.matches(url: Url): List<RouteInfo<C>> {
    val options = map { it.route }.toIList()
    return mapNotNull { rc ->
        val route = rc.route
        val match = url.matches(route) ?: return@mapNotNull null
        // The parent change
        RouteInfo(null, match, options, iMapOf(), rc.content)
    }
}

@JvmName("routeInfoBestMatch")
fun <C> Collection<RouteInfo<C>>.bestMatch(): RouteInfo<C>? {
    if (isEmpty()) return null
    val topScore = maxOf { it.match.score() }
    return filter {
        it.match.score() == topScore
    }.minByOrNull {
        it.match.pattern.segments.size
    }
}

fun Collection<RouteConfig<*>>.missingRouteMessage(route: Url): String {
    return "Failed to find matching route for ${route.path} from options " +
            joinToString(prefix = "[", separator = ",", postfix = "]") { "'${it.route.path}'" }
}

fun <C> selectRoute(parent: RouteInfo<C>?, currentRoute: Url, options: List<RouteConfig<C>>): RouteInfo<C>? {
    val base = parent?.match?.pattern ?: Url("/")
    val rebasedRoute = base.rebase(currentRoute)
    val matches = options.matches(rebasedRoute)

    val match = matches.bestMatch()?.copy(
        matches = matches.associate { it.match.route to it.match.score() }.toIMap()
    )

    if (match == null) {
        println(options.map { it.copy(base.sibling(it.route.path)) }.missingRouteMessage(currentRoute))
        return null
    }

    val parentPattern = parent?.match?.pattern
    val childPattern = parentPattern?.sibling(match.match.pattern.path) ?: match.match.pattern
    return match.copy(
        parent = parent,
        options = match.options.map { parentPattern?.sibling(it.path) ?: it },
        matches = matches.associate {
            val pattern = it.match.pattern
            (parentPattern?.sibling(pattern.path) ?: pattern) to it.match.score()
        }.toIMap(),
        match = UrlMatch(currentRoute.trail(), childPattern, match.match.segments)
    )
}