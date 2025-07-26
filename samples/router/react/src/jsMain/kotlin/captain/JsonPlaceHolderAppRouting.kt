package captain

import captain.routes.posts.Posts
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1

val JsonPlaceHolderAppRouting = FC<Props> {
    ReactRoutes {
        Users {}
    }

//    Routes {
//        Route("/", h1.create { +"JsonPlaceHolder routing demo" })
//        Route("/posts/*", Posts)
//        Route("/about") { h1 { +"Welcome to the about page" } }
//        Route("/info") { Navigate(to = "/about") }
//        Route("/users") {
//            h1 { +"Users page" }
//            div { +"This is the users page" }
//        }
//        Route("*") {
//            h1 { +"I think you are lost" }
//        }
//    }
}

val ReactRoutes = FC<PropsWithChildren>("ReactRoutes") { props ->
    InternalRoutes {
        InternalRoute {
            path = "/"
            element = h1.create { +"JsonPlaceHolder routing demo" }
        }
        InternalRoute {
            path = "/posts/*"
            element = Posts.create()
        }
        InternalRoute {
            path = "/about"
            element = h1.create { +"Welcome to the about page" }
        }

        +props.children

        InternalRoute {
            path = "/info"
            element = InternalNavigate.create { to = "/about" }
        }

        Fragment {
            InternalRoute {
                path = "/test"
                element = Fragment.create()
            }
        }

        InternalRoute {
            path = "*"
            element = Fragment.create {
                h1 { +"I think you are lost" }
            }
        }
    }
}

val Users = FC<Props>("Users") {
    InternalRoute {
        path = "/users"
        element = Fragment.create {
            h1 { +"Users page" }
            div { +"This is the users page" }
        }
    }
}