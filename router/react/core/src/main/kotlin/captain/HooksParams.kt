@file:JsExport
@file:Suppress("UNUSED_PARAMETER", "NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")

package captain

import js.core.Record
import js.core.jso
import kase.Optional
import kase.optionalOf
import kollections.toIList
import kollections.Map
import kollections.toIMap

external interface Params : Record<String, String> {
    var all: Map<String, String>
}

inline fun useOptionalParam(key: String): Optional<String> = optionalOf(useRouteInfo()).flatMap { it.match.param(key) }

inline fun useParam(key: String): String = useOptionalParam(key).getOrThrow()

fun useParams(): Params {
    val ri = useRouteInfo()
    val params = ri?.match?.params ?: mapOf()
    val pb = jso<Params>()
    params.values.toIList()
    pb.all = params.toIMap()
    for ((key, value) in params) pb[key] = value
    return pb
}

fun <T : Params> useParamsOf(): T = useParams().unsafeCast<T>()

inline operator fun Params.component1(): String = asDynamic()["all"].unsafeCast<Map<String, String>>().values.toList().component1()
inline operator fun Params.component2(): String = asDynamic()["all"].unsafeCast<Map<String, String>>().values.toList().component2()
inline operator fun Params.component3(): String = asDynamic()["all"].unsafeCast<Map<String, String>>().values.toList().component3()
inline operator fun Params.component4(): String = asDynamic()["all"].unsafeCast<Map<String, String>>().values.toList().component4()
inline operator fun Params.component5(): String = asDynamic()["all"].unsafeCast<Map<String, String>>().values.toList().component5()