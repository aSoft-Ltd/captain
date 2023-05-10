package captain

import captain.internal.UrlImpl

fun Url(path: String): Url = UrlImpl(path)

fun Url(
    protocol: String = "",
    domain: String = "",
    vararg paths: String
): Url = UrlImpl(protocol, domain, paths.toList())