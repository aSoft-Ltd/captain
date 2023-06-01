@file:Suppress("NOTHING_TO_INLINE")

package captain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

@PublishedApi
internal val LocalNavigator = compositionLocalOf<Navigator> { BasicNavigator("/") }

@Composable
inline fun rememberNavigator(): Navigator = LocalNavigator.current

@Composable
inline fun Navigate(to: String) = rememberNavigator().navigate(to)

@Composable
inline fun rememberNavigate(): NavigateFunction = NavigateFunction(rememberNavigator())