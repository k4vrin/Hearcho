# Hearcho Project Context for AI Agents

Read this file before changing code. The repository is an early scaffold, while [`ROADMAP.md`](./ROADMAP.md) describes the approved target implementation.

## Product and Runtime

Hearcho is a realtime voice-room product with:

- Android Compose client.
- Native SwiftUI iOS client.
- Shared Kotlin contracts and client behavior.
- Ktor JVM backend. There is no Spring Boot application.
- LiveKit as the first voice provider behind replaceable Hearcho-owned ports.

## Actual Module Topology

| Module | Actual targets | Purpose |
| --- | --- | --- |
| `app/androidApp` | Android application | Compose host and Android wiring. |
| `app/sharedUI` | Android KMP library | Compose UI consumed by `androidApp`; no iOS target. |
| `app/sharedLogic` | Android, iOS arm64, iOS simulator arm64 | Shared client logic and exported `SharedLogic` framework; no JVM target. |
| `app/iosApp` | Xcode iOS application | SwiftUI host; not included in Gradle settings. |
| `core` | Android, iOS arm64, iOS simulator arm64, JVM | Domain primitives and wire contracts shared with `server`. |
| `server` | JVM | Ktor backend; depends on `core`. |

The KMP library modules use `com.android.kotlin.multiplatform.library` and `androidLibrary {}`. Do not convert them to older `androidTarget {}` examples.

## Current Implementation

Implemented:

- Module and source-set scaffold.
- Generated placeholder Kotlin, Compose, SwiftUI, and Ktor code.
- Version catalog with Kotlin, AGP, Compose, Ktor, AndroidX, and Logback.
- Product, architecture, stack, roadmap, structure, and ADR documents.

Not implemented:

- Authentication, rooms, realtime contracts, moderation, or notifications.
- PostgreSQL, Redis, RabbitMQ, Exposed, Flyway, or HikariCP.
- Ktor Client, Koin, Decompose, SQLDelight, Multiplatform Settings, or Arrow.
- LiveKit adapters or provider-neutral voice ports.

## Finalized Stack Rules

- Backend is Ktor on Netty with PostgreSQL, Exposed DSL, HikariCP, Flyway, Redis/Lettuce, RabbitMQ, and OpenTelemetry.
- Shared client uses Ktor Client, kotlinx.serialization, coroutines/`StateFlow`, Koin, Decompose/Essenty, SQLDelight, and Multiplatform Settings.
- Feature state uses immutable models and pure reducers exposed through `StateFlow`.
- Orbit MVI is excluded. Arrow is approved only for `Either`, `Raise`, `NonEmptyList`, selective Optics, and cancellation-safe `parZip` usage under ADR 0010.
- Android uses Compose Material 3; iOS uses SwiftUI.
- LiveKit is selected, but feature code depends on `VoiceEngine`, `VoiceTokenProvider`, and provider-neutral models.
- LiveKit SDK types stay inside native/server adapter packages so mediasoup can replace those adapters later.

## Dependency Rules

- `core` has no dependency on client, server implementation, UI, or provider SDKs.
- `app/sharedLogic` depends on `core` and provider-neutral ports, never Android UI, SwiftUI, Ktor server, or LiveKit types.
- `app/sharedUI` depends on `app/sharedLogic`; it renders state and emits actions.
- `app/androidApp` owns Android startup, permissions, audio, FCM, maps, and the LiveKit Android adapter.
- `app/iosApp` owns SwiftUI, Apple frameworks, Keychain, APNs, and the LiveKit Swift adapter.
- `server` depends on `core`; it owns Ktor routes, persistence, jobs, and the LiveKit server adapter.
- Product state travels through Ktor APIs/WebSockets. Media state is adapted from the active voice provider.

## Implementation Patterns

- Use sealed domain errors and states; avoid `!!`.
- Keep failures project-owned when using Arrow; `Either` and `Raise` express outcomes but do not define product error semantics.
- Use `NonEmptyList` when failure collections must be non-empty, Optics for justified nested immutable updates, and `parZip` only for independent suspend operations.
- Use structured coroutine scopes and cancel them with component lifecycle.
- Use `suspend` for operations and `StateFlow` for observable state.
- Keep transport DTOs separate from UI state.
- Keep PostgreSQL authoritative; Redis data must be disposable.
- Publish durable side effects through a transactional outbox and idempotent RabbitMQ consumers.
- Put secrets in environment/secret management, never source or logs.
- Introduce a dependency with its first production use and tests, not as empty wiring.

## Documentation Contract

- Module/source-set change: update `PROJECT_STRUCTURE.md`, `ARCHITECTURE.md`, and ADRs if the boundary changes.
- Dependency decision: update `TECH_STACK.md`, `ROADMAP.md`, and the version catalog when installed.
- Completed work: check the lowest-level roadmap items and relevant phase exit criteria.
- Provider boundary change: update ADR 0009 and prove fake-provider contract tests still pass.

## Validation Commands

```bash
./gradlew :core:allTests
./gradlew :app:sharedLogic:allTests
./gradlew :app:sharedUI:testAndroidHostTest
./gradlew :app:androidApp:assembleDebug
./gradlew :server:test
```

Build `app/iosApp/iosApp.xcodeproj` after changes to `SharedLogic`, Swift bridges, iOS configuration, or native SDKs.

## Avoid

- Do not introduce Spring Boot, Socket.io, or Orbit MVI without an accepted ADR.
- Do not extend Arrow beyond ADR 0010's approved types and patterns without another architecture decision.
- Do not add nonexistent iOS/JVM targets to `sharedUI` or JVM to `sharedLogic` casually.
- Do not expose LiveKit or future mediasoup types through `core`, shared feature APIs, or public HTTP/WebSocket contracts.
- Do not infer authoritative room membership from the voice provider.
- Do not put business rules in Compose or SwiftUI views.
- Do not treat RabbitMQ as an event store.
