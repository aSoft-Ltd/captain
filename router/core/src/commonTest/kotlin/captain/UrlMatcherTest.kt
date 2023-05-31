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

    @Test
    fun should_not_match_unmatching_path_with_wild_card() {
        val config = Url("/posts/*")
        val route = Url("/")
    }

    @Test
    fun should_be_able_to_match_dynamic_routes_defined_with_a_colon() {
        val params = Url("/test/123").matches(Url("/test/:uid"))
        expect(params).toBeNonNull()
    }

    @Test
    fun should_be_able_to_get_route_params_of_dynamic_routes_defined_with_a_colon() {
        val params = Url("/test/123").matches(Url("/test/:uid"))
        val uid = params?.get("uid")?.getOrNull()
        expect(uid).toBe("123")
    }

    @Test
    fun should_be_able_to_match_dynamic_routes_defined_with_curled_brackets() {
        val params = Url("/test/123").matches(Url("/test/{uid}"))
        expect(params).toBeNonNull()
    }

    @Test
    fun should_be_able_to_get_route_params_of_dynamic_routes_defined_with_curled_brackets() {
        val params = Url("/test/123").matches(Url("/test/{uid}"))
        val uid = params?.get("uid")?.getOrNull()
        expect(uid).toBe("123")
    }

    @Test
    fun should_match_the_beginning_of_a_nested_route() {
        val params = Url("/test/").matches(Url("/test/*"))
        expect(params).toBeNonNull()
    }

    @Test
    fun should_match_the_beginning_of_a_nested_route_with_longer_lenght_than_configured() {
        val params = Url("/test/people/me").matches(Url("/test/*"))
        expect(params).toBeNonNull()
    }

    @Test
    fun should_be_able_to_choose_the_best_option() {
        val options = listOf("/", "/posts/*", "/*").map {
            RouteConfig(Url(it), it)
        }
        val matches = options.matches("/posts")
        expect(matches).toBeNonNull()
        val selected = matches.bestMatch()
        expect(selected?.match?.url?.path).toBe("/posts/*")
    }

    @Test
    fun should_be_able_to_disabuaguate_a_deep_wild_card() {
        val options = listOf("/posts/{uid}/*", "/posts").map {
            RouteConfig(Url(it), it)
        }

        val matches = options.matches("/posts")
        val selected = matches.bestMatch()
        expect(selected?.match?.url?.path).toBe("/posts")
    }
}