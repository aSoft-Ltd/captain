@file:JsExport

package captain

import react.FC
import react.PropsWithChildren
import react.useMemo

external interface RouterProps : PropsWithChildren {
    var navigator: Navigator?
}

private const val NAME = "Router"

@JsName(NAME)
val InternalRouter = FC<RouterProps>(NAME) { props ->
    val navigator = useMemo { props.navigator ?: BrowserNavigator(syncWithAddressBar = true) }
    NavigatorContext.Provider(navigator) {
        child(props.children)
    }
}