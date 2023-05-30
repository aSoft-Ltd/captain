package captain

import react.FC
import react.Props

val App = FC<Props> {
    Router {
        JsonPlaceHolderNavigationMenu {}
        JsonPlaceHolderAppRouting {}
    }
}