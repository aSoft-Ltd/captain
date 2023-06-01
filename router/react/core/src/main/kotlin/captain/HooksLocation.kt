@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE", "NOTHING_TO_INLINE")

package captain

inline fun useLocation(): Url = useNavigator().current()