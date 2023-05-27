package captain

class RouteInfo<out C>(
    val params: RouteMatchParams,
    val config: Url,
    val route: Url,
    val children: C
)