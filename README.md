# Cats Compose Sample

Jetpack Compose sample themed for cats: basic composables, lists with state, Material 3 components, navigation, permissions, location, maps (OpenStreetMap), CameraX, and Firebase push.

## What it shows
- Basic Compose UI: `Column`, `Row`, `Box` in cards, badges, and summaries.
- Material 3 components: Scaffold, TopAppBar, Cards, Buttons, IconButtons, and theming via `CatAppTheme`.
- Lists + state: `LazyColumn` renders cats; favorites and filter state live in `CatViewModel` (`StateFlow`).
- Navigation: `NavHost` with a home list, detail screen, and a Field Kit screen.
- Architecture: single `CatViewModel` as the source of truth; UI is driven by `CatUiState`.
- Permissions: runtime handling for location and camera (plus notifications on Android 13+).
- Location + Maps: fetch current location (Play Services Fused Location) and show it on OpenStreetMap via osmdroid.
- CameraX: live preview using CameraX (back camera) in the Field Kit screen, plus capture + thumbnail of the latest photo.
- Firebase Cloud Messaging: notification handling via `CatFirebaseMessagingService` with deep-link extras; token fetch helper in Field Kit.

## Key files
- `app/src/main/java/com/example/cat/MainActivity.kt` — entry point; sets osmdroid user agent, handles notification intents, requests notification permission, and wires the theme and ViewModel into the composable tree.
- `app/src/main/java/com/example/cat/ui/CatApp.kt` — nav host with routes for home, detail, and tools (Field Kit).
- `app/src/main/java/com/example/cat/ui/CatViewModel.kt` — sample cat data + UI state (`StateFlow`), favorite/filter toggles.
- `app/src/main/java/com/example/cat/ui/screens/HomeScreen.kt` — list, filter control, cards, status pills, empty state, entry to Field Kit.
- `app/src/main/java/com/example/cat/ui/screens/DetailScreen.kt` — detail layout with back nav, status badge, and favorite button.
- `app/src/main/java/com/example/cat/ui/screens/ToolsScreen.kt` — Field Kit: permissions, location fetch, OpenStreetMap (osmdroid) map, CameraX preview + capture, and FCM token display.
- `app/src/main/java/com/example/cat/ui/theme/*` — light/dark palettes and typography for Material 3.
- `app/src/main/java/com/example/cat/notifications/CatFirebaseMessagingService.kt` — Firebase message handler + notification builder.
- `app/build.gradle.kts` — Compose-enabled Android module with Navigation, Material 3, ViewModel, coroutines, Play Services location, CameraX, osmdroid, Coil, and Firebase Messaging deps.

## Running it (Android Studio)
1. Open the project in Android Studio (Giraffe/Koala or newer).
2. Let Gradle sync; ensure Android SDK 34 is installed. (Min SDK is 26.)
3. Place your `google-services.json` under `app/` (replace the placeholder) and sync so Firebase Messaging can build.
4. Run the `app` configuration on an emulator or device. Make sure a device/emulator is online.
5. For emulator: enable camera and set a location (Extended controls ? Location). Grant camera/location/notification permissions on first launch.
6. For push: send a test notification from Firebase Console. Include `catId=<id>` or `destination=tools` in data payloads to deep-link.

## Notes
- This repo is self-contained; no network calls beyond tile loading for OSM and Firebase push.
- Build was last verified locally via `./gradlew assembleDebug`. If versions conflict, align the Android Gradle Plugin/Kotlin/Compose versions to your installed SDK.
- OpenStreetMap runs via osmdroid (no billing needed). CameraX preview needs camera permission; location/map needs location permission; push needs notification permission on Android 13+.
