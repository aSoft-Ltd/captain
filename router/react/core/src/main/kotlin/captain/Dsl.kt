@file:Suppress("NOTHING_TO_INLINE")

package captain

import captain.internal.Config
import captain.internal.Routes
import cinematic.watchAsState
import react.ChildrenBuilder
import react.FC
import react.createElement


@PublishedApi
internal val RoutesDsl = FC<RoutesBuilder> {props->
    val parent = useRouteInfo()
    val navigator = useNavigator()
    val options = props.options.map { Config(parent, it.route.path, it.content) }
    Routes(navigator.route.watchAsState(), options)
}

inline fun ChildrenBuilder.Routes(noinline builder: RoutesBuilder.() -> Unit) {
    child(createElement(RoutesDsl, RoutesBuilder().apply(builder)))
}

inline fun ChildrenBuilder.Router(
    navigator: Navigator? = null,
    noinline builder: ChildrenBuilder.() -> Unit
) {
    InternalRouter {
        this.navigator = navigator
        this.builder()
    }
}