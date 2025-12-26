@file:JsExport

package captain.stack

import kotlinx.JsExport

interface LinearlyTraversable<out E> {
    fun current(): E?
    fun forward(): E?
    fun backward(): E?
    fun canGoForward(): Boolean
    fun canGoBackward(): Boolean
    fun canGo(steps: Int): Boolean
    fun go(steps: Int): E?
}