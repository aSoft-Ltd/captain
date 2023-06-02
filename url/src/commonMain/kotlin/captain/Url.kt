@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package captain

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * A representation of a Url
 */
interface Url {
    /**
     * The scheme of the url (i.e. https, http)
     * e.g. For a url with https://test.com/john/doe
     * scheme should give you "https"
     */
    val scheme: String

    /**
     * The domain of the url (i.e. test.com)
     * e.g. For a url with https://test.com/john/doe
     * domain should give you "test.com"
     */
    val domain: String

    /**
     * The list of the path segments of the url
     * e.g. For a url with https://test.com/john/doe
     * segments should give you ["john","doe"]
     */
    val segments: List<String>

    /**
     * The root (scheme + domain) of the url
     * e.g. For a url with https://test.com/john/doe
     * root should give you "https://test.com"
     */
    val root: String

    /**
     * The path url without the domain and scheme
     * e.g. For a url with https://test.com/john/doe
     * path should give you "john/doe"
     */
    val path: String

    fun at(path: String): Url
    fun child(url: String): Url
    fun sibling(url: String): Url

    fun resolve(path: String): Url

    @JsName("rebaseUrl")
    fun rebase(url: Url): Url

    fun rebase(url: String) = rebase(Url(url))

    fun trail(): Url

    fun matches(pattern: String): UrlMatch?

    @JsName("matchesUrl")
    fun matches(pattern: Url): UrlMatch?
}