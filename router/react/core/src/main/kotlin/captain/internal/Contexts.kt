package captain.internal

import captain.Navigator
import captain.RouteContent
import captain.RouteInfo
import kiota.Url
import react.createContext

@PublishedApi
internal val NavigatorContext = createContext<Navigator>()

@PublishedApi
internal val RouteInfoContext = createContext<RouteInfo<RouteContent>>()

@PublishedApi
internal val NavigateReferenceContext = createContext<Url>()