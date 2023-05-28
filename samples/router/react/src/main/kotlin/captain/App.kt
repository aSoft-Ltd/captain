package captain

import react.FC
import react.Props

val App = FC<Props> {
    Router {
        navigator = BrowserNavigator(syncWithAddressBar = true)
        JsonPlaceHolderNavigationMenu {}
        JsonPlaceHolderAppRouting {}
    }
}