package captain.internal

import captain.Navigator
import captain.RouteInfo
import captain.Url
import react.ReactNode
import react.createContext

@PublishedApi
internal val NavigatorContext = createContext<Navigator>()

@PublishedApi
internal val RouteInfoContext = createContext<RouteInfo<ReactNode?>>()

@PublishedApi
internal val NavigateReferenceContext = createContext<Url>()