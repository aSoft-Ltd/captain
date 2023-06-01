package captain

import captain.internal.UrlImpl

fun Url(path: String): Url = UrlImpl(path)

fun Url(
    scheme: String = "",
    domain: String = "",
    vararg paths: String
): Url = UrlImpl(scheme, domain, paths.toList())