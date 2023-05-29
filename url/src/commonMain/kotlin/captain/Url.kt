@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import kotlin.js.JsExport

interface Url {
    val protocol: String
    val domain: String
    val paths: List<String>

    fun at(path: String): Url
    fun child(url: String): Url
    fun sibling(url: String): Url

    fun resolve(path: String): Url

    fun root(): String
    fun trail(): String
}