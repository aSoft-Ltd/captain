package captain

import kommander.expect
import kollections.listOf
import kiota.Url
import kollections.map
import kotlin.test.Test

class RouterMatcherTest {
    @Test
    fun should_be_able_to_choose_the_best_option() {
        val options = listOf("/", "/posts/*", "/*").map {
            RouteConfig(Url(it), it)
        }
        val matches = options.matches("/posts")
        expect(matches).toBeNonNull()
        val selected = matches.bestMatch()
        expect(selected?.match?.pattern?.path).toBe("/posts/*")
    }

    @Test
    fun should_be_able_to_disambiguate_a_deep_wild_card() {
        val options = listOf("/posts/{uid}/*", "/posts").map {
            RouteConfig(Url(it), it)
        }

        val matches = options.matches("/posts")
        val selected = matches.bestMatch()
        expect(selected?.match?.pattern?.path).toBe("/posts")
    }
}