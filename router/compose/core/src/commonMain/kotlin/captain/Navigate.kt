package captain

import androidx.compose.runtime.Composable

@Composable
fun Navigate(to: String) {
    val navigator = rememberNavigator()
    navigator.navigate(to)
}