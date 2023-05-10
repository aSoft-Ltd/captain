package captain.internal

import captain.Navigator
import captain.Url
import cinematic.MutableLive

abstract class AbstractNavigator : Navigator {

    abstract override val route: MutableLive<Url>

    override fun navigate(path: String) {
        val current = current()
        route.value = when {
            path.startsWith("/") -> current.at(path)
            current.paths.isEmpty() -> current.at(path)
            else -> current.sibling(path)
        }
    }
}