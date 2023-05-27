@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import react.createContext
import react.useContext

internal val NavigatorContext = createContext<Navigator>()

fun useNavigator(): Navigator {
    return useContext(NavigatorContext) ?: throw RuntimeException("Looks like you haven't called <Router navigator={...}></Router>")
}