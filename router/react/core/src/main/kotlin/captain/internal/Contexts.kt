package captain.internal

import captain.Navigator
import captain.RouteInfo
import react.ReactNode
import react.createContext

@PublishedApi
internal val NavigatorContext = createContext<Navigator>()

@PublishedApi
internal val RouteInfoContext = createContext<RouteInfo<ReactNode?>>()