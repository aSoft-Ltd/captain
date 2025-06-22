package captain

import cinematic.MutableLive
import cinematic.mutableLiveOf
import kiota.Url
import kollections.LinearlyTraversableStack
import kollections.traversableStackOf

class BasicNavigator(private val root: String) : Navigator {
    override val route: MutableLive<Url> = mutableLiveOf(Url(root), 0)

    private val history: LinearlyTraversableStack<Url> = traversableStackOf()

    private val states = mutableMapOf<Url,Any?>()

    init {
        history.insertTrimmingTop(current())
    }

    override fun current(): Url = route.value

    override fun navigate(path: String, record: Boolean, state: Any?) {
        val url = current().resolve(path)
        route.value = url
        states[url] = state
        if (!record) return
        history.insertTrimmingTop(url)
    }

    override fun state(): Any? = states[current()]

    override fun go(steps: Int) {
        val url = history.go(steps) ?: return
        route.value = url
    }

    fun canGo(steps: Int) = history.canGo(steps)

    override fun toString() = "BasicNavigator(root=$root)"
}