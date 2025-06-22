package captain

import kiota.Url
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
        val root = Url(initial).root
        expect(navigator.route.value).toBe(Url("$root/customers"))
    }

    @Test
    fun should_be_able_to_encode_another_url_as_a_query_parameter() {
        val callback = "https://example.com"
        navigator.navigate("/customers?callback=$callback")
        val root = Url(initial).root
        expect(navigator.route.value).toBe(Url("$root/customers?callback=$callback"))
    }
}