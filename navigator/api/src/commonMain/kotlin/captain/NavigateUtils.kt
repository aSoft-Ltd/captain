package captain

typealias NavigateFunction = (arg: Any) -> Unit

fun NavigateFunction(navigator: Navigator): NavigateFunction = { arg: Any ->
    when (arg) {
        is String -> navigator.navigate(arg)
        is Int -> navigator.go(arg)
        else -> throw UnsupportedOperationException("Unsupported argument type for navigation")
    }
}