package captain

import captain.internal.AbstractNavigator
import cinematic.MutableLive
import cinematic.mutableLiveOf
import cinematic.singleWatchableLiveOf
import kotlinx.browser.document
import kotlinx.browser.window

class BrowserNavigator(private val syncWithAddressBar: Boolean) : AbstractNavigator() {

    override val route: MutableLive<Url> = if (syncWithAddressBar) mutableLiveOf(current()) else singleWatchableLiveOf(current())

    init {
        if (syncWithAddressBar) window.onpopstate = {
            navigate(current().trail(), false)
        }
    }

    override fun current() = Url(window.location.href)

    private fun navigate(path: String, pushing: Boolean) {
        super.navigate(path)
        if (pushing && syncWithAddressBar) {
            window.history.pushState(null, document.title, route.value.trail())
        }
    }

    override fun navigate(path: String) = navigate(path, true)

    override fun toString(): String = "BrowserNavigator"
}