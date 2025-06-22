package captain

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cinematic.watchAsState
import kiota.Url
import kollections.emptyList
import kollections.toList
import kollections.values
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun Routes(builder: RoutesBuilder.() -> Unit) {
    val navigator = rememberNavigator()
    val parent = rememberRouteInfo()

    CompositionLocalProvider(LocalNavigateReference provides (parent?.match?.evaluatedRoute ?: Url("/"))) {
        val options = remember { RoutesBuilder().apply(builder).options }
        var previous by remember { mutableStateOf<RouteInfo<RouteContent>?>(null) }
        var current by remember { mutableStateOf<RouteInfo<RouteContent>?>(null) }
        val state = navigator.route.watchAsState()
        val route = remember(parent, state, options) {
            selectRoute(parent, state, options)
        }
        val duration = 2.seconds
        LaunchedEffect(state, route) {
            if (route == null) return@LaunchedEffect
            previous = current
            current = route
            delay(duration)
            previous = null
        }
//        val route = selectRoute(parent, navigator.route.watchAsState(), options) ?: return@CompositionLocalProvider

        if (route != null) CompositionLocalProvider(LocalRouteInfo provides route) {
            AnimatedVisibility(
                visible = previous != null,
                exit = slideOutHorizontally()
            ) {
                previous?.content(previous?.match?.params?.values?.toList() ?: emptyList())
            }

            AnimatedVisibility(
                visible = current != null,
                enter = slideInHorizontally()
            ) {
                route.content(route.match.params.values.toList())
            }
        }
    }
}