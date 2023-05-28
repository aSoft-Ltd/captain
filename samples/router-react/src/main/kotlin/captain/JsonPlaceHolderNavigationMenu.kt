package captain

import react.FC
import react.Props
import react.dom.html.ReactHTML.button

val JsonPlaceHolderNavigationMenu = FC<Props>("NavigationMenu") {
    val nav = useNavigator()
    button {
        onClick = { nav.navigate("/") }
        +"/home"
    }

    button {
        onClick = { nav.navigate("/posts") }
        +"/posts"
    }

    button {
        onClick = { nav.navigate("/users") }
        +"/users"
    }

    button {
        onClick = { nav.navigate("/photos") }
        +"photos"
    }
}