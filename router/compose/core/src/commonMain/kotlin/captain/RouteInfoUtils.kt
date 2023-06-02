package captain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

@PublishedApi
internal val LocalRouteInfo = compositionLocalOf<RouteInfo<RouteContent>?> { null }

@Composable
fun rememberRouteInfo(): RouteInfo<RouteContent>? = LocalRouteInfo.current