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
    fun should_resolve_back_like_a_file_system_route() {
        val url = Url("google.com")
        expect(url.resolve("..")).toBe(Url("google.com"))
    }

    @Test
    fun should_resolve_to_a_child_on_a_relative_route_like_in_a_file_system() {
        val url = Url("google.com/meet/user")
        expect(url.resolve("./admin")).toBe(Url("google.com/meet/user/admin"))
    }

    @Test
    fun should_resolve_to_a_child_on_a_relative_route() {
        val url = Url("google.com/meet/user")
        expect(url.resolve("admin")).toBe(Url("google.com/meet/user/admin"))
    }

    @Test
    fun should_resolve_to_sibling_route_as_a_file_system_would() {
        val url = Url("google.com/meet/user")
        expect(url.resolve("../admin")).toBe(Url("google.com/meet/admin"))
    }

    @Test
    fun should_resolve_to_an_absolute_path() {
        val url = Url("google.com/meet/user")
        expect(url.resolve("/john/doe")).toBe(Url("google.com/john/doe"))
    }
}