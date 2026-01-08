@file:JsExport
@file:Suppress("UNUSED_PARAMETER", "NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")

package captain

import js.objects.Record
import js.objects.jso
import kase.Optional
import kase.optionalOf

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