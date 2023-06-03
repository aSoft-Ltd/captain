package captain

import kommander.expect
import kotlin.test.Test

class GenericRouterTest {
    @Test
    fun should_render_the_one_route() {
        val gr = router(BasicNavigator("/")) {
            routes {
                route("/") { 0 }
                route("/dashboard") { 1 }
                route("*") { 4 }
            }
        }

        expect(gr.render("/")).toBe(0)
        expect(gr.render("/dashboard")).toBe(1)
        expect(gr.render("/test")).toBe(4)
    }

    @Test
    fun should_be_able_to_access_parent_info_on_route_level() {
        val gr = router(BasicNavigator("/")) {
            routes {
                route("/") { "/" }
                route("/dashboard") { "/dashboard" }
                route("/nested/*") {

                    routes {
                        route("/") { "/nested" }
                    }
                }
            }
        }

        expect(gr.render("/")).toBe("/")
        expect(gr.render("/dashboard")).toBe("/dashboard")
        expect(gr.render("/nested")).toBe("/nested")
        expect(gr.render("/nested/")).toBe("/nested")
    }

    @Test
    fun should_render_nested_routes() {
        val gr = router(BasicNavigator("/")) {
            routes {
                route("/") { "/" }
                route("/dashboard") { "/dashboard" }
                route("/nested/*") {
                    routes {
                        route("/") { "/nested" }
                    }
                }
            }
        }

        expect(gr.render("/")).toBe("/")
        expect(gr.render("/dashboard")).toBe("/dashboard")
        expect(gr.render("/nested")).toBe("/nested")
        expect(gr.render("/nested/")).toBe("/nested")
    }
}