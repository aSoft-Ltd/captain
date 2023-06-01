@file:Suppress("NOTHING_TO_INLINE")
@file:OptIn(ExperimentalJsExport::class)

package captain

import captain.internal.RouteInfoContext
import cinematic.watchAsState
import js.core.jso
import kollections.toIMap
import react.Children
import react.ChildrenBuilder
import react.FC
import react.Fragment
import react.PropsWithChildren
import react.ReactElement
import react.ReactNode
import react.asElementOrNull
import react.createElement
import react.useMemo

private const val NAME = "Routes"

// only for react.js consumers (Not for kotlin-react consumers)
@JsExport
@JsName(NAME)
val InternalRoutes = FC<PropsWithChildren>(NAME) { props ->
    val parent = useRouteInfo()
    val navigator = useNavigator()
    val options = useMemo(parent) {
        Children.toArray(props.children).flatMap { it.toRouteConfig(parent) }
    }
    Routes(navigator.route.watchAsState(), options)
}

// only for kotlin-react consumers. (Not for react.js consumers)
@PublishedApi
internal val RoutesDsl = FC<RoutesBuilder>(NAME) { props ->
    val parent = useRouteInfo()
    val navigator = useNavigator()
    val options = props.options.map { Config(parent, it.route.path, it.content) }
    Routes(navigator.route.watchAsState(), options)
}

// only for kotlin-react consumers. (Not for react.js consumers)
inline fun ChildrenBuilder.Routes(noinline builder: RoutesBuilder.() -> Unit) {
    val props = jso<RoutesBuilder> {
        options = arrayOf()
        builder()
    }
    child(createElement(RoutesDsl, props))
}

private inline fun ChildrenBuilder.Routes(currentRoute: Url, options: List<RouteConfig<ReactNode?>>) {
    val matches = options.matches(currentRoute)
    val route = matches.bestMatch()?.copy(matches = matches.associate { it.route to it.match.score() }.toIMap())
    if (route == null) {
        console.warn("Failed to find matching route for ${currentRoute.path} from ", options.map { it.route.path }.toTypedArray())
        return
    }
    RouteInfoContext.Provider(route) { child(route.content) }
}

private inline fun Config(
    parent: RouteInfo<ReactNode?>?,
    path: String,
    element: ReactNode?
) = RouteConfig(parent?.match?.pattern?.sibling(path) ?: Url(path), element)

private fun ReactNode.toRouteConfig(parent: RouteInfo<ReactNode?>?): List<RouteConfig<ReactNode?>> {
    val element = asElementOrNull() ?: return listOf()
    if (element.type == Fragment) {
        val elements = mutableListOf<RouteConfig<ReactNode?>>()
        val props = element.props.unsafeCast<PropsWithChildren>()
        Children.toArray(props.children).forEach {
            elements.addAll(it.toRouteConfig(parent))
        }
        return elements
    }

    if (element.type != InternalRoute) {
        console.error("Only <Route> components are allowed in <$NAME>")
        return emptyList()
    }

    val el = element.unsafeCast<ReactElement<RawRouteProps>>()

    if (el.props.path == null) {
        console.error("<Route> component is missing prop 'path'")
        return emptyList()
    }

    val props = el.props.unsafeCast<RouteProps>()

    return listOf(Config(parent, props.path, el))
}

