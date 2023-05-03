package captain.internal

import captain.Navigator
import cinematic.mutableLiveOf

class NavigatorImpl : Navigator {
    override val destination = mutableLiveOf("/")
    override fun navigate(path: String) {
        destination.value = path
    }
}