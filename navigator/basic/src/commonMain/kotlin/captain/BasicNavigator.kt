package captain

import captain.internal.AbstractNavigator
import cinematic.MutableLive
import cinematic.mutableLiveOf

class BasicNavigator(private val root: String) : AbstractNavigator() {
    override val route: MutableLive<Url> = mutableLiveOf(Url(root), HISTORY_CAPACITY)
    override fun current(): Url = route.value
    override fun toString() = "BasicNavigator(root=$root)"
}