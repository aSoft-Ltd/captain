package captain

import captain.exceptions.UnsupportedNavigationArgument

typealias StringOrInt = Any
typealias NavigateFunction = (arg: StringOrInt) -> Unit

/**
 * A simplified function to make navigating on relative and nested routes, easier
 *
 * @param from the reference url to navigate from. Defaults to [Navigator.current]
 *
 * @return a [NavigateFunction] [from] whence this call should base it's transition of
 */
fun NavigateFunction(navigator: Navigator, from: Url = navigator.current()): NavigateFunction = { arg ->
    when (arg) {
        is String -> navigator.navigate(from.resolve(arg).path)
        is Int -> navigator.go(arg)
        else -> throw UnsupportedNavigationArgument(arg)
    }
}

private val VoidNavigateFunction: NavigateFunction = { arg ->
    when (arg) {
        is String, is Int -> println("""called navigate("$arg")""")
        else -> throw UnsupportedNavigationArgument(arg)
    }
}

fun NavigateFunction(): NavigateFunction = VoidNavigateFunction