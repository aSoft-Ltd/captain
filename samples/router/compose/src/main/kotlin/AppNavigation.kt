import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import captain.rememberNavigate

@Composable
fun AppNavigation() = Row(horizontalArrangement = Arrangement.Center) {
    val navigate = rememberNavigate()
    Button(onClick = { navigate("/") }) { Text("Home") }
    Button(onClick = { navigate("/about") }) { Text("About") }
    Button(onClick = { navigate("/info") }) { Text("Info") }
    Button(onClick = { navigate("/customers") }) { Text("Customers") }
    Button(onClick = { navigate("/test") }) { Text("Test") }
    Button(onClick = { navigate(-1) }) { Text("< Prev") }
    Button(onClick = { navigate(1) }) { Text("Next >") }
}