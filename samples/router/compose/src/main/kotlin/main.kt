import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
                    navigator.navigate("/customers/456")
                }) {
                    Text("/customers/456")
                }
                Button(onClick = {
                    navigator.navigate("/customers/andy")
                }) {
                    Text("/customers/andy")
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
                Group {
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
                Group {
                    Route("contacts") {
                        Text("/contacts")
                    }
                    Route("customers") {
                        Column {
                            var uid by remember { mutableStateOf("012") }
                            Row {
                                Text("/customers/")
                                TextField(value = uid, onValueChange = { uid = it })
                                Button(onClick = {
                                    navigator.navigate("/customers/$uid")
                                }) {
                                    Text("/customers/$uid")
                                }
                            }

                            Routes {
                                Route("andy") {
                                    Text("Customer Andylamax")
                                }
                                Route("*") {
                                    Text("Customer list")
                                }
                                Route("{uid}") { uid ->
                                    Text("Customer details for $uid")
                                }
                            }
                        }
                    }
                }
                Route("/auth/forgot") {
                    Text("/auth/forgot")
                }
            }
        }
    }
}