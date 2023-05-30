package captain

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.A as Anchor

@Composable
fun A(to: String, content: @Composable () -> Unit) {
    val navigate = rememberNavigate()
    Anchor(href = to, attrs = {
        onClick { event ->
            event.preventDefault()
            navigate(to)
        }
    }) { content() }
}