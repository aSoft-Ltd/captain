package captain

import kollections.MutableList
import react.Props
import react.ReactNode

external interface RoutesBuilder : Props {
    var options: MutableList<RouteConfig<ReactNode?>>
}