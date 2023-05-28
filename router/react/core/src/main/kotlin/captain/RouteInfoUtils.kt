@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import react.ReactNode
import react.createContext
import react.useContext

internal val RouteInfoContext = createContext<RouteInfo<ReactNode?>>()

// consider removing the error on the right and place it inside useRouteParams
fun useRouteInfo(): RouteInfo<ReactNode?>? = useContext(RouteInfoContext) // ?: error("Looks like you have a <Route> component outside of <Routes>")