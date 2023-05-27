package captain

import react.FC
import react.Props
import react.dom.html.ReactHTML.div

val App = FC<Props> {
    Router {
        navigator = BrowserNavigator()
        Routes {
            Route {
                path = "/"
                div { +"This is home" }
            }

            Route {
                path = "/about"
                div { +"This is about" }
            }

            Route {
                path = "/settings"
                div { +"Settings" }
            }
        }
    }
}