package captain

import captain.internal.AbstractNavigator
import cinematic.MutableLive
import cinematic.singleWatchableLiveOf

class BasicNavigator(private val root: String) : AbstractNavigator() {
    override val route: MutableLive<Url> = singleWatchableLiveOf(Url(root))
    override fun current(): Url = route.value
    override fun toString() = "BasicNavigator(root=$root)"
}