@file:Suppress("NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")
@file:OptIn(ExperimentalJsExport::class)

package captain

import react.ChildrenBuilder
import react.FC
import react.Props
import react.useEffect

@JsExport
external interface NavigateProps : Props {
    var to: String
}

private const val NAME = "Navigate"

// Only for react.js consumers (Not for kotlin-react consumers)
@JsExport
@JsName(NAME)
val InternalNavigate = FC<NavigateProps>(NAME) { props ->
    val navigate = useNavigate()
    useEffect(props.to) { navigate(props.to) }
}

// Only for kotlin-react consumers (Not for react.js consumers)
inline fun ChildrenBuilder.Navigate(to: String) = InternalNavigate { this.to = to }