package captain

import kommander.expect
import kotlin.test.Test

class UrlTest {
    @Test
    fun should_detect_the_url() {
        val url = Url("https://test.com")
        expect(url.scheme).toBe("https")
    }

    @Test
    fun should_return_a_blank_protocol_if_url_has_no_protocol() {
        val url = Url("test.com")
        expect(url.scheme).toBe("")
    }

    @Test
    fun should_return_a_blank_protocol_if_a_trailing_slash_url_has_no_protocol() {
        val url = Url("test.com/")
        expect(url.scheme).toBe("")
    }

    @Test
    fun should_detect_the_domain_of_a_trailing_slash_url_has_no_protocol() {
        val url = Url("test.com/")
        expect(url.domain).toBe("test.com")
    }

    @Test
    fun should_detect_the_domain() {
        val url = Url("https://test.com")
        expect(url.domain).toBe("test.com")
    }

    @Test
    fun should_detect_the_domain_of_a_trailing_slash_url() {
        val url = Url("https://test.com/")
        expect(url.domain).toBe("test.com")
    }

    @Test
    fun should_detect_the_domain_of_a_protocol_less_url() {
        val url = Url("test.com")
        expect(url.domain).toBe("test.com")
    }

    @Test
    fun should_detect_the_domain_of_a_url_with_no_protocol_but_has_paths() {
        val url = Url("test.com/api/v1")
        expect(url.domain).toBe("test.com")
    }

    @Test
    fun should_detect_the_domain_of_a_url_with_protocol_and_has_paths() {
        val url = Url("https://test.com/api/v1")
        expect(url.domain).toBe("test.com")
    }

    @Test
    fun should_detect_paths_of_a_url_with_domain_and_protocol() {
        val url = Url("https://test.com/api/v1")
        expect(url.segments).toBe(listOf("api", "v1"))
    }

    @Test
    fun should_detect_paths_of_a_url_with_domain_but_no_protocol() {
        val url = Url("test.com/api/v1")
        expect(url.segments).toBe(listOf("api", "v1"))
    }

    @Test
    fun should_detect_paths_of_a_trailing_slash_url_with_domain_but_no_protocol() {
        val url = Url("test.com/api/v1/")
        expect(url.segments).toBe(listOf("api", "v1"))
    }

    @Test
    fun should_return_a_sibling_url() {
        val url = Url("test.com/api/v2/")
        expect(url.sibling("v2").toString()).toBe("test.com/api/v2")
    }

    @Test
    fun should_return_a_blank_url_with_blank_fields() {
        val url = Url("")
        expect(url.scheme).toBe("")
        expect(url.domain).toBe("")
        expect(url.segments).toBeEmpty()
    }

    @Test
    fun should_detect_a_slash_only_url_with_blank_fields() {
        val url = Url("/")
        expect(url.scheme).toBe("")
        expect(url.domain).toBe("")
        expect(url.segments).toBeEmpty()
    }

    @Test
    fun a_trail_of_a_root_path_should_be_itself() {
        val url = Url("/")
        expect(url.path).toBe("/")
    }

    @Test
    fun a_trail_of_a_url_with_no_path_should_just_be_slash() {
        val url = Url("https://github.com")
        expect(url.path).toBe("/")
    }

    @Test
    fun a_trail_of_a_url_with_no_path_and_a_trailing_slash_should_just_be_slash() {
        val url = Url("https://github.com/")
        expect(url.path).toBe("/")
    }

    @Test
    fun should_have_a_relative_path_on_a_root_like_url() {
        val url = Url("/")
        val next = url.at("/users")
        expect(next.segments).toBe(listOf("users"))
        expect(next).toBe(Url("", "", "users"))
    }

    @Test
    fun should_keep_dynamic_definitions_intact_with_domains() {
        val url = Url("https://test.com/customer/:uid")
        expect(url.path).toBe("/customer/:uid")
    }

    @Test
    fun should_keep_dynamic_definitions_intact_without_domains() {
        val url = Url("/customer/:uid")
        expect(url.path).toBe("/customer/:uid")
    }

    @Test
    fun should_recover_from_trailing_slash_url_mistakes() {
        val url = Url("//users")
        expect(url).toBe(Url("/users"))
        expect(url.domain).toBe("")
        expect(url.scheme).toBe("")
        expect(url.segments).toBe(listOf("users"))
    }

    @Test
    fun should_return_the_correct_url_trail() {
        expect(Url("/users").path).toBe("/users")
        expect(Url("test.com/users").path).toBe("/users")
        expect(Url("http://test.com/users").path).toBe("/users")
        expect(Url("https://test.com/users").path).toBe("/users")
    }

    @Test
    fun should_return_the_correct_url_trail_of_a_long_url() {
        expect(Url("/users/890/info/files").path).toBe("/users/890/info/files")
        expect(Url("test.com/users/890/info/files").path).toBe("/users/890/info/files")
        expect(Url("http://test.com/users/890/info/files").path).toBe("/users/890/info/files")
        expect(Url("https://test.com/users/890/info/files").path).toBe("/users/890/info/files")
    }

    @Test
    fun should_detect_domains_with_port_number_but_no_dot() {
        val url = Url("http://localhost:4000/users/890/info/files")
        expect(url.domain).toBe("localhost:4000")
        expect(url.path).toBe("/users/890/info/files")
    }

    @Test
    fun should_have_paths_with_dots_int_them() {
        val url = Url("http://localhost:4000/content.html")
        expect(url.domain).toBe("localhost:4000")
        expect(url.path).toBe("/content.html")
        expect(url.segments).toBe(listOf("content.html"))
    }

    @Test
    fun should_be_able_to_get_the_root_of_the_url() {
        val url = Url("http://github.com/andylamax")
        expect(url.root).toBe("http://github.com")
    }
}