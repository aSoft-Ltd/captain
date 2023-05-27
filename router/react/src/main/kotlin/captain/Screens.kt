@file:JsExport

package captain

import react.PropsWithChildren
import react.ReactNode
import react.useEffectOnce
import react.useState
import web.console.console

external interface ScreensProps : PropsWithChildren

fun Screens(props: ScreensProps): Any? {
    val (mounted, setMounted) = useState(false)
//    useEffectOnce {
//        setMounted(true)
//        cleanup { setMounted(false) }
//    }

//    screens<ReactNode> {
//        props.children
//    }
    console.log("mounted", mounted)
    return undefined
}

external interface ScreenProps : PropsWithChildren

fun Screen(props: ScreenProps) {

}