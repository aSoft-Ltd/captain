@file:Suppress("NOTHING_TO_INLINE")

package captain

inline fun NavigateFunction(navigator: Navigator, ri: RouteInfo<*>?): NavigateFunction {
    val from = ri?.evaluatedRoute ?: navigator.current()
    return NavigateFunction(navigator, from)
}