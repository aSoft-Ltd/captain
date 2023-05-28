package captain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cinematic.watchAsState

class RoutesBuilder internal constructor() {

    internal val children = mutableListOf<RouteConfig<ComposeNode>>()

    @Composable
    fun Route(path: String, content: @Composable (params: List<String>) -> Unit) {
        children.add(RouteConfig(Url(path), content))
    }
}

@Composable
fun Routes(builder: @Composable RoutesBuilder.() -> Unit) {
    val navigator = rememberNavigator()
    val currentRoute = navigator.route.watchAsState()
    val parent = rememberRouteInfo()

    val routes = RoutesBuilder().apply { builder() }.children.map { rc ->
        val route = parent?.config?.sibling(rc.route.trail()) ?: rc.route
        RouteConfig(route, rc.content)
    }

    val match = routes.bestMatch(currentRoute) ?: return

    CompositionLocalProvider(RouteInfoCompositionLocal provides match) {
        match.content(match.match.params.values.toList())
    }
}