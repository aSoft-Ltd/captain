@file:JsExport

package captain.stack

import kotlinx.JsExport

interface LinearlyTraversableStack<E> : Stack<E>, LinearlyTraversable<E> {
    /**
     * Inserts an element into the stack cursor position and pops out all elements that came after the cursor position
     */
    fun insertTrimmingTop(element: E)

    fun insertTrimmingBottom(element: E)

    /**
     * Pushes an element to the stack and moves the internal cursor to point to it
     */
    fun insert(element: E)
}