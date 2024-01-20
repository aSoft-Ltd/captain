package captain

import kiota.Url

private class GenericRouteTest<T>(
    var navigator: Navigator
)

class RouterBuilder<T> {
    internal val root = RoutesBuilder<T>()
    fun routes(children: RoutesBuilder<T>.() -> Unit) {
        root.apply(children)
    }
}

class Router<T>(internal val builder: RouterBuilder<T>) {
    fun render(path: String): T {
        val options = builder.root.options
        val selected = selectRoute(null, Url(path), options)
        return selected?.content?.invoke(null) ?: error("")
    }
}

fun <T> router(navigator: Navigator, children: RouterBuilder<T>.() -> Unit): Router<T> {
    val rb = RouterBuilder<T>().apply(children)
    return Router(rb)
}