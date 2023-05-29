@file:JsExport
package captain

import react.PropsWithChildren

external interface LinkProps : PropsWithChildren {
    var to: String
}