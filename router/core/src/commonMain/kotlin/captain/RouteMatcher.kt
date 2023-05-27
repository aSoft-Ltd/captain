package captain

fun Url.matches(url: Url): RouteMatchParams? {
    if (this == url) return RouteMatchParams(mapOf(), isExact = true)
    if (url.paths.size == paths.size) {
        val map = mutableMapOf<String, String>()
        for (i in paths.indices) {
            if (url.paths[i] == "*") continue
            if (url.paths[i].startsWith(":")) {
                val param = url.paths[i].substringAfter(":")
                map[param] = paths[i]
                continue
            }
            if (url.paths[i] != paths[i]) return null
        }
        return RouteMatchParams(map, isExact = false)
    }
    return null
}