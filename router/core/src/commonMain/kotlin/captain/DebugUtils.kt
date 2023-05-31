package captain

internal fun indent(spaces: Int = 0): String {
    var blanks = ""
    repeat(spaces) {
        blanks = "$blanks "
    }
    return blanks
}