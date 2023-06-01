@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import react.FC
import react.Props
import react.createContext
import react.useContext
import react.useEffectOnce

internal val NavigatorContext = createContext<Navigator>()

fun useNavigator(): Navigator {
    return useContext(NavigatorContext) ?: throw RuntimeException("Looks like you haven't called <Router navigator={...}></Router>")
}

fun useNavigate(): NavigateFunction = NavigateFunction(useNavigator())

external interface NavigateProps : Props {
    var to: String
}

val Navigate = FC<NavigateProps> { props ->
    val navigate = useNavigate()
    useEffectOnce { navigate(props.to) }
}