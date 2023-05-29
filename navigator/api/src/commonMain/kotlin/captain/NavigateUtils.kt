package captain

typealias StringOrInt = Any
typealias NavigateFunction = (arg: StringOrInt) -> Unit

fun NavigateFunction(navigator: Navigator): NavigateFunction = { arg: Any ->
    when (arg) {
        is String -> navigator.navigate(arg)
        is Int -> navigator.go(arg)
        else -> throw UnsupportedOperationException("Unsupported argument type for navigation")
    }
}