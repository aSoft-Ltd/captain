package captain

import captain.stack.LinearlyTraversableStack
import captain.stack.traversableStackOf
import cinematic.MutableLive
import cinematic.mutableLiveOf
import kiota.Url

class BasicNavigator(private val root: String) : Navigator {
    override val route: MutableLive<Url> = mutableLiveOf(Url(root), 0)

    private val history: LinearlyTraversableStack<Url> = traversableStackOf()

    private val states = mutableMapOf<Url, Any?>()

    init {
        history.insertTrimmingTop(current())
    }

    override fun current(): Url = route.value

    override fun navigate(path: String, options: NavigateOptions) {
        val url = current().resolve(path, options.preserve == Preserve.QueryOnly || options.preserve == Preserve.Both)
        route.value = url
        val state = options.state
        if (state != null) {
            states[url] = state
        } else if (options.preserve == Preserve.None || options.preserve == Preserve.QueryOnly) {
            states.remove(url)
        }
        if (!options.record) return
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