@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import cinematic.Live
import kiota.Url
import kotlinx.JsExport
import kotlinx.JsName

interface Navigator {
    val route: Live<Url>
    fun state(): Any? = null
    fun current(): Url

    /**
     * Navigate to the specified [path].
     *
     * @param path The path to navigate to
     * @param record If true, the navigation will be recorded in the history stack.
     *               If false, it will not record the navigation, prohibiting the user from going back to it.
     */
    @JsName("navigateWithState")
    fun navigate(path: String, record: Boolean = true, state: Any?)

    fun navigate(path: String, record: Boolean = true)

    fun go(steps: Int)
}