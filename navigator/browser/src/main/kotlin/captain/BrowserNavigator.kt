package captain

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.Window

class BrowserNavigator : BrowserFollowingNavigator() {

    private val history = window.history

    init {
        window.onpopstate = {
            navigate(current().trail())
        }
    }

    override fun navigate(path: String) {
        super.navigate(path)
        history.pushState(null, document.title, route.value.trail())
    }

    override fun toString(): String = "BrowserNavigator"
}