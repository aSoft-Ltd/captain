@file:JsExport
@file:Suppress("NOTHING_TO_INLINE")

package captain.stack

import captain.stack.internal.LinearlyTraversableStackImpl
import kotlinx.JsExport

inline fun <E> traversableStackOf(): LinearlyTraversableStack<E> = LinearlyTraversableStackImpl()