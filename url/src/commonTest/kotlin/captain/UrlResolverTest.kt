package captain

import kommander.expect
import kotlin.test.Test

class UrlResolverTest {
    @Test
    fun should_be_able_to_go_back() {
        val url = Url("google.com/meet/user")
        expect(url.resolve("..")).toBe(Url("google.com/meet"))
    }

    @Test
    fun should_fail_to_slice_when_there_is_no_trail() {
        val url = Url("google.com")
        expect(url.resolve("..")).toBe(Url("google.com"))
    }

    @Test
    fun should_resolve_to_a_sibling_on_a_relative_route() {
        val url = Url("google.com/meet/user")
        expect(url.resolve("./admin")).toBe(Url("google.com/meet/admin"))
    }
}