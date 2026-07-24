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

    @Test
    fun should_be_able_to_navigate_with_query_parameters() {
        navigator.navigate("/customers?callback=https://example.com")
        val root = Url(initial).root
        expect(navigator.route.value).toBe(Url("$root/customers?callback=https://example.com"))
    }

    @Test
    fun should_be_able_preserve_query_parameters_when_navigating() {
        navigator.navigate("/customers?callback=https://example.com")
        val root = Url(initial).root
        navigator.navigate("/people", NavigateOptions(preserve = Preserve.QueryOnly))
        expect(navigator.route.value).toBe(Url("$root/people?callback=https://example.com"))
    }

    @Test
    fun should_be_able_to_preserve_state() {
        navigator.navigate("/person", NavigateOptions(state = 123))
        expect(navigator.state()).toBe(123)
        navigator.navigate("/company")
        expect(navigator.state()).toBe(null)
        navigator.navigate("/person")
        expect(navigator.state()).toBe(123)
    }

    @Test
    fun should_be_able_to_stop_preserving_state() {
        navigator.navigate("/person", NavigateOptions(state = 123))
        expect(navigator.state()).toBe(123)
        navigator.navigate("/company")
        expect(navigator.state()).toBe(null)
        navigator.navigate("/person", NavigateOptions(preserve = Preserve.None))
        expect(navigator.state()).toBe(null)
    }
}