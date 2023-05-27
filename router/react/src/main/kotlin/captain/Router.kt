@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import react.FC
import react.PropsWithChildren

external interface RouterProps: PropsWithChildren {
    var navigator: Navigator
}

val Router = FC<RouterProps> {props->
    NavigatorContext.Provider {
        value = props.navigator
        child(props.children)
    }
}
