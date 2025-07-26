package captain

import react.create
import react.dom.client.createRoot
import web.dom.document
import web.html.HTMLDivElement
import captain.BrowserNavigator

fun main() {
    val root = document.getElementById("root") as HTMLDivElement
    val navigator = BrowserNavigator(syncWithAddressBar = true)
    createRoot(root).render(App.create())
}