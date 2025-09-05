import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.window.singleWindowApplication
import captain.LocalNavigator
import captain.Navigate
import captain.Route
import captain.Router
import captain.Routes

fun main() = singleWindowApplication {
    Router("/auth/login") {
        val navigator = LocalNavigator.current
        Column {
            Row {
                Button(onClick = {
                    navigator.navigate("/auth/login")
                }) {
                    Text("/auth/login")
                }
                Button(onClick = {
                    navigator.navigate("/home")
                }) {
                    Text("/home")
                }
                Button(onClick = {
                    navigator.navigate("/about")
                }) {
                    Text("/about")
                }
                Button(onClick = {
                    navigator.navigate("/customers")
                }) {
                    Text("/customers")
                }
                Button(onClick = {
                    navigator.navigate("/contacts")
                }) {
                    Text("/contacts")
                }
                Button(onClick = {
                    navigator.navigate("/auth/forgot")
                }) {
                    Text("/auth/forgot")
                }
            }
            Routes {
                Route("/auth/login") {
                    Text("/auth/login")
                }
                Routes {
                    Route("home") {
                        Text("/home")
                    }
                    Route("about") {
                        Text("/about")
                    }
                    Route("*") {
                        Navigate(to = "/home")
                    }
                }
                Routes {
                    Route("contacts") {
                        Text("/contacts")
                    }
                    Route("customers") {
                        Text("/customers")
                    }
                }
                Route("/auth/forgot") {
                    Text("/auth/forgot")
                }
            }
        }
    }
}