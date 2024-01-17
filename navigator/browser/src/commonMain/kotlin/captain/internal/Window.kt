package captain.internal

external interface Window {
    var onpopstate: () -> Unit
    var location: Localtion
    val history: History
}

external val window: Window