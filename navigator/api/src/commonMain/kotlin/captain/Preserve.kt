package captain

enum class Preserve {
    /**
     * Preserve nothing
     */
    None,

    /**
     * Preserve the state assigned to the url
     */
    StateOnly,

    /**
     * Preserve the query params
     */
    QueryOnly,

    /**
     * Preserve both, the state and the query paramas while navigating
     */
    Both
}