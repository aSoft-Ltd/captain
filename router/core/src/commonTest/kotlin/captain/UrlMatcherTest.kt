package captain

import kommander.expect
import kotlin.test.Test

class UrlMatcherTest {
    @Test
    fun should_match_two_root_routes() {
        val params = Url("/").matches(Url("/"))
        expect(params).toBeNonNull()
    }

    @Test
    fun should_match_tow_exact_routes() {
        val params = Url("/test/123").matches(Url("/test/123"))
        expect(params).toBeNonNull()
    }

    @Test
    fun should_match_wild_card_routes() {
        val params = Url("/test/123").matches(Url("/test/*"))
        expect(params).toBeNonNull()
    }

    @Test
    fun should_not_match_un_matching_wild_card_routes() {
        val params = Url("/tist/123").matches(Url("/test/*"))
        expect(params).toBeNull()
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
}