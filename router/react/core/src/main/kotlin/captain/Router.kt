@file:JsExport
package captain

import react.FC
import react.PropsWithChildren
import react.useMemo

external interface RouterProps : PropsWithChildren {
    var navigator: Navigator?
}

val Router = FC<RouterProps> { props ->
    val navigator = useMemo { props.navigator ?: BrowserNavigator(syncWithAddressBar = true) }
    NavigatorContext.Provider(navigator) {
        child(props.children)
    }
}