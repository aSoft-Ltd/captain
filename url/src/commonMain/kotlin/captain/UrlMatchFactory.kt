@file:Suppress("NOTHING_TO_INLINE")

package captain

import captain.internal.UrlMatchImpl
import kollections.toIList

inline fun UrlMatch(
    route: Url,
    pattern: Url,
    segments: Collection<SegmentMatch>
): UrlMatch = UrlMatchImpl(route, pattern, segments.toIList())