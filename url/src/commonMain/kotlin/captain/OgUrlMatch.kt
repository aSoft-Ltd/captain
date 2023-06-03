@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import kotlin.js.JsExport
import kollections.List

data class OgUrlMatch(
    override val route: Url,
    override val pattern: Url,
    override val segments: List<SegmentMatch>
) : UrlMatch {
    override fun toString() = debugString(0)
}