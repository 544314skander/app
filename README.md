# Cats Compose Sample

Jetpack Compose sample themed for cats: basic composables, lists with state, Material 3 components, navigation, permissions, location, maps (OpenStreetMap), and CameraX.

## What it shows
- Basic Compose UI: `Column`, `Row`, `Box` in cards, badges, and summaries.
- Material 3 components: Scaffold, TopAppBar, Cards, Buttons, IconButtons, and theming via `CatAppTheme`.
- Lists + state: `LazyColumn` renders cats; favorites and filter state live in `CatViewModel` (`StateFlow`).
- Navigation: `NavHost` with a home list, detail screen, and a Field Kit screen.
- Architecture: single `CatViewModel` as the source of truth; UI is driven by `CatUiState`.
- Permissions: runtime handling for location and camera.
- Location + Maps: fetch current location (Play Services Fused Location) and show it on OpenStreetMap via osmdroid.
- CameraX: live preview using CameraX (back camera) in the Field Kit screen.

## Key files
- `app/src/main/java/com/example/cat/MainActivity.kt` — entry point; sets osmdroid user agent and wires the theme and ViewModel into the composable tree.
- `app/src/main/java/com/example/cat/ui/CatApp.kt` — nav host with routes for home, detail, and tools (Field Kit).
- `app/src/main/java/com/example/cat/ui/CatViewModel.kt` — sample cat data + UI state (`StateFlow`), favorite/filter toggles.
- `app/src/main/java/com/example/cat/ui/screens/HomeScreen.kt` — list, filter control, cards, status pills, empty state, entry to Field Kit.
- `app/src/main/java/com/example/cat/ui/screens/DetailScreen.kt` — detail layout with back nav, status badge, and favorite button.
- `app/src/main/java/com/example/cat/ui/screens/ToolsScreen.kt` — Field Kit: permission requests, location fetch, OpenStreetMap (osmdroid) map, and CameraX preview.
- `app/src/main/java/com/example/cat/ui/theme/*` — light/dark palettes and typography for Material 3.
- `app/build.gradle.kts` — Compose-enabled Android module with Navigation, Material 3, ViewModel, coroutines, Play Services location, CameraX, and osmdroid deps.

## Running it (Android Studio)
1. Open the project in Android Studio (Giraffe/Koala or newer).
2. Let Gradle sync; ensure Android SDK 34 is installed. (Min SDK is 26.)
3. Run the `app` configuration on an emulator or device. Make sure a device/emulator is online.
4. For emulator: enable camera and set a location (Extended controls → Location). Grant camera/location permissions on first launch.

## Notes
- This repo is self-contained; no network calls beyond tile loading for OSM. Sample data is in `CatViewModel`.
- Build was last verified locally via `./gradlew assembleDebug`. If versions conflict, align the Android Gradle Plugin/Kotlin/Compose versions to your installed SDK.
- OpenStreetMap runs via osmdroid (no billing needed). CameraX preview needs camera permission; location/map needs location permission.
