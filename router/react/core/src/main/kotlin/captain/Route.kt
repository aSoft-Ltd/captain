@file:JsExport
@file:Suppress("NOTHING_TO_INLINE", "NON_EXPORTABLE_TYPE", "NonVarPropertyInExternalInterface")

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

val Route = FC<RouteProps>("Route") {}