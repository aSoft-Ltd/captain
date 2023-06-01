@file:JsExport
@file:Suppress("NOTHING_TO_INLINE")

package captain

import captain.internal.Config
import captain.internal.Routes
import cinematic.watchAsState
import react.Children
import react.FC
import react.PropsWithChildren
import react.asElementOrNull
import react.useMemo

private const val NAME = "Routes"

@JsName(NAME)
val InternalRoutes = FC<PropsWithChildren>(NAME) {
    val parent = useRouteInfo()
    val navigator = useNavigator()
    val options = useMemo(parent) {
        Children.toArray(it.children).mapNotNull { node ->
            val element = node.asElementOrNull() ?: return@mapNotNull null
            if (element.type !== InternalRoute) return@mapNotNull null
            val props = element.props.unsafeCast<RawRouteProps>()
            val path = props.path ?: return@mapNotNull null
            Config(parent, path, element)
        }
    }
    Routes(navigator.route.watchAsState(), options)
}