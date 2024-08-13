@file:JsExport

package captain

import react.FC
import react.PropsWithChildren
import react.dom.html.ReactHTML.a

external interface AProps : DestinationProps, PropsWithChildren

val A = FC<AProps>("A") { props ->
    val navigate = useNavigate()
    a {
        href = props.to
        onClick = { event ->
            event.preventDefault()
            navigate(props.to)
        }
        +props.children
    }
}