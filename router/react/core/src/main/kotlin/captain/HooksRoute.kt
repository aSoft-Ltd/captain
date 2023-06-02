@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")

package captain

import captain.internal.RouteInfoContext
import react.ReactNode
import react.useContext

inline fun useRouteInfo(): RouteInfo<RouteContent>? = useContext(RouteInfoContext)