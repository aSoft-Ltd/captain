@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

fun useLocation(): Url = useNavigator().current()