@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")

package captain

import captain.internal.NavigatorContext
import react.useContext

inline fun useNavigator() = useContext(NavigatorContext) ?: throw RuntimeException(
    """
    You are trying to call useNavigator() on component that is not a child of a <Router>
    Make sure that you attempt to call a navigator only if one of it's ancestors is a <Router>
    """.trimIndent()
)

fun useNavigate(): NavigateFunction {
    val navigator = useNavigator()
    val ri = useRouteInfo()
    return NavigateFunction(useNavigator(), ri?.match?.route ?: navigator.current())
}