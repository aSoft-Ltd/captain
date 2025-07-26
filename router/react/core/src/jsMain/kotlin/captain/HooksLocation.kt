@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")

package captain

import kiota.Url

inline fun useLocation(): Url = useNavigator().current()