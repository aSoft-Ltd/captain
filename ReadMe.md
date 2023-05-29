# captain

A kotlin multiplatform library pack that you can use to navigate though different destinations of your application.

With captain, you can do all your navigation from your business logic (which should be in common code) 
and do your routing in the ui layer (which depending on your tech stack, may or may not be in common code)

![Maven](https://img.shields.io/maven-central/v/tz.co.asoft/captain/2.0.16?style=for-the-badge)
![Kotlin](https://img.shields.io/badge/kotlin-multiplatform-blue?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=&logoColor=white)
![Swift](https://img.shields.io/badge/swift-F54A2A?style=for-the-badge&logo=swift&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-000000?style=for-the-badge&logo=ios&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![TypeScript](https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white)

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
<Router> {
    <AppNavigation />
    <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="/settings" element={<Settings/>} />
        <Route path="/user/{uid}" element={<Profile/>} />
    <Routes>
</Router>
```

### kotlin-react

```kotlin
Router {
    AppNavigation()
    Routes {
        Route {
            path = "/"
            element = Home.create()
        }
        Route {
            path = "/settings"
            element = Settings.create()
        }
        Route {
            path = "/user/{uid}"
            element = Profile.create()
        }
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
    implementation("tz.co.asoft:captain-router-compose-core:2.0.16")
    // for compose-html use
    implementation("tz.co.asoft:captain-router-compose-html:2.0.16")
}
```

### kotlin-react

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    // for react-core (can be used even in react-native applications)
    implementation("tz.co.asoft:captain-router-react-core:2.0.16")
    // for react-dom (can be used even in react-dom applications)
    implementation("tz.co.asoft:captain-router-react-dom:2.0.16")
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
    nav.navigate(-1)
}

fun goForward() {
    val nav: Navigator = getNavigator()
    nav.navigate(-1)
}
```

#### dependencies

```kotlin
dependencies {
    implementation("tz.co.asoft:captain-navigator-api:2.0.16")
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
    implementation("tz.co.asoft:captain-navigator-browser:2.0.16")
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
    implementation("tz.co.asoft:captain-navigator-basic:2.0.16")
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