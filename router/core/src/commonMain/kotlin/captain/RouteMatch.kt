@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import kase.Optional
import kase.optionalOf
import kotlin.js.JsExport

data class RouteMatch(
    val url: Url,
    val segments: List<SegmentMatch>
) {
    val params: Map<String, String> = segments.filterIsInstance<DynamicParamMatch>().associate { it.key to it.value }

    fun get(key: String): Optional<String> = optionalOf(params[key])

    fun debugString(spaces: Int = 0) = buildString {
        val gap = indent(spaces)
        appendLine("RouteMatch(")
        appendLine("$gap${gap}url = $url")
        appendLine("$gap${gap}params = $params")
        appendLine("$gap${gap}score = ${score()}")
        append("${gap})")
    }

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

    override fun toString() = debugString(0)
}