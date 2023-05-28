package captain

import react.FC
import react.Props

val App = FC<Props> {
    val nav = BrowserNavigator(syncWithAddressBar = true)
    Router {
        navigator = nav
        JsonPlaceHolderNavigationMenu {}
        JsonPlaceHolderAppRouting {}
    }
}