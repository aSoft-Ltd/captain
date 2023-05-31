@file:JsExport
@file:Suppress("UNUSED_PARAMETER", "NON_EXPORTABLE_TYPE")

package captain

import kase.Optional

fun useRouteParams(): RouteMatch = useRouteInfo()?.match ?: error("useRouteParams can only be used inside a <Route> component")

fun useOptionalParam(key: String): Optional<String> {
    val params = useRouteParams()
    return params.get(key)
}

fun useParams(key: String): String {
    val params = useOptionalParam(key)
    return params.getOrThrow()
}