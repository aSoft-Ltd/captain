@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import captain.internal.indent
import kollections.List
import kollections.Map
import kotlin.js.JsExport

data class RouteInfo<out C>(
    val parent: RouteInfo<C>?,
    val match: UrlMatch,
    val options: List<Url>,
    val matches: Map<Url, Int>,
    val content: C
) {

    fun debugString() = buildString {
        val spaces = 2
        val gap = indent(spaces)
        appendLine("RouteInfo(")
        appendLine("${gap}options = $options")
        appendLine("${gap}matches = $matches")
        appendLine("${gap}match = ${match.debugString(spaces)}")
        appendLine(")")
    }

//    val evaluatedRoute: Url get() = parent?.evaluatedRoute?.sibling(match.evaluatedRoute) ?: Url("/").child(match.evaluatedRoute)
    val evaluatedRoute: Url get() = match.evaluatedRoute
    fun printDebugString() = println(debugString())

    override fun toString() = debugString()
}