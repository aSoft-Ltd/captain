@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import cinematic.watchAsState
import react.FC
import react.PropsWithChildren
import react.useEffectOnce
import react.useState

external interface RoutesProps : PropsWithChildren

val Routes = FC<RoutesProps>("Routes") { props ->
    val (_, setMounted) = useState(false)
    useEffectOnce {
        setMounted(true)
        cleanup { setMounted(false) }
    }

    val navigator = useNavigator()
    val route = navigator.route.watchAsState()

    child(props.children)
    val matches = routeMap.entries.mapNotNull {
        val params = route.matches(it.key) ?: return@mapNotNull null
        RouteInfo(params, it.key, route, it.value)
    }

    val match = matches.firstOrNull() ?: return@FC
    val el = match.children

    RouteInfoContext.Provider(match) {
        child(el)
    }
}