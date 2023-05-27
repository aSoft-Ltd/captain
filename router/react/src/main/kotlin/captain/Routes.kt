@file:JsExport

package captain

import cinematic.useLive
import cinematic.watchAsState
import react.FC
import react.PropsWithChildren

external interface RoutesProps : PropsWithChildren {

}

val Routes = FC<RoutesProps>("Routes") { props ->
    val navigator = useNavigator()
    val route = navigator.route.watchAsState()
    child(props.children)
    val element = routeMap.entries.find { it.key.trail() == route.trail() }?.value
    child(element)
    console.log("route", route.trail())
    console.log("element", element)
//    routeMap.debug()
}