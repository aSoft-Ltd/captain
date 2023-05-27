package captain

import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.section
import react.dom.html.ReactHTML.ul

val App = FC<Props> {
    val nav = BrowserNavigator(syncWithAddressBar = true)
    Router {
        navigator = nav
        Routes {
            Route {
                path = "/"
                section {
                    div { +"Home page" }
                    NavigationMenu {}
                }
            }

            Route {
                path = "/about"
                section {
                    div { +"This is about" }
                    NavigationMenu {}
                }
            }

            Route {
                path = "/settings"
                section {
                    div { +"Settings Page" }
                    NavigationMenu {}
                }
            }

            Route {
                path = "/customer/{name}"
                People { heading = "Customer" }
            }

            Route {
                path = "/champion/:name"
                People { heading = "Champion" }
            }

            Route {
                path = "/test"
                section {
                    div { +"Test Page" }
                    NavigationMenu {}
                }
            }
        }
    }
}

val NavigationMenu = FC<Props>("NavigationMenu") {
    val nav = useNavigator()
    ul {
        li {
            button {
                onClick = { nav.navigate("/") }
                +"/home"
            }
        }
        listOf("about", "settings", "test").forEach { path ->
            li {
                button {
                    onClick = { nav.navigate("/$path") }
                    +"/$path"
                }
            }
        }

        for (entity in listOf("customer", "champion")) {
            people.forEach { path ->
                li {
                    button {
                        onClick = { nav.navigate("/$entity/$path") }
                        +"/$entity/$path"
                    }
                }
            }
        }
    }
}

val people = listOf("Raiden", "Goro", "Peter")

external interface PeopleProps : Props {
    var heading: String
}

val People = FC<PeopleProps> { props ->
    val name = useOptionalParam("name").getOr("Unknown")
    section {
        h3 {
            +props.heading
        }

        div {
            div { +"id  : ${people.indexOf(name)}" }
            div { +"name: $name" }
        }
    }
}