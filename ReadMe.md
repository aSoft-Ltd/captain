# Captain

A platform and framework agnostic Kotlin Multiplatform navigation toolkit.

Captain splits navigation into two layers so that the *decision* to go somewhere lives in your business logic (common code) and the *rendering* of that destination lives in your UI layer (Compose Multiplatform, Compose HTML, or Kotlin React).

![Maven](https://img.shields.io/maven-central/v/tz.co.asoft/captain-navigator-api?style=for-the-badge&logo=apachemaven&logoColor=white&color=C71A36&label=maven-central)
![Kotlin](https://img.shields.io/badge/kotlin-multiplatform-blue?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=&logoColor=white)
![Swift](https://img.shields.io/badge/swift-F54A2A?style=for-the-badge&logo=swift&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-000000?style=for-the-badge&logo=ios&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![TypeScript](https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white)

> **Watch the tutorials:** a step-by-step [YouTube playlist](https://youtube.com/playlist?list=PLmnmuJ9Wj0z3aeHFy8l1QO9DGPECsA307&si=_gjfGrYlbCyfiJkx) walks through Captain in real apps.

---

## Table of contents

- [Mental model](#mental-model)
- [Modules](#modules)
- [Installation](#installation)
- [Quick start](#quick-start)
- [Navigators](#navigators)
  - [The `Navigator` interface](#the-navigator-interface)
  - [`BasicNavigator`](#basicnavigator)
  - [`BrowserNavigator`](#browsernavigator)
  - [`NavigateFunction` utility](#navigatefunction-utility)
  - [Attaching state to a destination](#attaching-state-to-a-destination)
  - [History semantics: `record`, `go`, and trimming](#history-semantics-record-go-and-trimming)
- [Path syntax](#path-syntax)
  - [Exact, dynamic, wildcard](#exact-dynamic-wildcard)
  - [Match scoring and tie breaking](#match-scoring-and-tie-breaking)
  - [Relative vs absolute resolution](#relative-vs-absolute-resolution)
  - [Nested routes and parameter accumulation](#nested-routes-and-parameter-accumulation)
  - [`RouteInfo`](#routeinfo)
- [Router for Compose](#router-for-compose)
  - [`Router` composable](#router-composable)
  - [`Routes` and `Route`](#routes-and-route)
  - [Reading the current route](#reading-the-current-route)
  - [Reading parameters](#reading-parameters)
  - [Imperative navigation: `rememberNavigate`](#imperative-navigation-remembernavigate)
  - [Declarative redirect: `Navigate`](#declarative-redirect-navigate)
  - [Organising routes: `Group` and `Nested`](#organising-routes-group-and-nested)
  - [Compose HTML extras: the `A` anchor](#compose-html-extras-the-a-anchor)
- [Router for Kotlin React](#router-for-kotlin-react)
  - [`Router` component](#router-component)
  - [`Routes` and `Route` (DSL)](#routes-and-route-dsl)
  - [`InternalRoutes` and `InternalRoute` (FC props API)](#internalroutes-and-internalroute-fc-props-api)
  - [Hooks](#hooks)
  - [`Navigate` for redirects](#navigate-for-redirects)
  - [`A` link component (react-dom)](#a-link-component-react-dom)
- [Testing](#testing)
- [Sample apps](#sample-apps)
- [API reference](#api-reference)
- [Contributing and support](#contributing-and-support)
- [Credits](#credits)

---

## Mental model

Captain has exactly two concepts you need to internalise.

1. **A `Navigator` owns the current URL and its history.** It is plain Kotlin. You can construct it once and reach for it from any layer of your app. It exposes a reactive `route: Live<Url>` you can observe and a small set of imperative methods (`navigate`, `go`, `current`, `state`). There is no UI dependency.

2. **A `Router` watches a `Navigator` and renders the matching destination.** Your UI describes a list of routes and what to draw for each. When the navigator's route changes, the router picks the best matching destination and renders it. There is one router binding per UI framework (Compose Multiplatform, Compose HTML, Kotlin React), all built on the same core matching engine.

This split is what lets you write navigation decisions in shared business logic ("after sign-in, go to `/home`") and render them with whichever UI stack each platform uses.

---

## Modules

Captain is published as a set of small modules. Pull in only what you need.

| Module | Targets | Purpose |
|--------|---------|---------|
| `captain-navigator-api` | common | The `Navigator` interface, `Navigable`, `NavigateFunction`, exceptions |
| `captain-navigator-basic` | common | `BasicNavigator`: in-memory navigator with a history stack |
| `captain-navigator-browser` | JS | `BrowserNavigator`: integrates with `window.history` and the address bar |
| `captain-navigator-test` | common | `AbstractNavigatorTest`: shared contract tests for any `Navigator` |
| `captain-router-core` | common | Framework-agnostic route matching engine (`RouteConfig`, `RouteInfo`, `selectRoute`, `bestMatch`) |
| `captain-router-compose-core` | common | Compose Multiplatform bindings: `Router`, `Routes`, `Route`, `remember*` hooks |
| `captain-router-compose-html` | JS | Compose HTML extras: the `A` anchor composable |
| `captain-router-react-core` | JS | Kotlin React bindings: `Router`, `Routes`, `Route`, `Navigate`, `use*` hooks |
| `captain-router-react-dom` | JS | React DOM extras: the `A` link component |

All artifacts are published under the `tz.co.asoft` group.

> The `router/generic` module exists in the source tree as a reference implementation of the matching engine but is excluded from the published build. The `samples/router/react` module is also currently excluded from the build until [KT-80014](https://youtrack.jetbrains.com/issue/KT-80014) is fixed; its source is still useful as a usage reference.

---

## Installation

Every artifact follows the same coordinate shape. Replace `3.3.8` with whichever version you want.

```kotlin
repositories {
    mavenCentral()
}
```

### Compose Multiplatform

```kotlin
dependencies {
    implementation("tz.co.asoft:captain-router-compose-core:3.3.8")
}
```

### Compose HTML (web only)

```kotlin
dependencies {
    implementation("tz.co.asoft:captain-router-compose-core:3.3.8")
    implementation("tz.co.asoft:captain-router-compose-html:3.3.8")
    implementation("tz.co.asoft:captain-navigator-browser:3.3.8")
}
```

### Kotlin React (browser)

```kotlin
dependencies {
    implementation("tz.co.asoft:captain-router-react-core:3.3.8")
    implementation("tz.co.asoft:captain-router-react-dom:3.3.8")
    implementation("tz.co.asoft:captain-navigator-browser:3.3.8")
}
```

### Common code only (just the `Navigator` types)

```kotlin
dependencies {
    implementation("tz.co.asoft:captain-navigator-api:3.3.8")
    implementation("tz.co.asoft:captain-navigator-basic:3.3.8")
}
```

### Testing your own `Navigator`

```kotlin
dependencies {
    testImplementation("tz.co.asoft:captain-navigator-test:3.3.8")
}
```

---

## Quick start

### Compose Multiplatform desktop

```kotlin
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.singleWindowApplication
import captain.*

val navigator = BasicNavigator("/")

fun main() = singleWindowApplication {
    Router(start = "/", navigator = navigator) {
        Column {
            Menu()
            Routes {
                Route("/")           { Text("Home") }
                Route("/settings")   { Text("Settings") }
                Route("/profile/{uid}") { (uid) -> Text("Profile of $uid") }
                Route("*")           { Text("Not found") }
            }
        }
    }
}

@Composable
fun Menu() = Row {
    val navigate = rememberNavigate()
    Button(onClick = { navigate("/") })                 { Text("Home") }
    Button(onClick = { navigate("/settings") })         { Text("Settings") }
    Button(onClick = { navigate("/profile/andy") })     { Text("Profile") }
    Button(onClick = { navigate(-1) })                  { Text("Back") }
    Button(onClick = { navigate(1) })                   { Text("Forward") }
}
```

### Compose HTML (web)

```kotlin
import androidx.compose.runtime.*
import captain.*
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Router(navigator = BrowserNavigator(syncWithAddressBar = true)) {
            Routes {
                Route("/")     { Home() }
                Route("/blog/{postId}") { (postId) -> Post(postId) }
                Route("*")     { NotFound() }
            }
        }
    }
}
```

### Kotlin React

```kotlin
import captain.*
import react.*
import react.dom.client.createRoot
import react.dom.html.ReactHTML.button
import web.dom.document
import web.html.HTMLDivElement

val Home    = FC<Props> { h1 { +"Home" } }
val About   = FC<Props> { h1 { +"About" } }
val Profile = FC<Props> {
    val (uid) = useParams()
    h1 { +"Profile of $uid" }
}

val App = FC<Props> {
    Router {
        Menu()
        Routes {
            Route("/",              Home)
            Route("/about",         About)
            Route("/profile/{uid}", Profile)
            Route("*") { h1 { +"Not found" } }
        }
    }
}

val Menu = FC<Props> {
    val navigate = useNavigate()
    button { onClick = { navigate("/") };              +"Home" }
    button { onClick = { navigate("/about") };         +"About" }
    button { onClick = { navigate("/profile/andy") };  +"Profile" }
    button { onClick = { navigate(-1) };               +"Back" }
}

fun main() {
    val root = document.getElementById("root") as HTMLDivElement
    createRoot(root).render(App.create())
}
```

---

## Navigators

### The `Navigator` interface

```kotlin
interface Navigator {
    val route: Live<Url>
    fun state(): Any? = null
    fun current(): Url
    fun navigate(path: String, record: Boolean = true, state: Any?)
    fun navigate(path: String, record: Boolean = true)
    fun go(steps: Int)
}
```

| Member | What it does |
|--------|--------------|
| `route` | A reactive `Live<Url>` (from cinematic) that emits whenever the current URL changes. UI bindings observe this. |
| `current()` | Reads the current URL once, without subscribing. |
| `state()` | Returns whatever state object you attached when you last navigated to the current URL. `null` if you never attached one. |
| `navigate(path, record, state)` | Resolves `path` against `current()`, updates the route, optionally records it in history, and stores `state` keyed by the resolved URL. |
| `navigate(path, record)` | Same as above without a state object. |
| `go(steps)` | Moves through the recorded history. Positive steps go forward, negative steps go back. A no-op if it would fall outside history bounds. |

Two convenience types live alongside the interface:

```kotlin
interface Navigable { val navigator: Navigator }   // mixin for any type that owns a Navigator
typealias NavigateFunction = (arg: StringOrInt) -> Unit
```

There is also a single exception:

```kotlin
class UnsupportedNavigationArgument(val arg: Any) : IllegalArgumentException
```

It is thrown by `NavigateFunction` when you pass something that is neither a `String` nor an `Int`.

### `BasicNavigator`

`BasicNavigator(root: String)` is the in-memory, platform-agnostic implementation. Use it for desktop, JVM, Android, Native, and tests.

```kotlin
val nav = BasicNavigator("https://myapp.com")
nav.navigate("products/123")          // resolves to https://myapp.com/products/123
nav.navigate("details")               // resolves to https://myapp.com/products/123/details
nav.go(-1)                            // back to /products/123
nav.go(-1)                            // back to the root
nav.canGo(-1)                         // false, we are at the bottom of the stack
```

Internally `BasicNavigator` keeps a `LinearlyTraversableStack<Url>`: a list with a cursor. Calling `navigate(...)` with `record = true` inserts the new URL at `cursor + 1` and trims everything past the cursor. This implements the standard "going back and taking a different path discards the forward history" behaviour you expect from a browser back button.

### `BrowserNavigator`

`BrowserNavigator(syncWithAddressBar: Boolean)` is the JS implementation that talks to `window.history`.

```kotlin
val nav = BrowserNavigator(syncWithAddressBar = true)
```

When `syncWithAddressBar` is true:
- `navigate(path, record = true)` calls `window.history.pushState(null, document.title, path)` so the URL bar updates.
- A `window.onpopstate` listener is installed; when the user presses the browser back or forward button, the navigator's `route` updates to match without recording a fresh history entry.

When `syncWithAddressBar` is false:
- The navigator is fully internal. The address bar is not touched and no popstate handler is installed. This is what you want in unit tests, SSR hydration, or any context where you do not want the browser to be the source of truth.

A few things worth knowing:

- Only the *path* component is pushed to history. Query strings and fragments are kept in the live `route` value but are not part of the history entry.
- The navigator does not push an initial entry. The first `navigate(..., record = true)` call is what creates the first history entry.
- `window.onpopstate` is assigned directly. Only one `BrowserNavigator` with `syncWithAddressBar = true` should be alive per window.
- State attached with `navigator.navigate(path, state = ...)` lives in a Kotlin map inside the navigator, not in `History.state`. It does not survive a full page reload.

### `NavigateFunction` utility

`NavigateFunction` is a tiny adapter that turns a `Navigator` into a `(StringOrInt) -> Unit` lambda. It is what powers both `rememberNavigate()` in Compose and `useNavigate()` in React, but you can call it directly too.

```kotlin
val nav = BasicNavigator("/")
val navigate: NavigateFunction = NavigateFunction(nav)

navigate("/settings")     // calls nav.navigate("/settings")
navigate(-1)              // calls nav.go(-1)
navigate(2.5)             // throws UnsupportedNavigationArgument
```

The two-arg form lets you anchor relative paths to a specific URL instead of the current one:

```kotlin
val fromProfile = NavigateFunction(nav, from = Url("/profile/andy"))
fromProfile("settings")   // navigates to /profile/andy/settings, regardless of current route
```

There is also a no-arg overload `NavigateFunction()` that returns a function which just prints what would have happened. Handy for stubbing in tests.

### Attaching state to a destination

You can attach any object to a destination at navigation time:

```kotlin
data class Person(val id: String, val name: String)
nav.navigate("/about", state = Person("42", "Andy"))
println(nav.state())   // Person(id=42, name=Andy)
```

State is keyed by the resolved `Url`, so coming back to the same URL via `go(-1)` retrieves the same object. State is *not* observed: subscribing to `route` only fires on URL changes, so code that cares about state should call `state()` explicitly after a route change.

### History semantics: `record`, `go`, and trimming

- `navigate(path, record = true)` (the default) appends to history and trims any "forward" entries.
- `navigate(path, record = false)` updates the route in place without touching history. The user cannot go back to where they were before this call.
- `go(n)` moves the history cursor by `n`. Positive is forward, negative is back. If the move would fall outside history bounds it is a no-op.
- `canGo(n)` (available on `BasicNavigator`) tells you whether a `go(n)` would succeed.

---

## Path syntax

Captain's matcher is provided by `captain-router-core`, which delegates URL parsing to the `kiota` library. The same rules apply across every UI binding.

### Exact, dynamic, wildcard

| Pattern | Matches | Notes |
|---------|---------|-------|
| `/` | The root only | Does not match `/anything`. |
| `/about` | Exactly `/about` | Does not match `/about/x`. |
| `/profile/{uid}` | `/profile/andy`, `/profile/42` | `uid` is captured. |
| `/posts/*` | `/posts/anything` | `*` matches a single path segment. |
| `/api/**` | `/api/v1/users/3` and similar | `**` matches one or more nested segments. |
| `*` | A single segment at this level | Useful as a fallback when nested. |

A parameter is a path segment wrapped in braces: `{name}`. Whatever appears in that position becomes the parameter's value, as a string. There is no automatic type coercion; convert to `Int`, `UUID`, etc. yourself.

### Match scoring and tie breaking

When several patterns match the same URL, the router picks one using a small algorithm:

1. Each candidate gets a score from the matcher. More specific segments (exact > dynamic > wildcard) score higher.
2. The highest score wins.
3. If two candidates tie on score, the one with *fewer* total segments wins (it is the more specific pattern).

So for the URL `/posts` against the options `/`, `/posts/*`, and `/*`, the matcher picks `/posts/*`. For `/posts` against `/posts/{uid}/*` and `/posts`, it picks `/posts` because the dynamic pattern requires more segments than the URL provides.

### Relative vs absolute resolution

Navigation strings are resolved against the current URL using `Url.resolve(...)`. The familiar rules apply:

- `/abs` is absolute and replaces everything after the host.
- `rel` is relative and is appended to the current path.
- `../up` walks one level up the path hierarchy.

```kotlin
nav.navigate("https://app.com")
nav.navigate("a/b/c")        // -> https://app.com/a/b/c
nav.navigate("../d")         // -> https://app.com/a/b/d
nav.navigate("/root")        // -> https://app.com/root
```

In Compose and React the same resolution applies to `rememberNavigate()` / `useNavigate()` calls, but the *base* for relative paths is the parent route's pattern, not the navigator's current URL. This is what lets nested routes use clean relative paths.

### Nested routes and parameter accumulation

Routes nest by placing a `Routes { ... }` block inside a `Route { ... }` block whose pattern ends in a wildcard. The parent supplies the matched portion, and the child matcher only sees the remaining tail.

```kotlin
Routes {
    Route("/campuses/*") {
        Routes {
            Route("/") { Text("Campuses list") }
            Route("{campus}/*") { (campus) ->
                Routes {
                    Route("/") { Text("Campus index: $campus") }
                    Route("curriculums/{curriculum}") { (campus, curriculum) ->
                        Text("$campus / $curriculum")
                    }
                }
            }
        }
    }
    Route("*") { Text("Not found") }
}
```

Parameters captured at outer levels stay available to inner levels. In Compose, the destructured tuple grows with each level: `(campus)`, then `(campus, curriculum)`, then `(campus, curriculum, level)`, in the order they appear in the full path. In React you read them through `useParams()` or `useParam("name")`, which also see ancestor params.

### `RouteInfo`

When the router matches a destination it produces a `RouteInfo`:

```kotlin
data class RouteInfo<out C>(
    val parent: RouteInfo<C>?,
    val match: UrlMatch,
    val options: List<Url>,
    val matches: Map<Url, Int>,
    val content: C
)
```

- `parent` is the parent route info for nested routes, or `null` at the top level.
- `match` exposes the path segments, captured parameters, score, and `param(key)` lookup.
- `options` is every candidate route at the level that matched.
- `matches` is the per-candidate score map. Useful for debugging.
- `content` is the destination payload (a composable lambda in Compose, a `ReactNode` in React).

Call `routeInfo.printDebugString()` for a readable dump while debugging match resolution.

---

## Router for Compose

This binding lives in `captain-router-compose-core` (Compose Multiplatform) and `captain-router-compose-html` (web extras). Everything below works on JVM, Android, iOS, JS, and WASM unless explicitly noted as HTML only.

### `Router` composable

```kotlin
@Composable
fun Router(
    start: String = "/",
    navigator: Navigator = BasicNavigator(start),
    content: @Composable () -> Unit
)
```

`Router` provides the `Navigator` to every descendent via the `LocalNavigator` CompositionLocal. If you do not pass one in, a `BasicNavigator(start)` is created for you. If the navigator's current URL differs from `start`, the router calls `navigator.navigate(start)` once before rendering.

In a multiplatform app you usually keep your navigator in `commonMain` using `expect/actual`:

```kotlin
// commonMain
expect fun getNavigator(): Navigator

// jvmMain
private val navigator = BasicNavigator("/")
actual fun getNavigator(): Navigator = navigator

// jsMain
private val navigator = BrowserNavigator(syncWithAddressBar = true)
actual fun getNavigator(): Navigator = navigator
```

Then in shared code:

```kotlin
Router(navigator = getNavigator()) { /* routes */ }
```

### `Routes` and `Route`

```kotlin
@Composable
@RoutingDsl
fun Routes(builder: RoutesBuilder.() -> Unit)

@RoutingDsl
inline fun RoutesBuilder.Route(
    path: String,
    noinline content: @Composable RouteContent.(params: List<String>) -> Unit
)
```

`Routes` is the container that watches `LocalNavigator.current.route` and renders the best matching `Route`. Inside `Routes` you declare routes with `Route(pattern) { (param1, param2, ...) -> ... }`. The destructured tuple is the list of dynamic segment values, in the order they appear in the full path including any parent routes.

```kotlin
Routes {
    Route("/")                         { Home() }
    Route("/about")                    { About() }
    Route("/users/{uid}")              { (uid) -> UserProfile(uid) }
    Route("/orders/{oid}/items/{iid}") { (oid, iid) -> OrderItem(oid, iid) }
    Route("*")                         { NotFound() }
}
```

If you do not care about params, just ignore the lambda parameter:

```kotlin
Route("/about") { About() }
```

### Reading the current route

```kotlin
val location: Url = rememberLocation()   // recomposes on route change
val nav: Navigator = rememberNavigator()
val info: RouteInfo<RouteContent>? = rememberRouteInfo()
```

`rememberLocation()` is short for `rememberNavigator().current()`. `rememberRouteInfo()` returns the matched route info if you are inside a `Route { ... }` block, or `null` otherwise.

### Reading parameters

You have four options depending on how strict you want to be.

```kotlin
// 1. Destructuring (most common)
Route("/users/{uid}") { (uid) -> Text(uid) }

// 2. By name, throws if missing
val uid = rememberParam("uid")

// 3. By name, safe
val maybeUid: Optional<String> = rememberOptionalParams("uid")

// 4. The whole map
val all: Map<String, String> = rememberPathParams()

// 5. Query string
val q: QueryParams = rememberQueryParams()

// 6. State attached via navigate(..., state = ...)
val state: Any? = rememberRouteState()
```

### Imperative navigation: `rememberNavigate`

```kotlin
@Composable
inline fun rememberNavigate(): NavigateFunction
```

`rememberNavigate()` returns a `NavigateFunction` that resolves relative paths against the current route's pattern (via `LocalNavigateReference`). That means in nested routes you can write short relative paths and they "just work".

```kotlin
@Composable
fun ProfileMenu() {
    val navigate = rememberNavigate()
    Button(onClick = { navigate("/") })          { Text("Home") }
    Button(onClick = { navigate("settings") })   { Text("My settings") }  // relative
    Button(onClick = { navigate(-1) })           { Text("Back") }
}
```

For the navigator itself (for example to pass state):

```kotlin
val nav = rememberNavigator()
Button(onClick = { nav.navigate("/about", state = Person("42", "Andy")) }) {
    Text("About")
}
```

### Declarative redirect: `Navigate`

```kotlin
@Composable
inline fun Navigate(to: String, record: Boolean = false)
```

`Navigate` fires once on composition. Use it for redirect-style routes:

```kotlin
Route("/old-home") { Navigate(to = "/home") }
```

By default it does not record a history entry, so the user's back button will skip the redirect rather than bouncing through it.

### Organising routes: `Group` and `Nested`

For larger apps you can break up route declarations:

```kotlin
Routes {
    // Group wraps composables without affecting matching (e.g. behind an auth gate)
    Group {
        Route("/dashboard") { Dashboard() }
        Route("/settings")  { Settings() }
    }

    // Nested splits route declarations across functions
    Route("/admin/*") {
        Nested {
            AdminRoutes()
        }
    }
}

@Composable
fun RoutesBuilder.AdminRoutes() {
    Route("/users")   { UserList() }
    Route("/billing") { Billing() }
}
```

### Compose HTML extras: the `A` anchor

`captain-router-compose-html` adds a single composable that renders an HTML `<a>` element wired through the router:

```kotlin
@Composable
fun A(to: String, content: @Composable () -> Unit)
```

It renders `<a href="to">`, intercepts the click with `preventDefault()`, and calls `rememberNavigate()` so the navigation stays inside the SPA. Pair it with `BrowserNavigator(syncWithAddressBar = true)` for full address bar integration.

```kotlin
@Composable
fun NavBar() {
    A(to = "/")        { Text("Home") }
    A(to = "/blog")    { Text("Blog") }
    A(to = "/contact") { Text("Contact") }
}
```

---

## Router for Kotlin React

The React binding lives in `captain-router-react-core` (the components and hooks) and `captain-router-react-dom` (the `A` link component).

### `Router` component

```kotlin
external interface RouterProps : PropsWithChildren {
    var navigator: Navigator?
}
```

The Kotlin React DSL helper:

```kotlin
inline fun ChildrenBuilder.Router(
    navigator: Navigator? = null,
    noinline builder: ChildrenBuilder.() -> Unit
)
```

`Router` installs three React contexts that the rest of the library reads from: `NavigatorContext`, `RouteInfoContext`, and `NavigateReferenceContext`. If you do not pass a `navigator`, a `BrowserNavigator(syncWithAddressBar = true)` is created. To opt out of address bar syncing, pass your own:

```kotlin
Router(navigator = BrowserNavigator(syncWithAddressBar = false)) { /* ... */ }
```

### `Routes` and `Route` (DSL)

```kotlin
inline fun ChildrenBuilder.Routes(noinline builder: RoutesBuilder.() -> Unit)

inline fun RoutesBuilder.Route(path: String, element: ReactNode)
inline fun <P : Props> RoutesBuilder.Route(path: String, element: ElementType<P>)
inline fun <P : Props> RoutesBuilder.Route(
    path: String,
    element: ElementType<P>,
    noinline block: P.() -> Unit
)
inline fun RoutesBuilder.Route(
    path: String,
    noinline content: ChildrenBuilder.(Props) -> Unit
)
```

You can hand a `Route` either a pre-built `ReactNode`, an FC reference, an FC reference with a props builder block, or an inline `ChildrenBuilder` lambda. Pick whichever fits the call site.

```kotlin
val Home    = FC<Props> { h1 { +"Home" } }
val Profile = FC<Props> {
    val uid = useParam("uid")
    h1 { +"Profile $uid" }
}

val App = FC<Props> {
    Router {
        Routes {
            Route("/",              Home)
            Route("/profile/{uid}", Profile)
            Route("/users")         { div { +"Users page" } }
            Route("*")              { h1 { +"Not found" } }
        }
    }
}
```

### `InternalRoutes` and `InternalRoute` (FC props API)

For interop with plain JavaScript React consumers and for cases where you prefer props over a DSL, the components are also exposed in raw FC form:

```kotlin
val Routes = FC<PropsWithChildren>("Routes") {
    InternalRoutes {
        InternalRoute { path = "/";          element = Home.create() }
        InternalRoute { path = "/profile/{uid}"; element = Profile.create() }
        InternalRoute { path = "/info";      element = InternalNavigate.create { to = "/about" } }
        InternalRoute { path = "*";          element = NotFound.create() }
    }
}
```

This produces an identical runtime behaviour. The same `Routes` component will accept a mix of DSL-built `Route` children and `InternalRoute` children.

### Hooks

All hooks must be called inside the tree rendered by `<Router>`.

| Hook | Returns | Notes |
|------|---------|-------|
| `useNavigator()` | `Navigator` | The active `Navigator`. Throws if called outside a `Router`. |
| `useNavigate()` | `NavigateFunction` | Pass a `String` for path navigation, an `Int` for `go(n)`. Resolves relative paths against the current route. |
| `useNavigateReference()` | `Url` | The URL used as the base for relative navigation at this point in the tree. |
| `useLocation()` | `Url` | The current URL. Re-renders on route change. |
| `useRouteInfo()` | `RouteInfo<RouteContent>?` | The matched route info, or `null` outside a `Route`. |
| `useParam(key)` | `String` | The named path parameter. Throws if missing. |
| `useOptionalParam(key)` | `Optional<String>` | Safe variant. |
| `useParams()` | `Params` | All path parameters as a `Record<String, String>`. Destructurable. |
| `useParamsOf<T>()` | `T : Params` | Same as above but cast to a typed `Params` interface you defined. |

Example:

```kotlin
val CommentReply = FC<Props> {
    val (uid, cid) = useParams()
    val navRef = useNavigateReference()
    h2 { +"Reply $cid for post $uid (ref ${navRef.path})" }
}

val Menu = FC<Props> {
    val navigate = useNavigate()
    button { onClick = { navigate("/") };       +"Home" }
    button { onClick = { navigate(-1) };        +"Back" }
}
```

### `Navigate` for redirects

```kotlin
val DestinationProps = interface {
    var to: String
}

inline fun ChildrenBuilder.Navigate(to: String)
```

`Navigate` fires once on mount (and whenever `to` changes) via `useEffect`. Use it for redirect routes:

```kotlin
Route("/info") { Navigate(to = "/about") }
```

### `A` link component (react-dom)

`captain-router-react-dom` adds `A`, a router-aware anchor:

```kotlin
external interface AProps : DestinationProps, PropsWithChildren
val A = FC<AProps>("A")
```

It renders a real `<a href="to">` element so that middle-click, right-click "Open in new tab", screen readers, and SEO all behave correctly, but its `onClick` calls `event.preventDefault()` and routes via `useNavigate()` so an in-app navigation does not trigger a full page load.

```kotlin
nav {
    A { to = "/";        +"Home" }
    A { to = "/about";   +"About" }
    A { to = "/users/$id" 
        span { strong { +"View user" } }
    }
}
```

---

## Testing

`captain-navigator-test` ships an `AbstractNavigatorTest` you can extend to assert that any `Navigator` implementation honours the same contract:

```kotlin
import captain.AbstractNavigatorTest
import captain.BasicNavigator
import captain.Navigator
import kotlin.test.Test

class BasicNavigatorTest : AbstractNavigatorTest() {
    override val initial: String = "https://test.com"
    override val navigator: Navigator = BasicNavigator(initial)

    @Test
    fun should_use_basic_navigator() {
        // any extra assertions specific to your implementation
    }
}
```

The inherited tests cover:

- starting at the initial root,
- absolute path navigation (`/customers`),
- preserving query parameters that themselves contain encoded URLs.

You can use the no-arg `NavigateFunction()` helper to stub navigation in unit tests of components that take a `NavigateFunction`. It is a function that just prints what would have happened.

---

## Sample apps

Working samples live under `samples/`:

- `samples/router/compose` is a Compose Multiplatform desktop app. It demonstrates deeply nested routing, parameter accumulation across levels, attached state, query parameters, and async loading patterns. Look at `mainold.kt` for the canonical multi-level example and `tutorial.kt` for shorter, focused snippets.
- `samples/router/react` is a Kotlin React + JSONPlaceholder browser app. It is currently excluded from the build until [KT-80014](https://youtrack.jetbrains.com/issue/KT-80014) is resolved, but the sources are a useful reference for `useNavigate`, `useParams`, the `A` link, redirect routes, and parameter inheritance through nested `Routes`.

---

## API reference

Full Dokka-generated API docs are published at:

[https://asoft-ltd.github.io/captain](https://asoft-ltd.github.io/captain)

---

## Contributing and support

There are several ways to help.

- **Star the repo** if Captain is useful to you. It helps others discover the project.
- **Report issues** so bugs are visible and enhancements get tracked. The issues tab is the right place.
- **Send a pull request** against an open ticket. The codebase is intentionally small; jump in.

---

## Credits

- [andylamax](https://github.com/andylamax) - author and maintainer.
