package captain

class RoutingTreeBuilder {
    val entries = mutableListOf<Url>()
    fun route(path: String) {
        entries.add(Url(path))
    }

    fun routes(path: String, builder: RoutingTreeBuilder.() -> Unit) {
        val rtb = RoutingTreeBuilder().apply(builder)
        entries.addAll(rtb.entries.map { Url(path).child(it.trail()) })
    }
}

fun routes(builder: RoutingTreeBuilder.() -> Unit = {}): RoutingTree {
    val rtb = RoutingTreeBuilder().apply(builder)
    return RoutingTree(rtb.entries)
}