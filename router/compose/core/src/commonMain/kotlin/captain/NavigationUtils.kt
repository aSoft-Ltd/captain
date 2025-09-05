@file:Suppress("NOTHING_TO_INLINE")

package captain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import kiota.Url

val LocalNavigator = compositionLocalOf<Navigator> { BasicNavigator("/") }

@PublishedApi
internal val LocalNavigateReference = compositionLocalOf { Url("/") }

@Composable
inline fun rememberNavigator(): Navigator = LocalNavigator.current

@Composable
inline fun Navigate(to: String, record: Boolean = false) {
    val navigator = LocalNavigator.current
    navigator.navigate(to, record)
}

@Composable
inline fun rememberNavigate(): NavigateFunction = NavigateFunction(LocalNavigator.current, LocalNavigateReference.current)