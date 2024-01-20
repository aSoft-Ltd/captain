@file:Suppress("NOTHING_TO_INLINE")

package captain

import androidx.compose.runtime.Composable
import kiota.Url
import kollections.List
import kollections.add
import kollections.mutableListOf

class RoutesBuilder internal constructor() {

    @PublishedApi
    internal val options = mutableListOf<RouteConfig<RouteContent>>()
}

inline fun RoutesBuilder.Route(path: String, noinline content: @Composable (params: List<String>) -> Unit) {
    options.add(RouteConfig(Url(path), content))
}