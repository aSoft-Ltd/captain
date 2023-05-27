package captain

class ScreensBuilder<C> {
    val entries = mutableMapOf<Url, C>()
    fun screen(path: String, builder: ScreensBuilder<C>.() -> C) {
        entries[Url(path)] = builder()
    }

    fun screens(path: String, builder: ScreensBuilder<C>.() -> Unit) {
        val sb = ScreensBuilder<C>().apply(builder)
        entries.putAll(sb.entries)
    }
}

class ScreenMap<out C>(val entries: Map<Url, C>)

fun <C> screens(builder: ScreensBuilder<C>.() -> Unit = {}): ScreenMap<C> {
    val sb = ScreensBuilder<C>().apply(builder)
    return ScreenMap(sb.entries)
}