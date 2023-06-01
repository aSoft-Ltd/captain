@file:OptIn(ExperimentalJsExport::class)
@file:Suppress("NonVarPropertyInExternalInterface")

package captain

import js.core.push
import react.ChildrenBuilder
import react.ElementType
import react.FC
import react.Props
import react.ReactNode
import react.create
import react.createElement

external interface RawRouteProps : Props {
    val path: String?
    val element: ReactNode?
}

@JsExport
external interface RouteProps : RawRouteProps {
    override var path: String
    override var element: ReactNode
}

private const val NAME = "Route"

@JsExport
@JsName(NAME)
val InternalRoute = FC<RouteProps>(NAME) {}

inline fun RoutesBuilder.Route(path: String, element: ReactNode) {
    options.push(RouteConfig(Url(path), element))
}

inline fun <P : Props> RoutesBuilder.Route(path: String, element: ElementType<P>) {
    options.push(RouteConfig(Url(path), createElement(element)))
}

inline fun <P : Props> RoutesBuilder.Route(path: String, element: ElementType<P>, noinline block: P.() -> Unit) {
    options.push(RouteConfig(Url(path), element.create(block)))
}

inline fun RoutesBuilder.Route(path: String, noinline content: ChildrenBuilder.(Props) -> Unit) {
    options.push(RouteConfig(Url(path), FC(content).create()))
}