package captain

import kase.Optional
import kase.none

class RouteMatchParams(
    val isExact: Boolean
) {
    fun get(key: String): Optional<String> = none()
}