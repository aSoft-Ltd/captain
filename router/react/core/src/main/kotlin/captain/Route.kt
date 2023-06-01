@file:JsExport
@file:Suppress("NonVarPropertyInExternalInterface")

package captain

import react.FC
import react.Props
import react.ReactNode

external interface RawRouteProps : Props {
    val path: String?
    val element: ReactNode?
}

external interface RouteProps : RawRouteProps {
    override var path: String
    override var element: ReactNode
}

private const val NAME = "Route"

@JsName(NAME)
val InternalRoute = FC<RouteProps>(NAME) {}