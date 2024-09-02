import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.singleWindowApplication
import captain.*
import cinematic.watchAsState
import kotlin.random.Random

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
                Route("/heroes/*") { Heroes() }
                Route("/villains/*") { Villains() }
                Route("/info") { Navigate("/about") }
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
fun Heroes() = Routes {
    Route("/") {
        val navigate = rememberNavigate()
        LazyColumn {
            items(people) {
                Text(it.name, modifier = Modifier.clickable {
//                    navigate(it.name)
                    navigate(it.name + "?page=${Random.nextInt()}")
                })
            }
        }
    }
    Route("{name}") { (name) ->
        val page by rememberQueryParams().intOr(0)
        Column {
            val person = people.first { it.name == name }
            Text("page: $page")
            Text("Name: ${person.name}")
            Text("Power: ${person.power}")
        }
    }
}

@Composable
fun Villains() = Routes {
    Route("/") {
        val navigate = rememberNavigate()
        LazyColumn {
            items(people) {
                Text(it.name, modifier = Modifier.clickable {
                    navigate(it.name)
                })
            }
        }
    }
    Route("{name}") {
        val name by rememberParams()
        Column {
            val person = people.first { it.name == name }
            Text("Name: ${person.name}")
            Text("Power: ${person.power}")
        }
    }
}