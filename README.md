# Hearcho

Hearcho is a split-host Kotlin Multiplatform project targeting Android, iOS, and a Ktor JVM server. See [`docs/README.md`](./docs/README.md) for product and architecture documentation.

## Project Layout

- [`app/androidApp`](./app/androidApp): standalone Android application host using Compose.
- [`app/sharedUI`](./app/sharedUI): Compose Multiplatform library with an Android target; consumed by `androidApp`.
- [`app/sharedLogic`](./app/sharedLogic): shared Android/iOS client logic; exports the static `SharedLogic` framework.
- [`app/iosApp`](./app/iosApp): native SwiftUI Xcode host; not a Gradle module.
- [`core`](./core): Android/iOS/JVM KMP contracts and domain primitives shared by clients and server.
- [`server`](./server): Ktor JVM backend; depends on `core` only.

The KMP Android libraries use the current `com.android.kotlin.multiplatform.library` plugin and `androidLibrary {}` DSL. `sharedUI` does not target iOS, and `sharedLogic` does not target JVM.

## Build and Test

```bash
./gradlew :app:androidApp:assembleDebug
./gradlew :app:sharedUI:testAndroidHostTest
./gradlew :app:sharedLogic:testAndroidHostTest
./gradlew :app:sharedLogic:iosSimulatorArm64Test
./gradlew :core:allTests
./gradlew :server:test
./gradlew :server:run
```

Open `app/iosApp/iosApp.xcodeproj` in Xcode to build and run the iOS application.
