@file:JsExport
@file:Suppress("NOTHING_TO_INLINE", "NON_EXPORTABLE_TYPE")

package captain

import react.FC
import react.PropsWithChildren
import react.ReactNode
import react.useEffectOnce
import web.console.console

external interface RouteProps : PropsWithChildren {
    var path: String
}

internal val routeMap = mutableMapOf<Url, ReactNode?>()

fun Map<Url, *>.debugArray() = keys.map { it.toString() }.toTypedArray()
fun Map<Url, *>.debug() = console.log(debugArray())

val Route = FC<RouteProps>("Route") { props ->
    useEffectOnce {
        val key = Url(props.path)
        routeMap[key] = props.children
        cleanup { routeMap.remove(key) }
    }
}