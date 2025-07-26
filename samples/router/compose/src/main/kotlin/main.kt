import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.singleWindowApplication
import captain.BasicNavigator
import captain.Navigate
import captain.Route
import captain.Router
import captain.Routes
import captain.rememberNavigate
import captain.rememberNavigator
import captain.rememberParams
import captain.rememberQueryParams
import cinematic.watchAsState
import kotlin.random.Random

data class Pet(
    val uid: String,
    val name: String
) {
    companion object {
        val names = listOf("Dog", "Cat", "Fish", "Bird")
        fun random() = Pet(
            uid = Random.nextInt(10).toString(),
            name = names.random()
        )

        fun pets(count: Int = 10) = List(count) { random() }
    }
}

fun main() = singleWindowApplication {
    val pages = listOf("Home", "1", "2", "3", "Pets", "Settings")
    val pets = Pet.pets()
    Router("/chris/anderson") {
        Column {
            Menu(base = "", pages)
            val navigator = rememberNavigator()
            var age by remember { mutableStateOf("45") }
            var gender by remember { mutableStateOf("male") }

            TextField(value = age, onValueChange = { age = it })
            TextField(value = gender, onValueChange = { gender = it })

            Button(
                onClick = { navigator.navigate("/chris/joe?age=$age&gender=$gender") },
            ) {
                Text("/chris/joe?age=$age&gender=$gender")
            }
            Routes {
                Route("/edibert") {
                    Text("Welcome to Edibert's page")
                }
                Route("/chris") {
                    Routes {
                        Route("/joe") {
                            val params = rememberQueryParams()
                            val age by params
                            val gender by params
                            val search by params
                            Text("Welcome to Joe's page")
                            Text("age = $age")
                            Text("gender = $gender")
                            Text("search = $search")
                        }
                        Route("/edibert") {
                            Text("Welcome to Edibert's page")
                        }
                        Route("/*") {
                            Text("Welcome to Chris's page with age")
                        }
                    }
                }
                Route("*") {
                    Text("Page not found")
                }
            }
        }
    }
}

@Composable
fun Menu(base: String, pages: List<String>) = Row {
    val navigate = rememberNavigate()
    for (page in pages) Button(onClick = {
        val destination = "$base/${page.lowercase()}"
        navigate(destination)
    }) {
        Text(page)
    }
}


fun oldmain() = singleWindowApplication {
    Router("/", BasicNavigator("app://captain.asoft.co.tz")) {
        Column {
            val nav = rememberNavigator()
            val route = nav.route.watchAsState()
            Text("${route.scheme}")
            AppNavigation()
            Routes {
                Route("/") { Text("Home") }
                Route("/about") {
                    Column {
                        Text("About")
                        val p = state as? Person
                        if (p == null) Text("No person provided")
                        else Person(p)
                    }
                }
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
            Person(person)
        }
    }
}

@Composable
fun Person(p: Person) = Column {
    Text("Name: ${p.name}")
    Text("Power: ${p.power}")
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