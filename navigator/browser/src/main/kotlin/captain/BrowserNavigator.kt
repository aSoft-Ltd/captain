package captain

import captain.internal.AbstractNavigator
import cinematic.MutableLive
import cinematic.singleWatchableLiveOf
import kotlinx.browser.window

class BrowserNavigator : AbstractNavigator() {

    override val route: MutableLive<Url> = singleWatchableLiveOf(current())

    override fun current() = Url(window.location.href)

    override fun toString(): String = "BrowserNavigator"
}