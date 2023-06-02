@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")

package captain

import captain.internal.NavigatorContext
import react.useContext

inline fun useNavigator() = useContext(NavigatorContext)
    ?: throw RuntimeException("Looks like you haven't called <Router navigator={...}></Router>")

fun useNavigate(): NavigateFunction {
    val navigator = useNavigator()
    val ri = useRouteInfo()
    return NavigateFunction(useNavigator(), ri?.route ?: navigator.current())
}