@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import kotlin.js.JsExport

class RouteConfig<out C>(
    val route: Url,
    val content: C
) {

    override fun hashCode() = route.hashCode()
    override fun equals(other: Any?) = other is RouteConfig<*> && other.route == route
    override fun toString() = route.path
}