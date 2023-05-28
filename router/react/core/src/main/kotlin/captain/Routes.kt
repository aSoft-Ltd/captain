@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import cinematic.watchAsState
import react.Children
import react.FC
import react.PropsWithChildren
import react.asElementOrNull

val Routes = FC<PropsWithChildren>("Routes") {
    val navigator = useNavigator()
    val currentRoute = navigator.route.watchAsState()
    val parent = useRouteInfo()

    val routes = Children.toArray(it.children).mapNotNull { node ->
        val element = node.asElementOrNull() ?: return@mapNotNull null
        if (element.type !== Route) return@mapNotNull null
        val props = element.props.unsafeCast<RouteProps>()
        val path = props.path ?: return@mapNotNull null // Do not delete this, someone can choose not to set it from Javascript
        val route = parent?.config?.child(path) ?: Url(path)
        RouteConfig(route, props.element)
    }

    val matches = routes.mapNotNull { rc ->
        val route = rc.route
        val params = currentRoute.matches(route) ?: return@mapNotNull null
        RouteInfo(params, route, currentRoute, rc.content)
    }

    console.log("routes", routes.map { it.route.toString() }.toTypedArray())
    console.log("matches", matches.map { it.route.toString() }.toTypedArray())

    val match = matches.firstOrNull() ?: return@FC
    val element = match.content

    RouteInfoContext.Provider(match) { child(element) }
}