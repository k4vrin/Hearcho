# ADR 0007 - Adopt the Current Split-Host KMP Module Topology

Status: Accepted
Date: 2026-06-19

## Context

The generated project uses a newer Kotlin Multiplatform structure: a standalone Android application host, Android KMP library targets declared with `androidLibrary {}`, a SwiftUI Xcode host, a shared client framework, and a separate cross-runtime core. Older KMP examples often assume a single `composeApp` module or `androidTarget {}` and do not describe this repository accurately.

## Decision

Keep the generated topology:

- `app/androidApp` is the Android application host.
- `app/sharedUI` is an Android-targeted Compose Multiplatform library.
- `app/sharedLogic` targets Android and iOS and exports `SharedLogic` to Xcode.
- `app/iosApp` is the native SwiftUI Xcode host and is not a Gradle module.
- `core` targets Android, iOS, and JVM for contracts shared with `server`.
- `server` depends only on `core`, never on client modules.

## Consequences

- Android and iOS presentation can evolve independently.
- Server contracts remain available without making client logic JVM-compatible.
- Gradle examples must use the new Android KMP library DSL.
- iOS linkage and tests require Xcode validation in addition to Gradle validation.
- Shared Compose UI is not assumed to render on iOS.

## Alternatives Considered

- A single `composeApp` for Android and iOS: rejected because the product requires a native SwiftUI host.
- Add JVM to `sharedLogic`: rejected because the server should share contracts through `core`, not client behavior.
- Move all shared code into `core`: rejected because client orchestration and server-safe contracts have different dependency boundaries.
