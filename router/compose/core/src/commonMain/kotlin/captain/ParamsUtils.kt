package captain

import androidx.compose.runtime.Composable
import kase.Optional
import kase.optionalOf

@Composable
fun rememberRouteParams(): RouteMatch = rememberRouteInfo()?.match ?: error("rememberRouteMatchParams can only be used inside a Route{} composable")

@Composable
fun rememberOptionalParams(key: String): Optional<String> = optionalOf(rememberRouteInfo()).flatMap { it.match.get(key) }

@Composable
fun rememberParams(key: String): String {
    val ri = rememberRouteInfo() ?: error("Param $key is not available")
    return ri.match.get(key).getOrThrow("Param $key is not available in route ${ri.route.path()}")
}