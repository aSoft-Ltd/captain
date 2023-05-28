package captain

import react.FC
import react.Props
import react.dom.html.ReactHTML.button

val NavigationMenu = FC<Props>("NavigationMenu") {
    val nav = useNavigator()
    button {
        onClick = { nav.navigate("/") }
        +"/home"
    }
    listOf("about", "settings").forEach { path ->
        button {
            onClick = { nav.navigate("/$path") }
            +"/$path"
        }
    }

    for (entity in listOf("customer", "champion")) {
        people.forEach { path ->
            button {
                onClick = { nav.navigate("/$entity/$path") }
                +"/$entity/$path"
            }
        }
    }
}