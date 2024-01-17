package captain.internal

external interface History {
    fun pushState(state: String?,title: String, url: String)
    fun go(steps: Int)
}