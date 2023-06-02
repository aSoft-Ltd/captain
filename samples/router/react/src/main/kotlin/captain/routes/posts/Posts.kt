package captain.routes.posts

import captain.A
import captain.Route
import captain.Routes
import captain.useOptionalParam
import captain.useRouteInfo
import react.FC
import react.Fragment
import react.Props
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

    if (posts.isEmpty()) {
        progress {}
        div { +"Loading" }
    } else Routes {
        Route("{uid}/*", PostCompleteView)
        Route("/") {
            div {
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
    A {
        to = "/posts/${post.id}"
        div {
            +post.title
        }
    }
}

val PostCompleteView = FC<PostViewProps> {
    val (post, setPost) = useState<Post?>(null)
    val uid = useOptionalParam("uid").getOr("1")
    val ri = useRouteInfo()
    console.log("from: ", ri?.evaluatedRoute?.path)
    useEffectOnce {
        fetchAsync("https://jsonplaceholder.typicode.com/posts/$uid").then {
            it.json().unsafeCast<Post>()
        }.then { setPost(it) }
    }
    if (post != null) {
        h2 { +post.title }
        hr {}
        div { +post.body }
        A {
            to = "/posts/${uid}/comments"
            +"View Comments"
        }
    } else {
        progress {}
        div { +"Loading" }
    }
    Routes {
        Route("/", Fragment)
        Route("/comments", PostCommentView)
    }
}

external interface PostComment {
    val postId: Int
    val id: Int
    val name: String
    val email: String
    val body: String
}

val PostCommentView = FC<PostViewProps> { props ->
    val (comments, setComments) = useState<Array<PostComment>>(arrayOf())
    val uid = useOptionalParam("uid").getOr("12")
    useEffectOnce {
        fetchAsync("https://jsonplaceholder.typicode.com/posts/$uid/comments").then {
            it.json().unsafeCast<Array<PostComment>>()
        }.then { setComments(it) }
    }
    if (comments.isNotEmpty()) {
        comments.forEach { comment ->
            div {
                div { +"Name: ${comment.name}" }
                div { +"Email: ${comment.email}" }
                div { +"Comment: ${comment.body}" }
            }
            hr {}
        }
    } else {
        progress {}
        div { +"Loading comments" }
    }
}