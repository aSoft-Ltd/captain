package captain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import cinematic.watchAsState

@Composable
fun Routes(builder: RoutesBuilder.() -> Unit) {
    val navigator = rememberNavigator()
    val parent = rememberRouteInfo()
    val options = remember { RoutesBuilder().apply(builder).options }
    val route = selectRoute(parent, navigator.route.watchAsState(), options) ?: return

    CompositionLocalProvider(LocalRouteInfo provides route) {
        route.content(route.match.params.values.toList())
    }
}