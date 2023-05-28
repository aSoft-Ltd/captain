package captain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

internal val RouteInfoCompositionLocal = compositionLocalOf<RouteInfo<ComposeNode>?> { null }

@Composable
fun rememberRouteInfo(): RouteInfo<ComposeNode>? = RouteInfoCompositionLocal.current