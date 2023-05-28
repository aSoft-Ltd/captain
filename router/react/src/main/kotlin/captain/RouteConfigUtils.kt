package captain

import react.ReactNode
import react.createContext

internal val RouteConfigContext = createContext<MutableSet<RouteConfig<ReactNode>>>()