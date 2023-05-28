import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import captain.rememberNavigator

@Composable
fun AppNavigation() = Row(horizontalArrangement = Arrangement.Center) {
    val nav = rememberNavigator()
    Button(onClick = { nav.navigate("/") }) { Text("Home") }
    Button(onClick = { nav.navigate("/about") }) { Text("About") }
    Button(onClick = { nav.navigate("/customers") }) { Text("Customers") }
    Button(onClick = { nav.navigate("/test") }) { Text("Test") }
}