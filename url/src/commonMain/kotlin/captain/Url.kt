@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import kotlin.js.JsExport
import kotlin.js.JsName

interface Url {
    val scheme: String
    val domain: String
    val segments: List<String>

    val root: String
    val path: String

    fun at(path: String): Url
    fun child(url: String): Url
    fun sibling(url: String): Url

    fun resolve(path: String): Url

    fun trail(): Url

    fun matches(path: String): UrlMatch?

    @JsName("matchesUrl")
    fun matches(url: Url): UrlMatch?
}