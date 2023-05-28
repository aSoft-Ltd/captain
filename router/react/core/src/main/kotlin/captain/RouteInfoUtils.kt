@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import react.ReactNode
import react.createContext
import react.useContext

internal val RouteInfoContext = createContext<RouteInfo<ReactNode?>>()

fun useRouteInfo(): RouteInfo<ReactNode?>? = useContext(RouteInfoContext)