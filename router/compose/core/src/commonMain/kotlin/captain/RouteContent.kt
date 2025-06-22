package captain

import androidx.compose.runtime.Composable
import kollections.List

class RouteContent(
    var state: Any? = null,
    val render: @Composable RouteContent.(params: List<String>) -> Unit
)