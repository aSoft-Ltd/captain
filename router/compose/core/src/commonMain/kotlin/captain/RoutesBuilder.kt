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
    internal val contents = mutableListOf<@Composable () -> Unit>()

    fun Routes(content: @Composable () -> Unit) = contents.add(content)
}

inline fun RoutesBuilder.Route(path: String, noinline content: @Composable RouteContent.(params: List<String>) -> Unit) {
    options.add(RouteConfig(Url(path), RouteContent(render = content)))
}