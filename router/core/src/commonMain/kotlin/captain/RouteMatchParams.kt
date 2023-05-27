@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import kase.Optional
import kase.optionalOf
import kotlin.js.JsExport

data class RouteMatchParams(
    val params: Map<String, String>,
    val isExact: Boolean
) {
    fun get(key: String): Optional<String> = optionalOf(params[key])
}