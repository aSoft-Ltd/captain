package captain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

internal val LocalNavigator = compositionLocalOf<Navigator> { BasicNavigator("/") }

@Composable
fun rememberNavigator(): Navigator = LocalNavigator.current