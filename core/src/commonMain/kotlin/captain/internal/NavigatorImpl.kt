package captain.internal

import captain.Navigator
import cinematic.singleWatchableLiveOf

@PublishedApi
internal class NavigatorImpl(private val root: String) : Navigator {
    override val destination = singleWatchableLiveOf(root)
    override fun navigate(path: String) {
        destination.value = path
    }
}