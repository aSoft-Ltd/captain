@file:OptIn(ExperimentalJsExport::class)
@file:Suppress("NonVarPropertyInExternalInterface")

package captain

import kiota.Url
import react.ChildrenBuilder
import react.ElementType
import react.FC
import react.Props
import react.ReactNode
import react.create
import react.createElement
import kollections.add
@JsExport
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
    options.add(RouteConfig(Url(path), element))
}

inline fun <P : Props> RoutesBuilder.Route(path: String, element: ElementType<P>) {
    options.add(RouteConfig(Url(path), createElement(element)))
}

inline fun <P : Props> RoutesBuilder.Route(path: String, element: ElementType<P>, noinline block: P.() -> Unit) {
    options.add(RouteConfig(Url(path), element.create(block)))
}

inline fun RoutesBuilder.Route(path: String, noinline content: ChildrenBuilder.(Props) -> Unit) {
    options.add(RouteConfig(Url(path), FC(content).create()))
}