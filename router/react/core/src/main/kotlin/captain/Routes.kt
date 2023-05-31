@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import cinematic.watchAsState
import kollections.toIMap
import react.Children
import react.FC
import react.PropsWithChildren
import react.asElementOrNull
import react.useMemo

val Routes = FC<PropsWithChildren>("Routes") {
    val navigator = useNavigator()
    val currentRoute = navigator.route.watchAsState()
    val parent = useRouteInfo()

    val options = useMemo(parent) {
        Children.toArray(it.children).mapNotNull { node ->
            val element = node.asElementOrNull() ?: return@mapNotNull null
            if (element.type !== Route) return@mapNotNull null
            val props = element.props.unsafeCast<RawRouteProps>()
            val path = props.path ?: return@mapNotNull null
            val route = parent?.match?.config?.sibling(path) ?: Url(path)
            RouteConfig(route, props.element)
        }
    }

    val matches = options.matches(currentRoute)
    val route = matches.bestMatch()?.copy(matches = matches.associate { it.route to it.match.score() }.toIMap()) ?: return@FC

    RouteInfoContext.Provider(route) { child(route.content) }
}