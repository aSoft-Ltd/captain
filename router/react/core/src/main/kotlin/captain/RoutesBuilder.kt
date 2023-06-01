package captain

import js.core.push
import react.ChildrenBuilder
import react.ElementType
import react.FC
import react.Props
import react.ReactNode
import react.create
import react.createElement

external interface RoutesBuilder : Props {

    var options: Array<RouteConfig<ReactNode?>>
}

inline fun RoutesBuilder.Route(path: String, element: ReactNode) {
    options.push(RouteConfig(Url(path), element))
}

inline fun <P : Props> RoutesBuilder.Route(path: String, element: ElementType<P>) {
    options.push(RouteConfig(Url(path), createElement(element)))
}

//inline fun <P : Props> RoutesBuilder.Route(path: String, element: ElementType<P>, props: P) {
//    options.push(RouteConfig(Url(path), createElement(element, props)))
//}

inline fun <P : Props> RoutesBuilder.Route(path: String, element: ElementType<P>, noinline block: P.() -> Unit) {
    options.push(RouteConfig(Url(path), element.create(block)))
}

inline fun RoutesBuilder.Route(path: String, noinline content: ChildrenBuilder.(Props) -> Unit) {
    options.push(RouteConfig(Url(path), FC(content).create()))
}