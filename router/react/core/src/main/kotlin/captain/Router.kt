@file:OptIn(ExperimentalJsExport::class)
@file:Suppress("NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")

package captain

import captain.internal.NavigatorContext
import react.ChildrenBuilder
import react.FC
import react.PropsWithChildren
import react.useMemo

@JsExport
external interface RouterProps : PropsWithChildren {
    var navigator: Navigator?
}

private const val NAME = "Router"

@JsExport
@JsName(NAME)
val InternalRouter = FC<RouterProps>(NAME) { props ->
    val navigator = useMemo { props.navigator ?: BrowserNavigator(syncWithAddressBar = true) }
    NavigatorContext(navigator) { +props.children }
}

inline fun ChildrenBuilder.Router(
    navigator: Navigator? = null,
    noinline builder: ChildrenBuilder.() -> Unit
) = InternalRouter {
    this.navigator = navigator
    this.builder()
}