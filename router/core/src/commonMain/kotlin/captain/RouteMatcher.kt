package captain

fun Url.matches(url: Url): RouteMatchParams? {
    if (this == url) return RouteMatchParams(isExact = true)
    if (url.paths.size == paths.size) {
        for (i in paths.indices) {
            if (url.paths[i] == "*") continue
            if (url.paths[i] != paths[i]) return null
        }
        return RouteMatchParams(isExact = false)
    }
    return null
}