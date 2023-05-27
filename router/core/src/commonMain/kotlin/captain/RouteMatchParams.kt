package captain

import kase.Optional
import kase.optionalOf

class RouteMatchParams(
    val map: Map<String,String>,
    val isExact: Boolean
) {
    fun get(key: String): Optional<String> = optionalOf(map[key])
}