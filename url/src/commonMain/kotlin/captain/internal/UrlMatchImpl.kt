package captain.internal

import captain.SegmentMatch
import captain.Url
import captain.UrlMatch
import kase.Optional
import kase.optionalOf
import kollections.List
import kollections.Map

@PublishedApi
internal data class UrlMatchImpl(
    override val route: Url,
    override val pattern: Url,
    override val segments: List<SegmentMatch>
) : UrlMatch {

    override val params: Map<String, String> by lazy { segments.toParams() }

    override fun param(key: String): Optional<String> = optionalOf(params[key])

    override fun debugString(spaces: Int) = buildString {
        val gap = indent(spaces)
        appendLine("UrlMatch(")
        appendLine("$gap${gap}route = $route")
        appendLine("$gap${gap}pattern = $pattern")
        appendLine("$gap${gap}params = ${params.pretty()}")
        appendLine("$gap${gap}score = $score")
        append("${gap})")
    }

    override val evaluatedRoute get() = segments.toEvaluatedUrl()

    override val score: Int by lazy { segments.toScore() }

    override fun printDebugString() = println(debugString(0))
    override fun toString() = debugString(0)
}