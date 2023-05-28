@file:JsExport

package captain

import react.FC
import react.PropsWithChildren
import react.dom.html.ReactHTML.a

external interface AProps : PropsWithChildren {
    var to: String
}

val A = FC<AProps>("A") { props ->
    val nav = useNavigator()
    a {
        href = props.to
        onClick = { event ->
            event.preventDefault()
            nav.navigate(props.to)
        }
        child(props.children)
    }
}