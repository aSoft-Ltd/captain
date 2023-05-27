@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import cinematic.watchAsState
import react.FC
import react.PropsWithChildren
import react.useEffectOnce
import react.useState
import web.console.console

external interface RoutesProps : PropsWithChildren

val Routes = FC<RoutesProps>("Routes") { props ->
    val (mounted, setMounted) = useState(false)
    useEffectOnce {
        setMounted(true)
        cleanup { setMounted(false) }
    }

    val navigator = useNavigator()
    val route = navigator.route.watchAsState()

    child(props.children)
    val matches = routeMap.entries.filter { route.matches(it.key) != null }
    val el = matches.firstOrNull()?.value

    if (mounted) {
        console.clear()
        console.log("route  :", "'${route.trail()}'")
        console.log("routes :", routeMap.debugArray())
        console.log("element:", el)
    }

    child(el)
}