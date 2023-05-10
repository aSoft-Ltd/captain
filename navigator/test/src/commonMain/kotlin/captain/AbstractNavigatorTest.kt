package captain

import kommander.expect
import kotlin.test.Test

abstract class AbstractNavigatorTest {

    abstract val initial: String

    abstract val navigator: Navigator

    @Test
    fun should_start_at_initial_root() {
        expect(navigator.route.value).toBe(Url(initial))
    }

    @Test
    fun should_be_able_to_watch_navigate_freely() {
        navigator.navigate("/customers")
        val root = Url(initial).root()
        expect(navigator.route.value).toBe(Url("$root/customers"))
    }
}