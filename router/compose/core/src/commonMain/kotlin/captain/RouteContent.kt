package captain

import androidx.compose.runtime.Composable
import kollections.List

typealias RouteContent = @Composable (params: List<String>) -> Unit