package captain

import react.FC
import react.Props
import react.create

val AppRoutes = FC<Props> {
    Routes {
        Route {
//            path = "/"
            element = Home.create()
        }

        Route {
            path = "/about"
            element = About.create()
        }

        Route {
            path = "/settings"
            element = Settings.create()
        }

        Route {
            path = "/customer/{name}"
            element = People.create { heading = "Customer" }
        }

        Route {
            path = "/champion/:name"
            element = People.create { heading = "Champion" }
        }
    }
}