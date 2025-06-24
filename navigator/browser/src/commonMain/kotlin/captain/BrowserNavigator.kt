package captain

import captain.internal.document
import captain.internal.window
import cinematic.MutableLive
import cinematic.mutableLiveOf
import cinematic.singleWatchableLiveOf
import kiota.Url

class BrowserNavigator(private val syncWithAddressBar: Boolean) : Navigator {

    override val route: MutableLive<Url> = if (syncWithAddressBar) mutableLiveOf(current(), 0) else singleWatchableLiveOf(current())

    private val states = mutableMapOf<Url, Any?>()

    init {
        if (syncWithAddressBar) window.onpopstate = { navigate(current().path, record = false) }
    }

    override fun current() = Url(window.location.href)

    override fun navigate(path: String, record: Boolean, state: Any?) {
        val url = current().resolve(path)
        route.value = url
        states[url] = state
        if (record && syncWithAddressBar) pushState()
    }

    override fun navigate(path: String, record: Boolean) {
        val url = current().resolve(path)
        route.value = url
        if (record && syncWithAddressBar) pushState()
    }

    private fun pushState() {
        window.history.pushState(null, document.title, route.value.path)
    }

    override fun state(): Any? = states[current()]

    override fun go(steps: Int) = window.history.go(steps)

    override fun toString(): String = "BrowserNavigator"
}