@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import captain.internal.indent
import captain.internal.pretty
import kase.Optional
import kase.optionalOf
import kollections.Map
import kollections.List
import kollections.toIMap
import kotlin.js.JsExport

interface UrlMatch {
    val segments: List<SegmentMatch>
    val route: Url
    val pattern: Url

    val params: Map<String, String> get() = segments.filterIsInstance<DynamicParamMatch>().associate { it.key to it.value }.toIMap()

    fun get(key: String): Optional<String> = optionalOf(params[key])

    fun debugString(spaces: Int = 0) = buildString {
        val gap = indent(spaces)
        appendLine("UrlMatch(")
        appendLine("$gap${gap}route = $route")
        appendLine("$gap${gap}pattern = $pattern")
        appendLine("$gap${gap}params = ${params.pretty()}")
        appendLine("$gap${gap}score = ${score()}")
        append("${gap})")
    }

    val evaluatedRoute
        get() = segments.map {
            if (it is DynamicParamMatch) return@map it.value
            else it.path
        }.joinToString("/")

    private fun score(match: SegmentMatch) = when (match) {
        is WildCardMatch -> 1
        is DynamicParamMatch -> 2
        is ExactMatch -> 3
    }

    fun score(): Int {
        var score = 0
        segments.forEachIndexed { idx, path ->
            val level = idx + 1
            score += level * score(path)
        }
        return score
    }

    fun printDebugString() = println(debugString(0))
}