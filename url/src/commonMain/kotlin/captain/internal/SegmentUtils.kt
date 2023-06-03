package captain.internal

import captain.DynamicParamMatch
import captain.ExactMatch
import captain.SegmentMatch
import captain.WildCardMatch
import kollections.toIMap


private fun SegmentMatch.toScore() = when (this) {
    is WildCardMatch -> 1
    is DynamicParamMatch -> 2
    is ExactMatch -> 3
}

internal fun Collection<SegmentMatch>.toScore(): Int {
    var score = 0
    forEachIndexed { idx, segment ->
        val level = idx + 1
        score += level * segment.toScore()
    }
    return score
}

internal fun Collection<SegmentMatch>.toEvaluatedUrl() = map {
    if (it is DynamicParamMatch) return@map it.value
    else it.path
}.joinToString("/")

internal fun Collection<SegmentMatch>.toParams() = filterIsInstance<DynamicParamMatch>().associate { it.key to it.value }.toIMap()