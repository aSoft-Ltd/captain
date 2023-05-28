package captain

import react.FC
import react.Props
import react.createContext
import react.useContext

internal val NavigatorContext = createContext<Navigator>()

fun useNavigator(): Navigator {
    return useContext(NavigatorContext) ?: throw RuntimeException("Looks like you haven't called <Router navigator={...}></Router>")
}

fun useNavigate(): NavigateFunction = NavigateFunction(useNavigator())
external interface NavigateProps : Props {
    var to: String
}

val Navigate = FC<NavigateProps> { props ->
    useNavigator().navigate(props.to)
}