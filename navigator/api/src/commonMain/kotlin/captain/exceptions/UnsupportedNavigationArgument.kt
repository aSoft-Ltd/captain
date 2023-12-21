@file:JsExport

package captain.exceptions

import kotlinx.JsExport

class UnsupportedNavigationArgument(val arg: Any) : IllegalArgumentException(
    "Unsupported argument '$arg' of type ${arg::class.simpleName} when calling navigate()"
)