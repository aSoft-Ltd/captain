package captain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect

@Composable
fun Router(
    start: String = "/",
    navigator: Navigator = BasicNavigator(start),
    content: @Composable () -> Unit
) {
    if (start != navigator.current().trail()) navigator.navigate(start)
    CompositionLocalProvider(LocalNavigator provides navigator) {
        content()
    }
}