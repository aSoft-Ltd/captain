package captain

import captain.internal.AbstractNavigator
import cinematic.MutableLive
import cinematic.singleWatchableLiveOf
import kotlinx.browser.document
import kotlinx.browser.window

open class BrowserFollowingNavigator : AbstractNavigator() {

    override val route: MutableLive<Url> = singleWatchableLiveOf(current())

    final override fun current() = Url(window.location.href)

    override fun toString(): String = "BrowserFollowingNavigator"
}