@file:Suppress("NOTHING_TO_INLINE")

package captain

import react.ChildrenBuilder
import react.ElementType
import react.FC
import react.Key
import react.Props
import react.ReactNode
import react.create
import react.createElement

class RoutesBuilder : Props {

    override var key: Key? = undefined

    @PublishedApi
    internal val options = mutableListOf<RouteConfig<ReactNode?>>()

    inline fun Route(path: String, element: ReactNode) {
        options.add(RouteConfig(Url(path), element))
    }

    inline fun <P : Props> Route(path: String, element: ElementType<P>) {
        options.add(RouteConfig(Url(path), createElement(element)))
    }

    inline fun <P : Props> Route(path: String, element: ElementType<P>, props: P) {
        options.add(RouteConfig(Url(path), createElement(element, props)))
    }

    inline fun Route(path: String, noinline content: ChildrenBuilder.(Props) -> Unit) {
        options.add(RouteConfig(Url(path), FC(content).create()))
    }
}