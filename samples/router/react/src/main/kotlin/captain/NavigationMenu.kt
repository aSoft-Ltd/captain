package captain

import react.FC
import react.Props
import react.dom.html.ReactHTML.button

val NavigationMenu = FC<Props>("NavigationMenu") {
    val navigate = useNavigate()
    button {
        onClick = { navigate("/") }
        +"/home"
    }
    listOf("about", "settings").forEach { path ->
        button {
            onClick = { navigate("/$path") }
            +"/$path"
        }
    }

    for (entity in listOf("customer", "champion")) {
        people.forEach { path ->
            button {
                onClick = { navigate("/$entity/$path") }
                +"/$entity/$path"
            }
        }
    }
}