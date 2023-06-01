package captain

import captain.routes.posts.Posts
import react.FC
import react.Props
import react.create
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1

val JsonPlaceHolderAppRouting = FC<Props> {
    Routes {
        Route("/", h1.create { +"JsonPlaceHolder routing demo" })
        Route("/posts/*", Posts)
        Route("/home") { Navigate { to = "/" } }
        Route("/users") {
            h1 { +"Users page" }
            div { +"This is the users page" }
        }
//        Route("*") {
//            h1 { +"I think you are lost" }
//        }
    }
}