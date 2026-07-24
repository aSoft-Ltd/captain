package captain

import kiota.Url
import kommander.expect
import kotlin.test.Test
import kotlin.test.fail

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
        println("Navigating with callback")
        navigator.navigate("/customers?callback=https://example.com")
        println("Navigated with callback")
        println("After navigation (navigation.current() = ${navigator.current()})")
        println("After navigation (route.value = ${navigator.route.value})")
        val root = Url(initial).root
        navigator.navigate("/people", NavigateOptions(preserve = Preserve.Query))
        expect(navigator.route.value).toBe(Url("$root/people?callback=https://example.com"))
    }
}