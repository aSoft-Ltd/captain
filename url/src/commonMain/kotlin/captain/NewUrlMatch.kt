@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import kollections.List
import kotlin.js.JsExport

data class NewUrlMatch(
    override val segments: List<SegmentMatch>
) : UrlMatch {
    override val route
        get() = segments.joinToString(prefix = "/", separator = "/") {
            when (it) {
                is DynamicParamMatch -> it.value
                is ExactMatch -> it.path
                is WildCardMatch -> it.path
            }
        }.toUrl()

    override val pattern
        get() = segments.joinToString(prefix = "/", separator = "/") {
            when (it) {
                is DynamicParamMatch -> "{${it.key}}"
                is ExactMatch -> it.path
                is WildCardMatch -> "*"
            }
        }.toUrl()

    override fun toString() = debugString(0)
}