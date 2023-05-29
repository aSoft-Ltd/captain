@file:JsExport

package captain

import react.FC
import react.dom.html.ReactHTML.a

val A = FC<LinkProps>("A") { props ->
    val navigate = useNavigate()
    a {
        href = props.to
        onClick = { event ->
            event.preventDefault()
            navigate(props.to)
        }
        child(props.children)
    }
}