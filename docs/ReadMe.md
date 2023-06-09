# captain

A platform & framework agnostic kotlin multiplatform library pack that you can use to navigate though different destinations of your application.

With captain, you can do all your navigation from your business logic (which should be in common code) 
and do your routing in the ui layer (which depending on your tech stack, may or may not be in common code)

![Maven](https://img.shields.io/maven-central/v/tz.co.asoft/captain-navigator-api/[version]?style=for-the-badge)
[badges]

## Router Samples

### compose

```kotlin
Router {
    AppNavigation()
    Routes {
        Route("/") { Home() }
        Route("/settings") { Settings() }
        Route("/profile/{uid}") { (uid) ->
            Profile(uid)
        }
    }
}
```

### react

```jsx
<Router>
    <AppNavigation />
    <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="/settings" element={<Settings/>} />
        <Route path="/profile/{uid}" element={<Profile/>} />
    </Routes>
</Router>
```

### kotlin-react

```kotlin
Router {
    AppNavigation()
    Routes {
        Route("/", Home)
        Route("/settings", Settings)
        Route("/profile/{uid}", Profile)
    }
}
```

## Router Setup

### compose

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    // for compose-multiplatform 
    implementation("tz.co.asoft:captain-router-compose-core:[version]")
    // for compose-html use
    implementation("tz.co.asoft:captain-router-compose-html:[version]")
}
```

### kotlin-react

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    // for react-core (can be used even in react-native applications)
    implementation("tz.co.asoft:captain-router-react-core:[version]")
    // for react-dom (can be used even in react-dom applications)
    implementation("tz.co.asoft:captain-router-react-dom:[version]")
}
```

## Navigator Samples

### common-main

#### src
`Navigator.kt`

```kotlin
expect fun getNavigator(): Navigator
```

`NavigatorUtils.kt`
```kotlin
fun goTo(destination: String = "/") {
    val nav: Navigator = getNavigator()
    nav.navigate(destination)
}

fun goBack() {
    val nav: Navigator = getNavigator()
    nav.go(-1)
}

fun goForward() {
    val nav: Navigator = getNavigator()
    nav.go(1)
}
```

#### dependencies

```kotlin
dependencies {
    implementation("tz.co.asoft:captain-navigator-api:[version]")
}
```

### js-main (Browser specific)

To use the browser's history api and follow the address bar and back/forward presses

#### src

```kotlin
private val navigator = BrowserNavigator()
// to avoid creating a new navigator everytime we call getNavigator

actual fun getNavigator(): Navigator = navigator
```

#### dependencies

```kotlin
dependencies {
    implementation("tz.co.asoft:captain-navigator-browser:[version]")
}
```

### other-main

#### src
```kotlin
private val navigator = BasicNavigator()
// to avoid creating a new navigator everytime we call getNavigator

actual fun getNavigator(): Navigator = navigator
```

#### dependencies

```kotlin
dependencies {
    implementation("tz.co.asoft:captain-navigator-basic:[version]")
}
```
## Documentation
Full documentation will be published soon

## Api Reference

The full api reference of the kollections can be found
at [https://asoft-ltd.github.io/captain](https://asoft-ltd.github.io/captain)

## Support

There are multiple ways you can support this project

### Star It

If you found it useful, just give it a star

### Contribute

You can help by submitting pull request to available open tickets on the issues section

### Report Issues

This makes it easier to catch bugs and offer enhancements required

## Credits

- [andylamax](https://github.com/andylamax) - The author