package captain.internal

import captain.Url

internal class UrlImpl(
    override val protocol: String,
    override val domain: String,
    override val paths: List<String>
) : Url {
    companion object {

        private val String.isDomainLike: Boolean
            get() = contains(".") || (contains(":") && !startsWith(":"))

        operator fun invoke(value: String): Url {
            var protocol = value.split("://").getOrNull(0) ?: ""
            val lessProtocol = if (protocol == value) {
                protocol = ""
                value
            } else {
                value.replace("$protocol://", "")
            }
            val segments = lessProtocol.replace("//", "/").split("/").filter {
                it.isNotBlank()
            }
            val domain = segments.firstOrNull { it.isDomainLike } ?: ""
            val paths = segments - domain
            return UrlImpl(
                protocol = protocol,
                domain = domain,
                paths = paths
            )
        }
    }

    override fun toString() = buildString {
        append(root())
        append(trail())
    }

    override fun equals(other: Any?): Boolean = when (other) {
        is String -> toString() == other
        is Url -> toString() == other.toString()
        else -> false
    }

    private fun String.toPaths() = split("/").filter { it.isNotBlank() }

    override fun sibling(url: String): Url {
        if (paths.isEmpty()) return this
        val p = (paths - paths.last()) + url.toPaths()
        return UrlImpl(protocol, domain, p)
    }

    override fun at(path: String): Url = UrlImpl(protocol, domain, path.toPaths())

    override fun child(url: String): Url = UrlImpl(protocol, domain, paths + url.split("/").filterNot { it.isEmpty() })

    override fun trail(): String = buildString {
        append("/")
        append(paths.joinToString(separator = "/"))
    }

    override fun root() = buildString {
        if (protocol.isNotBlank()) {
            append(protocol)
            append("://")
        }
        if (domain.isNotBlank()) {
            append(domain)
        }
    }
}