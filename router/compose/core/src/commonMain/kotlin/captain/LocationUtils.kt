@file:Suppress("NOTHING_TO_INLINE")

package captain

import androidx.compose.runtime.Composable

@Composable
inline fun rememberLocation() = rememberNavigator().current()