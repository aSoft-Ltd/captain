@file:Suppress("NOTHING_TO_INLINE")

package captain

import androidx.compose.runtime.Composable
import kiota.Url

@RoutingDsl
class RoutesBuilder internal constructor() {

    @PublishedApi
    internal val options = mutableListOf<RouteConfig<RouteContent>>()
    internal val contents = mutableListOf<@Composable () -> Unit>()

    /**
     * To be used as a way to group routes together.
     *
     * Common use case is to group routes that are behind an authentication wall
     *
     * If you need to do nested routes, use [Routes] instead
     */
    fun Group(content: @Composable () -> Unit) = contents.add(content)

    @Composable
    fun Nested(content: @Composable RoutesBuilder.() -> Unit) {
        val builder = RoutesBuilder().apply { content() }
        options.addAll(builder.options)
    }
}

@RoutingDsl
inline fun RoutesBuilder.Route(path: String, noinline content: @Composable RouteContent.(params: List<String>) -> Unit) {
    options.add(RouteConfig(Url(path), RouteContent(render = content)))
}