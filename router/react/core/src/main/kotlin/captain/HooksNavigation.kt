@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")

package captain

import captain.internal.NavigatorContext
import react.useContext

inline fun useNavigator() = useContext(NavigatorContext) ?: throw RuntimeException("Looks like you haven't called <Router navigator={...}></Router>")

inline fun useNavigate(): NavigateFunction = NavigateFunction(useNavigator())