@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import kase.Optional
import kollections.List
import kollections.Map
import kotlin.js.JsExport

interface UrlMatch {
    val segments: List<SegmentMatch>
    val route: Url
    val pattern: Url
    val score: Int
    val params: Map<String, String>

    fun param(key: String): Optional<String>

    fun debugString(spaces: Int = 0): String

    val evaluatedRoute: Url

    fun printDebugString() = println(debugString(0))
}