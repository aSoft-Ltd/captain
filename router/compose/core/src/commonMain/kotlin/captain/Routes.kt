package captain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import cinematic.watchAsState
import kiota.Url
import kollections.iterator
import kollections.toList
import kollections.values

/**
 * This is the main routing component. It must be placed inside a [Router] and it will render the
 * appropriate [RouteContent] based on the current URL in the [Navigator].
 *
 * It should be used to Render top level and nested routes of the application
 *
 * It should not be confused with [RoutesBuilder.Group] whose sole purpose is to group routes
 * together without affecting the URL matching.
 */
@RoutingDsl
@Composable
fun Routes(builder: RoutesBuilder.() -> Unit) {
    val navigator = rememberNavigator()
    val parent = rememberRouteInfo()

    CompositionLocalProvider(LocalNavigateReference provides (parent?.match?.evaluatedRoute ?: Url("/"))) {
        val b = remember { RoutesBuilder().apply(builder) }
//        val b = RoutesBuilder().apply { builder() }
        val options = remember(builder) { b.options }
        val contents = remember(builder) { b.contents }
        val state = navigator.route.watchAsState()
        val route = remember(parent, state, options) {
            selectRoute(parent, state, options)?.also {
                it.content.state = navigator.state()
            }
        }

        for (c in contents) c()

        if (route != null) CompositionLocalProvider(LocalRouteInfo provides route) {
            route.content.render(route.content, route.match.params.values.toList())
        }
    }
}