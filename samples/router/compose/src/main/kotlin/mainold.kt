import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.singleWindowApplication
import captain.BasicNavigator
import captain.Route
import captain.Router
import captain.Routes
import cinematic.watchAsState
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


/**
 *
 */

val navigator = BasicNavigator("/")
fun main() = singleWindowApplication {
    Column {
        Text(
            text = navigator.route.watchAsState().toString()
        )
        Row {
            var path by remember { mutableStateOf("") }
            TextField(
                value = path,
                onValueChange = { path = it }
            )
            Button(
                onClick = { navigator.navigate(path) }
            ) {
                Text("Go")
            }
        }

        Router("/", navigator) {
            Routes {
                Route("campuses/*") {
                    Routes {
                        Route("/") {
                            Text("Campuses list")
                        }

                        Route("{campus}/*") { (campus) ->
                            Routes {
                                Route("/") {
                                    Text("Campus Index: $campus")
                                }

                                Route("curriculums/*") {
                                    Routes {
                                        Route("/") {
                                            Text("Curriculums list")
                                        }

                                        Route("{curriculum}/*") { (campus,curriculum) ->
                                            Loader(duration = curriculum.toInt().seconds) {
                                                Routes {
                                                    Route("/") { (campus, curriculum) ->
                                                        Text("Campus: ${campus}, Curriculum: $curriculum")
                                                    }

                                                    Route("/levels/*") {
                                                        Routes {
                                                            Route("/") { (campus, curriculum) ->
                                                                Text("Campus: ${campus}, Curriculum: ${curriculum}, Levels list")
                                                            }

                                                            Route("{level}/*") {
                                                                Routes {
                                                                    Route("/") { (campus, curriculum, level) ->
                                                                        Text("Campus: $campus, Curriculum: $curriculum, Level: $level")
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Route("*") {
                    Text("Not found")
                }
            }
        }
    }
}

@Composable
internal fun Loader(duration: Duration = 3.seconds, content: @Composable () -> Unit) = Column {
    var loading by remember { mutableStateOf(true) }
    LaunchedEffect(duration) {
        loading = true
        delay(duration)
        loading = false
    }
    if (loading) {
        Text("Loading...")
    } else {
        content()
    }
}