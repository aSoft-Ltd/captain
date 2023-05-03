@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import cinematic.Live
import kotlin.js.JsExport

interface Navigator {
    val destination: Live<String>
    fun navigate(path: String)
}