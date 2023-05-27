package captain

import react.ChildrenBuilder
import react.ReactNode
import react.createContext
import web.console.console

internal val NavigatorContext = createContext<Navigator>()

internal fun ChildrenBuilder.withNavigator(
    navigator: Navigator?,
    builder: ChildrenBuilder.() -> Unit
) {
    if (navigator != null) {
        console.log("Settings navigator")
        NavigatorContext.Provider(navigator) {
            this.builder()
        }
    } else {
        console.log("Navigator is null")
        builder()
    }
}

internal val routingMap = mutableMapOf<Url, ReactNode>()