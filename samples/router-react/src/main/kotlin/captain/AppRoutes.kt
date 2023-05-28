package captain

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.section

val Home = FC<Props> {
    section {
        div { +"Home page" }
    }
}

val About = FC<Props> {
    section {
        div { +"This is about" }
    }
}

val Settings = FC<Props> {
    section {
        div { +"Settings Page" }
    }
}