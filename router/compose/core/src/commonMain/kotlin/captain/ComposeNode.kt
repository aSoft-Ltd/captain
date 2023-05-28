package captain

import androidx.compose.runtime.Composable

typealias ComposeNode = @Composable (params: List<String>) -> Unit

//sealed interface ComposeNode {
//    class F0(val handler: @Composable () -> Unit) : ComposeNode
//    class F1(val handler: @Composable (String) -> Unit) : ComposeNode
//    class F2(val handler: @Composable (String, String) -> Unit) : ComposeNode
//    class F3(val handler: @Composable (String, String, String) -> Unit) : ComposeNode
//    class F4(val handler: @Composable (String, String, String, String) -> Unit) : ComposeNode
//}