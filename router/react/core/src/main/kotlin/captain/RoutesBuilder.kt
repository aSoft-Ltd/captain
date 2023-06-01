package captain

import react.Props
import react.ReactNode

external interface RoutesBuilder : Props {
    var options: Array<RouteConfig<ReactNode?>>
}