import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.singleWindowApplication
import captain.BasicNavigator
import captain.Router
import captain.Routes
import captain.rememberNavigator
import captain.rememberOptionalParams
import cinematic.watchAsState
import kase.None
import kase.Some

fun main() = singleWindowApplication {
    Router("/", BasicNavigator("app://captain.com")) {
        Column {
            val nav = rememberNavigator()
            val route = nav.route.watchAsState()
            Text("$route")
            AppNavigation()
            Routes {
                Route("/") { Text("Home") }
                Route("/about") { Text("About") }
                Route("/test") { Text("Test") }
                Route("/customers/*") { Customers() }
            }
        }
    }
}

class Person(val name: String, val power: String)

val people = mapOf(
    "Raiden" to "Thunder",
    "Scorpion" to "Fire",
    "Subzero" to "Ice",
    "Fujin" to "Wind"
).entries.map { Person(it.key, it.value) }

@Composable
fun Customers() = Routes {
    Route("/") {
        val nav = rememberNavigator()
        LazyColumn {
            items(people) {
                Text(it.name, modifier = Modifier.clickable {
                    nav.navigate("./${it.name}")
                })
            }
        }
    }
    Route("{name}") { (name)->
        Column {
            val person = people.first { it.name == name }
            Text("Name: ${person.name}")
            Text("Power: ${person.power}")
        }
//        when (val name = rememberOptionalParams("name")) {
//            is None -> Text("No param with name")
//            is Some -> Column {
//                val person = people.first { it.name == name.value }
//                Text("Name: ${person.name}")
//                Text("Power: ${person.power}")
//            }
//        }
    }
}