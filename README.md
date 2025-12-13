# Droid Compose Sample

Jetpack Compose sample that hits the requested deliverables: composable UI basics, list/state handling, Material 3 components, and navigation with a simple architecture (ViewModel + UI state).

## What it shows
- Basic Compose UI: uses `Column`, `Row`, and `Box` across cards, badges, and summaries.
- Material 3 components: Scaffold, TopAppBar, Cards, Buttons, IconButtons, and theming via `DroidAppTheme`.
- Lists + state: `LazyColumn` renders droids; favorites and filter state live in `DroidViewModel` (`StateFlow`).
- Navigation: `NavHost` with a home list and detail screen using Compose Navigation.
- Architecture: single `DroidViewModel` as the source of truth; UI is driven by `DroidUiState` for easy testing/evolution.

## Key files
- `app/src/main/java/com/example/droid/MainActivity.kt` — entry point; wires the theme and ViewModel into the composable tree.
- `app/src/main/java/com/example/droid/ui/DroidApp.kt` — nav host with routes for home and detail.
- `app/src/main/java/com/example/droid/ui/DroidViewModel.kt` — sample data + UI state (`StateFlow`), favorite/filter toggles.
- `app/src/main/java/com/example/droid/ui/screens/HomeScreen.kt` — list, filter control, cards, status pills, empty state.
- `app/src/main/java/com/example/droid/ui/screens/DetailScreen.kt` — detail layout with back nav, status badge, and favorite button.
- `app/src/main/java/com/example/droid/ui/theme/*` — light/dark palettes and typography for Material 3.
- `app/build.gradle.kts` — Compose-enabled Android module with Navigation, Material 3, ViewModel, and coroutines deps.

## Running it (Android Studio)
1. Open the project in Android Studio (Giraffe/Koala or newer).
2. Let Gradle sync; ensure Android SDK 34 is installed. (Min SDK is 26.)
3. Run the `app` configuration on an emulator or device.

## Notes
- This repo is self-contained; no network calls. Sample data is in `DroidViewModel`.
- I couldn’t run the build here (no Android SDK in this environment), so please sync and run locally. If versions conflict, align the Android Gradle Plugin/Kotlin/Compose versions to your installed SDK.
