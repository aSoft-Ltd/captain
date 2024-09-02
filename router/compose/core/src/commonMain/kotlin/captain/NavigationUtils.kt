@file:Suppress("NOTHING_TO_INLINE")

package captain

import kiota.Url
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import cinematic.watchAsState
import kiota.QueryParams

@PublishedApi
internal val LocalNavigator = compositionLocalOf<Navigator> { BasicNavigator("/") }

@PublishedApi
internal val LocalNavigateReference = compositionLocalOf { Url("/") }

@Composable
inline fun rememberNavigator(): Navigator = LocalNavigator.current

@Composable
inline fun Navigate(to: String) {
    val navigate = rememberNavigate()
    navigate(to)
}

@Composable
inline fun rememberNavigate(): NavigateFunction = NavigateFunction(LocalNavigator.current, LocalNavigateReference.current)