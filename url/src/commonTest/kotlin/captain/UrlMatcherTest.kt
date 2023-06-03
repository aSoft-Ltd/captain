package captain

import kommander.expect
import kotlin.test.Test

class UrlMatcherTest {
    @Test
    fun should_match_two_root_routes() {
        val match = Url("/").matches(Url("/"))
        expect(match).toBeNonNull()
    }

    @Test
    fun should_match_tow_exact_routes() {
        val match = Url("/test/123").matches(Url("/test/123"))
        expect(match).toBeNonNull()
    }

    @Test
    fun should_match_wild_card_routes() {
        val match = Url("/test/123").matches(Url("/test/*"))
        expect(match).toBeNonNull()
    }

    @Test
    fun should_not_match_un_matching_wild_card_routes() {
        val match = Url("/tist/123").matches(Url("/test/*"))
        expect(match).toBeNull()
    }

    @Test
    fun should_match_multiple_wild_card_routes() {
        val config = Url("/test/*/person/*")
        val route = Url("/test/123/person/456")
        expect(route.matches(config)).toBeNonNull()
    }

    @Test
    fun should_un_match_multiple_wild_card_routes() {
        val config = Url("/test/*/person/*")
        val route = Url("/test/123/lerson/456")
        expect(route.matches(config)).toBeNull()
    }

    @Test
    fun should_be_able_to_match_dynamic_routes_defined_with_a_colon() {
        val match = Url("/test/123").matches(Url("/test/:uid"))
        expect(match).toBeNonNull()
    }

    @Test
    fun should_be_able_to_get_route_params_of_dynamic_routes_defined_with_a_colon() {
        val match = Url("/test/123").matches(Url("/test/:uid"))
        val uid = match?.param("uid")?.getOrNull()
        expect(uid).toBe("123")
    }

    @Test
    fun should_be_able_to_match_dynamic_routes_defined_with_curled_brackets() {
        val match = Url("/test/123").matches(Url("/test/{uid}"))
        expect(match).toBeNonNull()
    }

    @Test
    fun should_be_able_to_get_route_params_of_dynamic_routes_defined_with_curled_brackets() {
        val match = Url("/test/123").matches(Url("/test/{uid}"))
        val uid = match?.param("uid")?.getOrNull()
        expect(uid).toBe("123")
    }

    @Test
    fun should_match_the_beginning_of_a_nested_route() {
        val match = Url("/test/").matches(Url("/test/*"))
        expect(match).toBeNonNull()
    }

    @Test
    fun should_match_the_beginning_of_a_nested_route_with_longer_lenght_than_configured() {
        val match = Url("/test/people/me").matches(Url("/test/*"))
        expect(match).toBeNonNull()
    }

    @Test
    fun should_not_match_with_parent() {
        val match = Url("/photos").matches("/")
        expect(match).toBeNull()
    }

    @Test
    fun should_match_wild_card_root_trailing() {
        val match = Url("/photos").matches("/*")
        expect(match).toBeNonNull()
    }
}