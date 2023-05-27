package captain

import react.create
import react.dom.client.createRoot
import web.dom.document
import web.html.HTMLDivElement

fun main() {
    val root = document.getElementById("root") as HTMLDivElement
    createRoot(root).render(App.create())
}