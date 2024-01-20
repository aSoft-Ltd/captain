package captain

import kiota.Url
import kollections.add
import kollections.mutableListOf

class RoutesBuilder<T> {
    internal val options = mutableListOf<RouteConfig<RouteContent<T>>>()
    fun route(path: String, content: (RouteInfo<T>?) -> T) {
        options.add(RouteConfig(Url(path), content))
    }
}