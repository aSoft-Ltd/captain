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
        if (syncWithAddressBar) window.onpopstate = { navigate(current().toString(), record = false) }
    }

    override fun current() = Url(window.location.href)

    override fun navigate(path: String, options: NavigateOptions) {
        val url = route.value.resolve(path, options.preserve == Preserve.QueryOnly || options.preserve == Preserve.Both)
        route.value = url
        val state = options.state
        if (state != null) {
            states[url] = state
        } else if (options.preserve == Preserve.None || options.preserve == Preserve.QueryOnly) {
            states.remove(url)
        }
        if (options.record && syncWithAddressBar) pushState()
    }

    private fun pushState() {
        window.history.pushState(null, document.title, route.value.path)
    }

    override fun state(): Any? = states[route.value]

    override fun go(steps: Int) = window.history.go(steps)

    override fun toString(): String = "BrowserNavigator"
}