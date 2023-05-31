package captain.internal

import captain.DynamicParamMatch
import captain.ExactMatch
import captain.SegmentMatch
import captain.Url
import captain.UrlMatch
import captain.WildCardMatch

internal class UrlImpl(
    override val protocol: String,
    override val domain: String,
    override val segments: List<String>
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
                segments = paths
            )
        }
    }

    override fun toString() = "$root$path"

    override fun equals(other: Any?): Boolean = when (other) {
        is String -> toString() == other
        is Url -> toString() == other.toString()
        else -> false
    }

    override fun hashCode() = toString().hashCode()

    private fun String.toPaths() = split("/").filter { it.isNotBlank() }

    override fun sibling(url: String): Url {
        if (segments.isEmpty()) return this
        val p = (segments - segments.last()) + url.toPaths()
        return UrlImpl(protocol, domain, p)
    }

    override fun at(path: String): Url = UrlImpl(protocol, domain, path.toPaths())

    override fun child(url: String): Url = UrlImpl(protocol, domain, segments + url.split("/").filterNot { it.isEmpty() })

    override val path = "/${segments.joinToString(separator = "/")}"
    override fun trail(): Url = Url(path)

    override fun resolve(path: String): Url = when {
        path.startsWith("/") -> at(path)
        path.startsWith("./") -> child(path.replace("./", ""))
        segments.isEmpty() -> at(path)
        else -> sibling(path)
    }

    override val root = buildString {
        if (protocol.isNotBlank()) {
            append(protocol)
            append("://")
        }
        if (domain.isNotBlank()) {
            append(domain)
        }
    }

    override fun matches(path: String): UrlMatch? = matches(Url(path))
    override fun matches(url: Url): UrlMatch? {
        val p = when {
            url.segments.size >= segments.size -> segments
            else -> url.segments
        }
        val pathMatches = mutableListOf<SegmentMatch>()
        for (i in p.indices) {
            val match = segments[i].matches(url.segments[i]) ?: return null
            pathMatches.add(match)
        }
        return UrlMatch(trail(), url.trail(), pathMatches)
    }

    private fun String.matches(configPath: String): SegmentMatch? {
        if (configPath == "*") return WildCardMatch(this)
        if (configPath.startsWith(":")) {
            val param = configPath.substringAfter(":")
            return DynamicParamMatch(this, param, this)
        }
        if (configPath.startsWith("{")) {
            val param = configPath.removePrefix("{").removeSuffix("}")
            return DynamicParamMatch(this, param, this)
        }
        if (configPath == this) return ExactMatch(this)
        return null
    }
}