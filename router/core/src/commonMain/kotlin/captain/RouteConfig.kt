@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import kotlinx.JsExport
import kotlin.js.JsName

class RouteConfig<out C>(
    val route: Url,
    val content: C
) {
    @JsName("_ignore_RouteConfig")
    constructor(route: String, content: C) : this(Url(route), content)

    override fun hashCode() = route.hashCode()
    override fun equals(other: Any?) = other is RouteConfig<*> && other.route == route

    fun copy(route: Url = this.route) = RouteConfig(route, content)

    override fun toString() = route.path
}