@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import cinematic.Live
import kiota.Url
import kotlinx.JsExport

interface Navigator {
    val route: Live<Url>
    fun current(): Url
    fun navigate(path: String)
    fun go(steps: Int)
}