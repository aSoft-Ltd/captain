package captain

import kiota.Url
import kommander.expect
import kommander.toBe
import captain.internal.window
import kotlin.test.Test

class BrowserNavigatorTest : AbstractNavigatorTest() {

    // During testing this looks like this
    // https://localhost:9065/content.html
    override val initial: String get() = window.location.href

    override val navigator: Navigator = BrowserNavigator(syncWithAddressBar = false)

    @Test
    fun should_be_able_to_navigate_to_relative_destinations() {
        val prev = Url(initial)
        navigator.navigate("../customers")
        expect(navigator.route.value).toBe(prev.at("/customers"))
    }

    @Test
    fun should_be_able_to_navigate_to_relative_destinations_with_multiple_paths() {
        val prev = Url(initial)
        navigator.navigate("../customers/123/info")
        expect(navigator.route.value).toBe(prev.at("/customers/123/info"))
    }

    @Test
    fun should_be_using_the_browser_navigator() {
        expect(navigator.toString()).toBe("BrowserNavigator")
        expect(navigator).toBe<BrowserNavigator>()
    }
}