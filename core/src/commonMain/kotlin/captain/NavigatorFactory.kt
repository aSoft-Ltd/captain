package captain

import captain.internal.NavigatorImpl
import kotlin.js.JsName

@JsName("navigator")
inline fun Navigator(root: String = ""): Navigator = NavigatorImpl(root)