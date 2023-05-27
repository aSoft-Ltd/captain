package captain

import react.FC
import react.PropsWithChildren

external interface RouterProps : PropsWithChildren {
    var navigator: Navigator
}

val Router = FC<RouterProps> { props ->
    NavigatorContext.Provider(props.navigator) {
        child(props.children)
    }
}