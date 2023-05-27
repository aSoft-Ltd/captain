package captain

import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul

val App = FC<Props> {
    val nav = BrowserNavigator()
    Router {
        navigator = nav
        Routes {
            Route {
                path = "/"
                div { +"This is home" }
                NavigationMenu {}
            }

            Route {
                path = "/about"
                div { +"This is about" }
                NavigationMenu {}
            }

            Route {
                path = "/settings"
                div { +"Settings" }
                NavigationMenu {}
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
        listOf("about", "settings").forEach { path ->
            li {
                button {
                    onClick = { nav.navigate("/$path") }
                    +"/$path"
                }
            }
        }
    }
}