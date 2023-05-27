@file:JsExport
@file:Suppress("NOTHING_TO_INLINE", "NON_EXPORTABLE_TYPE")

package captain

import react.FC
import react.PropsWithChildren
import react.ReactNode
import web.console.console

external interface RouteProps : PropsWithChildren {
    var path: String
}

internal val routeMap = mutableMapOf<Url, ReactNode?>()
fun Map<Url, *>.debug() {
    console.log(keys.map { it.toString() }.toTypedArray())
}

val Route = FC<RouteProps>("Route") { props ->
    routeMap[Url(props.path)] = props.children
    routeMap.debug()
}