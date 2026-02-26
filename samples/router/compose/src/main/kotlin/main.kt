import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.singleWindowApplication
import captain.LocalNavigator
import captain.Route
import captain.Router
import captain.Routes

fun mainold() = singleWindowApplication {
    Router("/customers/andy") {
        val navigator = LocalNavigator.current
        Column {
            Menu()
            Routes {
                Route("/auth/login") {
                    Column {
                        Text("Login here")
                        Button(onClick = { navigator.navigate("/home") }) {
                            Text("Login")
                        }
                    }
                }
                Route("*") {
                    Protected {
                        Panel {
                            Routes {
                                Route("home") {
                                    Text("/home")
                                }
                                Route("about") {
                                    Text("/about")
                                }
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
                        }
                    }
                    Route("/auth/forgot") {
                        Text("/auth/forgot")
                    }
                }
            }
        }
    }
}

@Composable
internal fun Protected(content: @Composable () -> Unit) = Column {
    Text("You are now authenticated")
    content()
}

@Composable
fun Panel(content: @Composable () -> Unit) {
    val navigator = LocalNavigator.current
    Row {
        Column {
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
        }
        content()
    }
}

@Composable
internal fun Menu() = Row {
    val navigator = LocalNavigator.current
    Button(onClick = {
        navigator.navigate("/auth/login")
    }) {
        Text("/auth/login")
    }
    Button(onClick = {
        navigator.navigate("/auth/forgot")
    }) {
        Text("/auth/forgot")
    }
}