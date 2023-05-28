package captain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect

@Composable
fun Router(
    home: String = "/",
    navigator: Navigator = BasicNavigator(home),
    content: @Composable () -> Unit
) {
    LaunchedEffect(Unit) {
        if (home != navigator.current().trail()) navigator.navigate(home)
    }
    CompositionLocalProvider(LocalNavigator provides navigator) {
        content()
    }
}