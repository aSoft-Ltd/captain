@file:JsExport
@file:Suppress("UNUSED_PARAMETER", "NON_EXPORTABLE_TYPE")

package captain

import js.core.Record
import js.core.jso
import kase.Optional
import kollections.toIList
import kollections.Map
import kollections.toIMap

external interface ParamsBag : Record<String, String> {
    var all: Map<String, String>
}

fun useRouteParams(): UrlMatch = useRouteInfo()?.match ?: error("useRouteParams can only be used inside a <Route> component")

fun useOptionalParam(key: String): Optional<String> {
    val params = useRouteParams()
    return params.get(key)
}

fun useParam(key: String): String {
    val params = useOptionalParam(key)
    return params.getOrThrow()
}

fun useParams(): ParamsBag {
    val ri = useRouteInfo()
    val params = ri?.match?.params ?: mapOf()
    val pb = jso<ParamsBag>()
    params.values.toIList()
    pb.all = params.toIMap()
    for ((key, value) in params) pb[key] = value
    return pb
}

inline operator fun ParamsBag.component1(): String = asDynamic()["all"].unsafeCast<Map<String, String>>().values.toList().component1()
inline operator fun ParamsBag.component2(): String = asDynamic()["all"].unsafeCast<Map<String, String>>().values.toList().component2()
inline operator fun ParamsBag.component3(): String = asDynamic()["all"].unsafeCast<Map<String, String>>().values.toList().component3()
inline operator fun ParamsBag.component4(): String = asDynamic()["all"].unsafeCast<Map<String, String>>().values.toList().component4()
inline operator fun ParamsBag.component5(): String = asDynamic()["all"].unsafeCast<Map<String, String>>().values.toList().component5()