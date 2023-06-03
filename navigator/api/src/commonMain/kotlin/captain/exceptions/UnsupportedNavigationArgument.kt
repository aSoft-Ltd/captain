@file:JsExport

package captain.exceptions

import kotlin.js.JsExport

class UnsupportedNavigationArgument(val arg: Any) : IllegalArgumentException(
    "Unsupported argument '$arg' of type ${arg::class.simpleName} when calling navigate()"
)