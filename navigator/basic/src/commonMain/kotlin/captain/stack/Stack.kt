@file:JsExport

package captain.stack

import kotlinx.JsExport

interface Stack<E> : Collection<E> {
    fun top(): E?
    fun push(element: E)
    fun pop(): E?
    fun canPop(): Boolean

    fun toList(): List<E>
}