@file:Suppress("NOTHING_TO_INLINE")

package captain.internal

import captain.RouteConfig
import captain.RouteInfo
import captain.RouteInfoContext
import captain.Url
import captain.bestMatch
import captain.matches
import kollections.toIMap
import react.ChildrenBuilder
import react.ReactNode

@PublishedApi
internal inline fun ChildrenBuilder.Routes(currentRoute: Url, options: List<RouteConfig<ReactNode?>>) {
    val matches = options.matches(currentRoute)
    val route = matches.bestMatch()?.copy(matches = matches.associate { it.route to it.match.score() }.toIMap()) ?: return
    RouteInfoContext.Provider(route) { child(route.content) }
}

@PublishedApi
internal inline fun Config(
    parent: RouteInfo<ReactNode?>?,
    path: String,
    element: ReactNode?
) = RouteConfig(parent?.match?.config?.sibling(path) ?: Url(path), element)