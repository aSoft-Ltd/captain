package captain.internal

fun Map<*, *>.pretty() = toList().joinToString(prefix = "[", separator = " , ", postfix = "]") {
    "${it.first}=${it.second}"
}

fun indent(spaces: Int = 0): String {
    var blanks = ""
    repeat(spaces) {
        blanks = "$blanks "
    }
    return blanks
}