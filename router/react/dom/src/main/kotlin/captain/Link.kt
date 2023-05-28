@file:JsExport

package captain

import react.FC
import react.PropsWithChildren
import react.dom.html.ReactHTML.a

external interface LinkProps : PropsWithChildren {
    var to: String
    var text: String?
}

val Link = FC<LinkProps>("Link") { props ->
    val nav = useNavigator()
    a {
        href = props.to
        onClick = { event ->
            event.preventDefault()
            nav.navigate(props.to)
        }
        when (val txt = props.text) {
            null -> child(props.children)
            else -> +txt
        }
    }
}