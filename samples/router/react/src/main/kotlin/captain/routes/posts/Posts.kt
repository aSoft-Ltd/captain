package captain.routes.posts

import captain.A
import captain.Route
import captain.Routes
import captain.component1
import captain.component2
import captain.useNavigateReference
import captain.useNavigator
import captain.useOptionalParam
import captain.useParams
import captain.useRouteInfo
import react.FC
import react.Fragment
import react.Props
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.hr
import react.dom.html.ReactHTML.progress
import react.useEffectOnce
import react.useState
import web.http.fetchAsync
import kotlin.random.Random
import kotlin.random.nextInt

external interface Post {
    val userId: Int
    val id: Int
    val title: String
    val body: String
}

val Posts = FC<Props> {
    val (posts, setPosts) = useState<Array<Post>>(arrayOf())
    val navRef = useNavigateReference()
    useEffectOnce {
        fetchAsync("https://jsonplaceholder.typicode.com/posts").then {
            it.jsonAsync().unsafeCast<Array<Post>>()
        }.then { setPosts(it) }
    }
    h2 {
        +"Posts"
    }

    div {
        +"NavRef: ${navRef.path}"
    }

    if (posts.isEmpty()) {
        progress {}
        div { +"Loading" }
    } else Routes {
        Route("/") {
            div {
                posts.forEach {
                    PostSummaryView { post = it }
                }
            }
        }
        Route("{uid}/*", PostCompleteView)
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
    val (uid) = useParams()
    val navRef = useNavigateReference()
//    val uid = useParams().asDynamic().uid
    useEffectOnce {
        fetchAsync("https://jsonplaceholder.typicode.com/posts/$uid").then {
            it.jsonAsync().unsafeCast<Post>()
        }.then { setPost(it) }
    }

    div { +"NavRef: ${navRef.path}" }
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
        Route("/comments/*", PostCommentView)
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
    val (uid) = useParams()
    val navRef = useNavigateReference()
//    val uid = useOptionalParam("uid").getOr("12")
    useEffectOnce {
        fetchAsync("https://jsonplaceholder.typicode.com/posts/$uid/comments").then {
            it.jsonAsync().unsafeCast<Array<PostComment>>()
        }.then { setComments(it) }
    }

    div { +"NavRef: ${navRef.path}" }
    hr {}
    Routes {
        Route("/") {
            comments.forEach { comment ->
                div {
                    div { +"Name: ${comment.name}" }
                    div { +"Email: ${comment.email}" }
                    div { +"Comment: ${comment.body}" }
                }
                hr {}
            }
            A {
                to = "/posts/${uid}/comments/${Random.nextInt(0..10)}/reply"
                +"View Comment Reply"
            }
            br {}

            A {
                to = "/posts/${uid}/comments/${Random.nextInt(0..10)}/unknown"
                +"Go rogue"
            }
        }
        Route("/{cid}/reply", CommentReply)
        Route("*") {
            h1 { +"Invalid route ya'll" }
        }
    }
}

val CommentReply = FC<Props> {
    val (uid, cid) = useParams()
    val navRef = useNavigateReference()
    h2 {
        +"Reply $cid for post $uid: ref = ${navRef.path}"
    }
}