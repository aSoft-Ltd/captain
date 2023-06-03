package captain

import react.FC
import react.Props
import react.dom.html.ReactHTML.section
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.div

val people = listOf("Raiden", "Goro", "Peter")

external interface PeopleProps : Props {
    var heading: String
}

val People = FC<PeopleProps>("People") { props ->
//    val name = useParam("name")
    val (name) = useParams()
    section {
        h3 {
            +props.heading
        }

        div {
            div { +"id  : ${people.indexOf(name)}" }
            div { +"name: $name" }
        }
    }

}