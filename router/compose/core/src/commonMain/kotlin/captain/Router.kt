package captain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun Router(
    start: String = "/",
    navigator: Navigator = BasicNavigator(start),
    content: @Composable () -> Unit
) {
    if (start != navigator.current().path) navigator.navigate(start)
    CompositionLocalProvider(LocalNavigator provides navigator) {
        content()
    }
}