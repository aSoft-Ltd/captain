@file:Suppress("NOTHING_TO_INLINE")

package captain

import androidx.compose.runtime.Composable
import kase.Optional
import kase.optionalOf

@Composable
inline fun rememberRouteParams(): UrlMatch = rememberRouteInfo()?.match ?: error("rememberRouteMatchParams can only be used inside a Route{} composable")

@Composable
inline fun rememberOptionalParams(key: String): Optional<String> = optionalOf(rememberRouteInfo()).flatMap { it.match.get(key) }

@Composable
inline fun rememberParams(key: String): String {
    val ri = rememberRouteInfo() ?: error("Param $key is not available")
    return ri.match.get(key).getOrThrow("Param $key is not available at ${ri.route.path}")
}