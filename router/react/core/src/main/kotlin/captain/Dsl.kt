@file:Suppress("NOTHING_TO_INLINE")

package captain

import captain.internal.Config
import captain.internal.Routes
import cinematic.watchAsState
import react.ChildrenBuilder
import react.ElementType
import react.FC
import react.Props
import react.ReactNode
import react.create
import react.createElement

class RoutesBuilder(@PublishedApi internal val parent: RouteInfo<ReactNode?>?) {
    @PublishedApi
    internal val options = mutableListOf<RouteConfig<ReactNode?>>()

    inline fun Route(path: String, element: ReactNode) {
        options.add(Config(parent, path, element))
    }

    inline fun <P : Props> Route(path: String, element: ElementType<P>) {
        options.add(Config(parent, path, createElement(element)))
    }

    inline fun <P : Props> Route(path: String, element: ElementType<P>, props: P) {
        options.add(Config(parent, path, createElement(element, props)))
    }

    inline fun Route(path: String, noinline content: ChildrenBuilder.(Props) -> Unit) {
        options.add(Config(parent, path, FC(content).create()))
    }
}

inline fun ChildrenBuilder.Routes(noinline builder: RoutesBuilder.() -> Unit) = InternalRoutes {
    val navigator = useNavigator()
    val parent = useRouteInfo()
    Routes(navigator.route.watchAsState(), RoutesBuilder(parent).apply(builder).options)
}

inline fun ChildrenBuilder.Router(
    navigator: Navigator = BrowserNavigator(syncWithAddressBar = true),
    noinline builder: ChildrenBuilder.() -> Unit
) = InternalRouter {
    this.navigator = navigator
    builder()
}