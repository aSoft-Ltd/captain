@file:JsExport

package captain

import kollections.JsExport

sealed interface SegmentMatch {
    val path: String
}

data class WildCardMatch(override val path: String) : SegmentMatch
data class DynamicParamMatch(
    override val path: String,
    val key: String,
    val value: String
) : SegmentMatch

data class ExactMatch(override val path: String) : SegmentMatch