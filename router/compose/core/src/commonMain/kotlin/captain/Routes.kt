package captain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import cinematic.watchAsState
import kollections.toIMap

@Composable
fun Routes(builder: RoutesBuilder.() -> Unit) {
    val navigator = rememberNavigator()
    val currentRoute = navigator.route.watchAsState()
    val parent = rememberRouteInfo()
    val options = remember(parent) {
        RoutesBuilder().apply(builder).options.map { rc ->
            val route = parent?.match?.pattern?.sibling(rc.route.path) ?: rc.route
            RouteConfig(route, rc.content)
        }
    }

    val matches = options.matches(currentRoute)
    val selected = options.bestMatch(currentRoute)?.copy(matches = matches.associate { it.route to it.match.score() }.toIMap()) ?: return

    CompositionLocalProvider(LocalRouteInfo provides selected) {
        selected.content(selected.match.params.values.toList())
    }
}