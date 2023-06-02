@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import kotlin.js.JsExport
import kotlin.js.JsName

class RouteConfig<out C>(
    val route: Url,
    val content: C
) {
    @JsName("_ignore_RouteConfig")
    constructor(route: String, content: C) : this(Url(route), content)

    override fun hashCode() = route.hashCode()
    override fun equals(other: Any?) = other is RouteConfig<*> && other.route == route

    @JsName("rebaseUrl")
    fun rebase(url: Url): Url {
        TODO()
    }

    fun copy(route: Url = this.route) = RouteConfig(route, content)

    fun rebase(url: String) = rebase(Url(url))
    override fun toString() = route.path
}