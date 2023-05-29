package captain.internal

import captain.Navigator
import captain.Url

abstract class AbstractNavigator : Navigator {

    protected fun resolve(path: String): Url {
        val current = current()
        return when {
            path.startsWith("/") -> current.at(path)
            path.startsWith("./") -> current.child(path.replace("./", ""))
            current.paths.isEmpty() -> current.at(path)
            else -> current.sibling(path)
        }
    }
}