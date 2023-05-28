package captain.routes.posts

import captain.Link
import captain.Route
import captain.Routes
import captain.useParams
import react.FC
import react.Props
import react.create
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.hr
import react.dom.html.ReactHTML.progress
import react.useEffectOnce
import react.useState
import web.http.fetchAsync

external interface Post {
    val userId: Int
    val id: Int
    val title: String
    val body: String
}

val Posts = FC<Props> {
    val (posts, setPosts) = useState<Array<Post>>(arrayOf())
    useEffectOnce {
        fetchAsync("https://jsonplaceholder.typicode.com/posts").then {
            it.json().unsafeCast<Array<Post>>()
        }.then { setPosts(it) }
    }
    h2 {
        +"Posts"
    }

    Routes {
        Route {
            path = "{uid}"
            element = PostCompleteView.create()
        }
        Route {
            path = "/"
            element = div.create {
                posts.forEach {
                    PostSummaryView { post = it }
                }
            }
        }
    }
}

external interface PostViewProps : Props {
    var post: Post
}

val PostSummaryView = FC<PostViewProps> { props ->
    val post = props.post
    Link {
        to = "/posts/${post.id}"
        div {
            +post.title
        }
    }
}

val PostCompleteView = FC<PostViewProps> { props ->
    val (post, setPost) = useState<Post?>(null)
    val uid = useParams("uid")
    useEffectOnce {
        fetchAsync("https://jsonplaceholder.typicode.com/posts/$uid").then {
            it.json().unsafeCast<Post>()
        }.then { setPost(it) }
    }
    if (post != null) {
        h2 { +post.title }
        hr {}
        div { +post.body }
    } else {
        progress {}
        div { +"Loading" }
    }
}