package captain

class NavigateOptions(
    /**
     * If true, the navigation will be recorded in the history
     */
    val record: Boolean = true,
    /**
     * The state to be associated with the navigation
     */
    val state: Any? = null,
    /**
     * Choose things to preserve when navigating
     */
    val preserve: Preserve = Preserve.None
)