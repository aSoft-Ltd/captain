package captain

import cinematic.MutableLive
import cinematic.mutableLiveOf
import cinematic.singleWatchableLiveOf
import kotlinx.browser.document
import kotlinx.browser.window

class BrowserNavigator(private val syncWithAddressBar: Boolean) : Navigator {

    override val route: MutableLive<Url> = if (syncWithAddressBar) mutableLiveOf(current(), 0) else singleWatchableLiveOf(current())

    init {
        if (syncWithAddressBar) window.onpopstate = { navigate(current().trail(), pushing = false) }
    }

    override fun current() = Url(window.location.href)

    private fun navigate(path: String, pushing: Boolean) {
        route.value = current().resolve(path)
        if (pushing && syncWithAddressBar) pushState()
    }

    private fun pushState() {
        window.history.pushState(null, document.title, route.value.trail())
    }

    override fun go(steps: Int) = window.history.go(steps)

    override fun navigate(path: String) = navigate(path, pushing = true)

    override fun toString(): String = "BrowserNavigator"
}