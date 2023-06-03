package captain.internal

import captain.DynamicParamMatch
import captain.ExactMatch
import captain.SegmentMatch
import captain.Url
import captain.UrlMatch
import captain.WildCardMatch
import kollections.toIList

@PublishedApi
internal class UrlImpl(
    override val scheme: String,
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
                scheme = protocol,
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
        return UrlImpl(scheme, domain, p)
    }

    override fun at(path: String): Url = UrlImpl(scheme, domain, path.toPaths())

    override fun child(url: String): Url = UrlImpl(scheme, domain, segments + url.split("/").filterNot { it.isEmpty() })

    override val path = "/${segments.joinToString(separator = "/")}"
    override fun trail(): Url = Url(path)

    override fun resolve(path: String): Url = when {
        path.startsWith("/") -> at(path)
        path.startsWith(".") -> fileSystemLikeResolve(path)
        segments.isEmpty() -> at(path)
        else -> child(path)
    }

    private fun fileSystemLikeResolve(path: String): Url {
        var out = segments
        for (segment in path.split("/")) when {
            segment == "." -> {}
            segment == ".." -> out = out - (out.lastOrNull() ?: "")
            else -> out = out + segment
        }
        return UrlImpl(scheme, domain, out)
    }

    override val root = buildString {
        if (scheme.isNotBlank()) {
            append(scheme)
            append("://")
        }
        if (domain.isNotBlank()) {
            append(domain)
        }
    }

    override fun rebase(url: Url): Url {
        val matchingSegments = mutableSetOf<String>()
        for (s in url.segments.indices) {
            val patternSegment = segments.getOrNull(s) ?: break
            if (patternSegment == "*" && s == segments.indices.last) break
            val routeSegment = url.segments.getOrNull(s) ?: break
            if (routeSegment.matches(patternSegment) == null) break
            matchingSegments.add(routeSegment)
        }
        return Url(url.scheme, url.domain, *(url.segments - matchingSegments).toTypedArray())
    }

    override fun rebase(url: String) = rebase(UrlImpl(url))

    override fun matches(pattern: String): UrlMatch? = matches(UrlImpl(pattern))
    override fun matches(pattern: Url): UrlMatch? {
        if (pattern.path == "/" && path != "/") return null
        val p = when {
            pattern.segments.size >= segments.size -> segments
            else -> pattern.segments
        }
        val pathMatches = mutableListOf<SegmentMatch>()
        for (i in p.indices) {
            val match = segments[i].matches(pattern.segments[i]) ?: return null
            pathMatches.add(match)
        }
        return UrlMatch(trail(), pattern.trail(), pathMatches.toIList())
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