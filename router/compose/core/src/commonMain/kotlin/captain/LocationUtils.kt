package captain

import androidx.compose.runtime.Composable

@Composable
fun rememberLocation() = rememberNavigator().current()