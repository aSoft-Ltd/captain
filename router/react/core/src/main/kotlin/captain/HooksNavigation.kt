@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")

package captain

import captain.internal.NavigateReferenceContext
import captain.internal.NavigatorContext
import react.useContext

inline fun useNavigator() = useContext(NavigatorContext) ?: throw RuntimeException(
    """
    You are trying to call useNavigator() on component that is not a child of a <Router>
    Make sure that you attempt to call a navigator only if one of it's ancestors is a <Router>
    """.trimIndent()
)

inline fun useNavigate(): NavigateFunction {
    val navigator = useNavigator()
    return NavigateFunction(useNavigator(), useContext(NavigateReferenceContext) ?: navigator.current())
}