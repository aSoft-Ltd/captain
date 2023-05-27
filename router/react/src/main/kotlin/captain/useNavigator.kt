@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import react.useContext

fun useNavigator(): Navigator {
    return useContext(NavigatorContext) ?: throw RuntimeException("Looks like you haven't called <Router navigator={...}></Router>")
}

