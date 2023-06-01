package captain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import cinematic.watchAsState
import kollections.toIMap

class RoutesBuilder internal constructor() {

    internal val children = mutableListOf<RouteConfig<ComposeNode>>()

    fun Route(path: String, content: @Composable (params: List<String>) -> Unit) {
        children.add(RouteConfig(Url(path), content))
    }
}

@Composable
fun Routes(builder: RoutesBuilder.() -> Unit) {
    val navigator = rememberNavigator()
    val currentRoute = navigator.route.watchAsState()
    val parent = rememberRouteInfo()
    val options = remember(parent) {
        RoutesBuilder().apply(builder).children.map { rc ->
            val route = parent?.match?.pattern?.sibling(rc.route.path) ?: rc.route
            RouteConfig(route, rc.content)
        }
    }

    val matches = options.matches(currentRoute)
    val match = options.bestMatch(currentRoute)?.copy(matches = matches.associate { it.route to it.match.score() }.toIMap()) ?: return

    CompositionLocalProvider(RouteInfoCompositionLocal provides match) {
        match.content(match.match.params.values.toList())
    }
}