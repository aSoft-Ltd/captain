package captain

import captain.routes.posts.Posts
import react.FC
import react.Props
import react.create
import react.dom.html.ReactHTML.h1

val JsonPlaceHolderAppRouting = FC<Props> {
    Routes {
        Route {
            path = "/"
            element = h1.create { +"JsonPlaceHolder routing demo" }
        }
        Route {
            path = "/posts/*"
            element = Posts.create()
        }
    }
}