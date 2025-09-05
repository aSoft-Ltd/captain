package captain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cinematic.watchAsState
import kiota.Url
import kollections.iterator
import kollections.toList
import kollections.values
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

/**
 * This is the main routing component. It must be placed inside a [Router] and it will render the
 * appropriate [RouteContent] based on the current URL in the [Navigator].
 *
 * It should be used to Render top level and nested routes of the application
 *
 * It should not be confused with [RoutesBuilder.Group] whose sole purpose is to group routes
 * together without affecting the URL matching.
 */
@Composable
fun Routes(builder: RoutesBuilder.() -> Unit) {
    val navigator = rememberNavigator()
    val parent = rememberRouteInfo()

    CompositionLocalProvider(LocalNavigateReference provides (parent?.match?.evaluatedRoute ?: Url("/"))) {
        val b = remember { RoutesBuilder().apply(builder) }
        val options = b.options
        val contents = b.contents
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