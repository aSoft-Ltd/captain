@file:JsExport
@file:Suppress("UNUSED_PARAMETER", "NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")

package captain

import js.core.Record
import js.core.jso
import kase.Optional
import kase.optionalOf
import kollections.Map
import kollections.mapOf
import kollections.component1
import kollections.component2
import kollections.component3
import kollections.component4
import kollections.component5
import kollections.component6
import kollections.entries
import kollections.iterator
import kollections.toList
import kollections.values

external interface Params : Record<String, String> {
    var all: Map<String, String>
}

inline fun useOptionalParam(key: String): Optional<String> = optionalOf(useRouteInfo()).flatMap { it.match.param(key) }

inline fun useParam(key: String): String = useOptionalParam(key).getOrThrow()

fun useParams(): Params {
    val ri = useRouteInfo()
    val params = ri?.match?.params ?: mapOf()
    val pb = jso<Params>()
    pb.all = params
    for ((key, value) in params.entries) pb[key] = value
    return pb
}

fun <T : Params> useParamsOf(): T = useParams().unsafeCast<T>()

inline operator fun Params.component1(): String = all.values.toList().component1()
inline operator fun Params.component2(): String = all.values.toList().component2()
inline operator fun Params.component3(): String = all.values.toList().component3()
inline operator fun Params.component4(): String = all.values.toList().component4()
inline operator fun Params.component5(): String = all.values.toList().component5()
inline operator fun Params.component6(): String = all.values.toList().component6()