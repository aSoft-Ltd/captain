@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import cinematic.Live
import kotlin.js.JsExport

interface Navigator {
    val route: Live<Url>
    fun current(): Url
    fun navigate(path: String)
}