package captain

import kommander.expect
import kommander.toBe
import kotlin.test.Test

class BasicNavigatorTest : AbstractNavigatorTest() {

    override val initial: String = "https://test.com"

    override val navigator: Navigator = BasicNavigator(initial)

    @Test
    fun should_be_able_to_navigate_to_relative_destinations() {
        navigator.navigate("customers")
        expect(navigator.route.value).toBe(Url("$initial/customers"))
    }

    @Test
    fun should_be_able_to_navigate_to_relative_destinations_with_multiple_paths() {
        navigator.navigate("customers/123/info")
        expect(navigator.route.value).toBe(Url("$initial/customers/123/info"))
        navigator.navigate("edit")
        expect(navigator.route.value).toBe(Url("$initial/customers/123/edit"))
    }

    @Test
    fun should_use_basic_navigator() {
        expect(navigator).toBe<BasicNavigator>()
        expect(navigator.toString()).toBe("BasicNavigator(root=https://test.com)")
    }
}