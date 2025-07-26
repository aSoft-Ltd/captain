package captain

import react.FC
import react.Props
import react.dom.html.ReactHTML.button

val JsonPlaceHolderNavigationMenu = FC<Props>("NavigationMenu") {
    val navigate = useNavigate()
    button {
        onClick = { navigate("/") }
        +"/home"
    }

    button {
        onClick = { navigate("/info") }
        +"/info"
    }

    button {
        onClick = { navigate("/about") }
        +"/about"
    }

    button {
        onClick = { navigate("/posts") }
        +"/posts"
    }

    button {
        onClick = { navigate("/users") }
        +"/users"
    }

    button {
        onClick = { navigate("/photos") }
        +"photos"
    }

    button {
        onClick = { navigate(-1) }
        +"< Go Back"
    }
}