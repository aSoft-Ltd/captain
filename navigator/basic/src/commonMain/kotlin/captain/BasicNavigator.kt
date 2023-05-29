package captain

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kollections.LinearlyTraversableStack
import kollections.traversableStackOf

class BasicNavigator(private val root: String) : Navigator {
    override val route: MutableLive<Url> = mutableLiveOf(Url(root), 0)

    private val history: LinearlyTraversableStack<Url> = traversableStackOf()

    init {
        history.insertTrimmingTop(current())
    }

    override fun current(): Url = route.value

    override fun navigate(path: String) {
        val url = current().resolve(path)
        route.value = url
        history.insertTrimmingTop(url)
    }

    override fun go(steps: Int) {
        val url = history.go(steps) ?: return
        route.value = url
    }

    override fun toString() = "BasicNavigator(root=$root)"
}