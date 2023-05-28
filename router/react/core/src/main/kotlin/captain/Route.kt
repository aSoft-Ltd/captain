@file:JsExport
@file:Suppress("NOTHING_TO_INLINE", "NON_EXPORTABLE_TYPE")

package captain

import react.FC
import react.Props
import react.ReactNode

external interface RouteProps : Props {
    var path: String
    var element: ReactNode
}

val Route = FC<RouteProps>("Route") {}