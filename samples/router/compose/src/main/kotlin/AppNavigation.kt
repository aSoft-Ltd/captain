import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import captain.rememberNavigate
import captain.rememberNavigator

@Composable
fun AppNavigation() = Row(horizontalArrangement = Arrangement.Center) {
    val navigate = rememberNavigate()
    val navigator = rememberNavigator()
    Button(onClick = { navigate("/") }) { Text("Home") }
    Button(onClick = {
        navigator.navigate("/about", state = people.random())
    }
    ) { Text("About") }
    Button(onClick = { navigate("/info") }) { Text("Info") }
    Button(onClick = { navigate("/heroes") }) { Text("Heroes") }
    Button(onClick = { navigate("/villains") }) { Text("Villains") }
    Button(onClick = { navigate("/test") }) { Text("Test") }
    Button(onClick = { navigate(-1) }) { Text("< Prev") }
    Button(onClick = { navigate(1) }) { Text("Next >") }
}