package captain.internal

external interface Window {
    var onpopstate: () -> Unit
    var location: Location
    val history: History
}

external val window: Window