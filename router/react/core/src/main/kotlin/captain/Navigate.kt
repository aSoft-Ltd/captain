@file:JsExport

package captain

import react.FC
import react.Props

external interface NavigateProps : Props {
    var to: String
}

val Navigate = FC<NavigateProps> { props ->
    val navigate = useNavigator()
    navigate.navigate(props.to)
}