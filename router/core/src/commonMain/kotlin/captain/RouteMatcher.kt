@file:Suppress("NOTHING_TO_INLINE")

package captain

import kiota.DynamicParamMatch
import kiota.ExactMatch
import kiota.SegmentMatch
import kiota.Url
import kiota.UrlMatch
import kiota.WildCardMatch
import kiota.toUrl
import kollections.Collection
import kollections.List
import kollections.associate
import kollections.dropLast
import kollections.filter
import kollections.isEmpty
import kollections.joinToString
import kollections.listOf
import kollections.map
import kollections.mapNotNull
import kollections.mapOf
import kollections.maxOf
import kollections.minByOrNull
import kollections.plus
import kollections.size
import kotlin.jvm.JvmName

internal inline fun <C> Collection<RouteConfig<C>>.matches(url: String) = matches(url.toUrl())

private fun <C> Collection<RouteConfig<C>>.matches(url: Url): List<RouteInfo<C>> {
    val options = map { it.route }
    return mapNotNull { rc ->
        val route = rc.route
        val match = url.matches(route) ?: return@mapNotNull null
        // The parent change
        RouteInfo(null, match, options, mapOf(), rc.content)
    }
}

@JvmName("routeInfoBestMatch")
fun <C> Collection<RouteInfo<C>>.bestMatch(): RouteInfo<C>? {
    if (isEmpty()) return null
    val topScore = maxOf { it.match.score }
    return filter {
        it.match.score == topScore
    }.minByOrNull {
        it.match.pattern.segments.size
    }
}

private fun Collection<RouteConfig<*>>.missingRouteMessage(route: Url): String {
    return "Failed to find matching route for ${route.path} from options " +
            joinToString(prefix = "[", separator = ",", postfix = "]") { "'${it.route.path}'" }
}

fun <C> selectRoute(parent: RouteInfo<C>?, currentRoute: Url, options: List<RouteConfig<C>>): RouteInfo<C>? {
    val base = parent?.match?.pattern ?: Url("/")
    val rebasedRoute = base.rebase(currentRoute)

    val matches = options.matches(rebasedRoute)

    val selected = matches.bestMatch()?.copy(
        matches = matches.associate { it.match.route to it.match.score }
    )

    if (selected == null) {
//        println(options.map { it.copy(base.sibling(it.route.path)) }.missingRouteMessage(currentRoute))
        println(options.missingRouteMessage(currentRoute))
        return null
    }

    val parentPattern = parent?.match?.pattern
    val childPattern = parentPattern?.sibling(selected.match.pattern.path) ?: selected.match.pattern

    val parentSegments = parent?.match?.segments?.dropLast(1) ?: listOf()
    return selected.copy(
        parent = parent,
        options = selected.options.map { parentPattern?.sibling(it.path) ?: it },
        matches = matches.associate {
            val pattern = it.match.pattern
            (parentPattern?.sibling(pattern.path) ?: pattern) to it.match.score
        },
        match = UrlMatch(currentRoute.trail(), childPattern, parentSegments + selected.match.segments)
    )
}

// TODO: Remove method Collection<SegmentMatch>.debugString()
private fun Collection<SegmentMatch>.debugString() = joinToString(prefix = "/", separator = "/") {
    when (it) {
        is DynamicParamMatch -> "D(${it.path}[${it.key}=${it.value}])"
        is ExactMatch -> "E(${it.path})"
        is WildCardMatch -> "W(${it.path})"
    }
}