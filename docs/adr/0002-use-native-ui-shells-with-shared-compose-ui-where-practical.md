# ADR 0002 - Use Native UI Shells with Shared Compose UI Where Practical

Status: Accepted
Date: 2026-06-19

## Context

Hearcho needs a polished Android and iOS experience. Android is naturally aligned with Jetpack Compose. iOS should preserve native SwiftUI behavior, Apple platform conventions, accessibility, permissions, and App Store expectations.

## Decision

Use a native Android app shell with Compose and a native iOS app shell with SwiftUI. Keep `app/sharedUI` as the Android-targeted Compose KMP library used by the Android host. Do not assume it is an iOS UI-sharing module.

## Consequences

- Android can move quickly with Compose and shared Kotlin state.
- iOS remains SwiftUI-first and aligned with Apple platform conventions.
- Compose reuse is available across Android surfaces without forcing Compose into iOS.
- Some UI behavior may need separate Android and iOS implementations.

## Alternatives Considered

- Compose Multiplatform for all iOS UI: maximizes UI sharing, but risks weaker native iOS fit for a voice/social product.
- Fully separate native UI with no shared UI module: maximizes native fidelity, but gives up useful Compose reuse already present in the scaffold.
