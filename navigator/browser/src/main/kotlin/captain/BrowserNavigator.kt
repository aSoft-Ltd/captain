package captain

import kotlinx.browser.document
import kotlinx.browser.window

class BrowserNavigator : BrowserFollowingNavigator() {

    private val history = window.history

    init {
        window.onpopstate = {
            navigate(current().trail(), false)
        }
    }

    private fun navigate(path: String, pushing: Boolean) {
        super.navigate(path)
        if (pushing) {
            history.pushState(null, document.title, route.value.trail())
        }
    }

    override fun navigate(path: String) = navigate(path, true)

    override fun toString(): String = "BrowserNavigator"
}