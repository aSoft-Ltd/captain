package captain.internal

import captain.Navigator
import cinematic.mutableLiveOf

@PublishedApi
internal class NavigatorImpl(private val root: String) : Navigator {
    override val destination = mutableLiveOf(root)
    override fun navigate(path: String) {
        destination.value = path
    }
}