@file:Suppress("NOTHING_TO_INLINE")
@file:OptIn(ExperimentalJsExport::class)

package captain

import captain.internal.NavigateReferenceContext
import captain.internal.RouteInfoContext
import cinematic.watchAsState
import js.objects.jso
import react.Children
import react.ChildrenBuilder
import react.FC
import react.Fragment
import react.Props
import react.PropsWithChildren
import react.ReactElement
import react.ReactNode
import react.asElementOrNull
import react.createElement
import react.useMemo
import kollections.List
import kollections.addAll
import kollections.listOf
import kollections.mutableListOf
import kollections.emptyList
import kollections.flatMap

private const val NAME = "Routes"

// only for react.js consumers (Not for kotlin-react consumers)
@JsExport
@JsName(NAME)
val InternalRoutes = FC<PropsWithChildren>(NAME) { props ->
    val parent = useRouteInfo()
    val navigator = useNavigator()
    NavigateReferenceContext(parent?.match?.evaluatedRoute) {
        val options = useMemo { Children.toArray(props.children).flatMap { it.toRouteConfig() } }
        SelectAndRender(parent, navigator, options)
    }
}

// only for kotlin-react consumers. (Not for react.js consumers)
@PublishedApi
internal val RoutesDsl = FC<RoutesBuilder>(NAME) { props ->
    val parent = useRouteInfo()
    val navigator = useNavigator()
    NavigateReferenceContext(parent?.match?.evaluatedRoute) {
        val options = useMemo(props.options) { props.options }
        SelectAndRender(parent, navigator, options)
    }
}

// only for kotlin-react consumers. (Not for react.js consumers)
inline fun ChildrenBuilder.Routes(noinline builder: RoutesBuilder.() -> Unit) = RoutesDsl {
    options = mutableListOf()
    builder()
}

private inline fun <C : RouteContent> ChildrenBuilder.SelectAndRender(
    parent: RouteInfo<C>?,
    navigator: Navigator,
    options: List<RouteConfig<C>>
) {
    val route = selectRoute(parent, navigator.route.watchAsState(), options) ?: return
    RouteInfoContext(route) { +route.content }
}

private fun ReactNode.toRouteConfig(): List<RouteConfig<RouteContent>> {
    var element = asElementOrNull() ?: return listOf()
    if (element.type == Fragment) {
        val elements = mutableListOf<RouteConfig<ReactNode?>>()
        val props = element.props.unsafeCast<PropsWithChildren>()
        Children.toArray(props.children).forEach {
            elements.addAll(it.toRouteConfig())
        }
        return elements
    }

    element = element.unsafeCast<ReactElement<PropsWithChildren>>()

    if (element.type != InternalRoute && js("typeof element.type") == "function") {
        val func = element.type.unsafeCast<(Props) -> ReactNode>()
        return func(element.props).toRouteConfig()
    }

    if (element.type != InternalRoute) {
        console.error("Only <Route> components are allowed in <$NAME>")
        console.error("You rendered a <${element.type}> as a direct child of <Routes>")
        return emptyList()
    }

    element = element.unsafeCast<ReactElement<RawRouteProps>>()

    if (element.props.path == null) {
        console.error("<Route> component is missing prop 'path'")
        return emptyList()
    }

    val props = element.props.unsafeCast<RouteProps>()

    return listOf(RouteConfig(props.path, props.element))
}