package captain

import kommander.expect
import kotlin.test.Test

class RoutingDslTest {
    @Test
    fun should_create_a_simple_and_abstract_routing_api() {
        val tree = routes()
        expect(tree).toBeNonNull()
    }

    @Test
    fun should_add_a_simple_destination() {
        val tree = routes {
            route("/test")
        }
        expect(tree.entries).toBeOfSize(1)
    }

    @Test
    fun should_add_a_multiple_destinations() {
        val tree = routes {
            route("/home")
            route("/about")
            route("/settings")
        }
        expect(tree.entries).toBeOfSize(3)
        expect(tree.entries.map { it.trail() }).toBe(listOf("/home", "/about", "/settings"))
    }

    @Test
    fun should_be_able_to_create_sub_routes() {
        val tree = routes {
            route("/home")
            routes("/customer") {
                route("info")
            }
        }
        expect(tree.entries).toBeOfSize(2)
        expect(tree.entries.map { it.trail() }).toBe(listOf("/home", "/customer/info"))
    }

    @Test
    fun should_be_able_to_create_a_deeply_nested_routing_tree() {
        val tree = routes {
            route("/home")
            route("/about")
            routes("/customers") {
                route("info")
                routes("orders") {
                    route("summary")
                    routes("1") {
                        route("details")
                        route("payments")
                        route("invoices")
                    }
                }
            }
        }

        expect(tree.entries).toBeOfSize(7)
        expect(tree.entries.map { it.trail() }).toBe(
            listOf(
                "/home", "/about",
                "/customers/info", "/customers/orders/summary",
                "/customers/orders/1/details","/customers/orders/1/payments","/customers/orders/1/invoices",
            )
        )
    }
}