# Hearcho Technology Stack

This document is the authoritative technology baseline. A dependency is either **Active**, **Committed**, or **Excluded**:

- **Active**: already present in the repository.
- **Committed**: approved for implementation and represented in the roadmap.
- **Excluded**: intentionally outside the baseline; adding it requires an ADR.

Versions already in the version catalog are recorded exactly. Versions for committed dependencies must be selected when the dependency is added, then pinned in `gradle/libs.versions.toml` after compatibility checks.

## Current Scaffold

The repository follows the current split-host Kotlin Multiplatform scaffold rather than the older single `composeApp` layout.

| Module | Gradle/plugin model | Targets | Role |
| --- | --- | --- | --- |
| `app/androidApp` | `com.android.application` | Android | Android application host; consumes `sharedUI`. |
| `app/sharedUI` | KMP + `com.android.kotlin.multiplatform.library` + Compose | Android library | Shared Compose UI currently published only to the Android host. |
| `app/sharedLogic` | KMP + `com.android.kotlin.multiplatform.library` | Android, iOS device, iOS simulator | Shared client logic; exports the static `SharedLogic` framework to Xcode. |
| `app/iosApp` | Xcode SwiftUI application | iOS | Native iOS host; consumes `SharedLogic`. It is not a Gradle module. |
| `core` | KMP + `com.android.kotlin.multiplatform.library` | Android, iOS device, iOS simulator, JVM | Contracts and domain primitives shared by clients and server. |
| `server` | Kotlin/JVM + Ktor | JVM | Backend application; consumes `core`. |

Important consequences:

- `androidLibrary {}` is the Android target DSL for KMP libraries in this scaffold; do not rewrite it to the older `androidTarget {}` form.
- Android host dependencies use the top-level `dependencies {}` block.
- KMP source-set dependencies stay inside `kotlin { sourceSets { ... } }`.
- `sharedUI` has no iOS target. SwiftUI is the iOS presentation layer.
- `sharedLogic` has no JVM target. Server sharing happens through `core`, not through client logic.

## Active Versions

Source of truth: [`gradle/libs.versions.toml`](../gradle/libs.versions.toml).

| Technology                            | Version        | Status                                                      |
|---------------------------------------|----------------|-------------------------------------------------------------|
| Kotlin                                | 2.4.0          | Active                                                      |
| Android Gradle Plugin                 | 9.0.1          | Active                                                      |
| Arrow Core, Fx Coroutines, and Optics | 2.2.3          | Active foundation in `core` and `app/sharedLogic`           |
| Compose Multiplatform                 | 1.11.1         | Active                                                      |
| Compose Material 3                    | 1.11.0-alpha07 | Active; reassess before release                             |
| Ktor                                  | 3.5.0          | Active                                                      |
| kotlinx.coroutines                    | 1.11.0         | Active in `app/sharedLogic`; test support in shared modules |
| kotlinx.serialization JSON            | 1.11.0         | Active in `core`                                            |
| Turbine                               | 1.2.1          | Active in `app/sharedLogic/commonTest`                      |
| Android compile/target SDK            | 36             | Active                                                      |
| Android minimum SDK                   | 24             | Active                                                      |
| JVM bytecode target                   | 11             | Active                                                      |
| Logback                               | 1.5.34         | Active on server                                            |

Do not upgrade versions as part of feature work. Dependency upgrades require a dedicated change that builds Android, shared KMP targets, iOS framework linkage, and server tests.

## Finalized Client Stack

| Concern                       | Choice                                                     | Status            | Placement                                                                      |
|-------------------------------|------------------------------------------------------------|-------------------|--------------------------------------------------------------------------------|
| Language and sharing          | Kotlin Multiplatform                                       | Active            | `core`, `app/sharedLogic`                                                      |
| Async/state                   | kotlinx.coroutines + `Flow`/`StateFlow`                    | Active foundation | `app/sharedLogic`; expand when first consumers are added                       |
| Serialization                 | kotlinx.serialization JSON                                 | Active foundation | `core`; expand to clients/server with transport setup                          |
| HTTP/WebSocket client         | Ktor Client                                                | Committed         | `app/sharedLogic` with Darwin/OkHttp engines                                   |
| Dependency injection          | Koin                                                       | Committed         | Shared definitions; native host startup modules                                |
| Navigation/lifecycle          | Decompose + Essenty                                        | Committed         | `app/sharedLogic`; native hosts adapt components                               |
| Feature state                 | Immutable state + actions + pure reducers over `StateFlow` | Committed         | `app/sharedLogic`                                                              |
| Typed outcomes and validation | Arrow `Either`, `Raise`, `NonEmptyList`                    | Active, scoped    | `core`, shared application logic, selected server services when first consumed |
| Immutable model updates       | Arrow Optics                                               | Active, selective | `core` models with repeated nested immutable updates only                      |
| Parallel composition          | Arrow Fx Coroutines `parZip`                               | Active, selective | Independent shared-client suspend operations                                   |
| Local relational cache        | SQLDelight                                                 | Committed         | `app/sharedLogic` with Android/iOS drivers                                     |
| Preferences                   | Multiplatform Settings                                     | Committed         | `app/sharedLogic`; never store raw secrets unprotected                         |
| Android UI                    | Compose Material 3                                         | Active            | `app/sharedUI`, `app/androidApp`                                               |
| iOS UI                        | SwiftUI                                                    | Active            | `app/iosApp`                                                                   |
| Tests                         | kotlin-test, kotlinx-coroutines-test, Turbine              | Active foundation | `core/commonTest`, `app/sharedLogic/commonTest`                                |

### Deliberate exclusions

- **Orbit MVI** is excluded from the baseline. Decompose components plus `StateFlow` reducers cover lifecycle, navigation, state, and effects without a second container abstraction.
- **Unbounded Arrow adoption** is excluded. Use Arrow only for the approved `Either`, `Raise`, `NonEmptyList`, Optics, and `parZip` patterns; keep domain failures project-owned and do not wrap all Kotlin APIs mechanically.
- **Compose for iOS** is excluded. `sharedUI` remains Android-targeted and iOS remains SwiftUI-first.
- **Client domain access from views** is excluded. Compose and SwiftUI render state and emit actions through feature components.

## Finalized Server Stack

| Concern | Choice | Status |
| --- | --- | --- |
| Runtime/API | Ktor on Netty | Active |
| JSON/contracts | kotlinx.serialization | Committed |
| Authentication | Ktor Authentication + JWT access/refresh sessions | Committed |
| Password hashing | Argon2id through a maintained JVM library | Committed |
| Dependency composition | Explicit constructors with a small Koin application module | Committed |
| Primary database | PostgreSQL | Committed |
| SQL access | Exposed DSL | Committed |
| Connection pool | HikariCP | Committed |
| Schema migrations | Flyway | Committed |
| Ephemeral presence/cache | Redis with Lettuce | Committed |
| Asynchronous work | RabbitMQ Java client | Committed |
| Delivery reliability | Transactional outbox + idempotent consumers | Committed |
| Realtime product state | Ktor WebSockets | Committed |
| Logging | SLF4J + Logback structured JSON output | Committed |
| Metrics/tracing | OpenTelemetry | Committed |
| Integration tests | Ktor test host + Testcontainers | Committed |
| Local infrastructure | Docker Compose | Committed |

PostgreSQL is authoritative. Redis contains disposable state only. RabbitMQ is a work queue and fan-out mechanism, not an event store.

## Finalized Voice Stack

| Concern | Choice | Status |
| --- | --- | --- |
| SFU/platform | LiveKit, cloud-first | Committed |
| Android media SDK | Native LiveKit Android SDK | Committed |
| iOS media SDK | Native LiveKit Swift SDK | Committed |
| Client abstraction | Hearcho-owned `VoiceEngine` port | Committed |
| Server abstraction | Hearcho-owned `VoiceTokenProvider` and webhook ports | Committed |
| Token issuance | Provider-neutral Ktor endpoint backed by a LiveKit adapter | Committed |
| Product events | Ktor WebSockets, separate from media transport | Committed |
| Self-hosting | Deferred operational option, not an MVP requirement | Committed boundary |

LiveKit is selected because it supplies maintained native SDKs and an operationally achievable SFU path. It is an infrastructure adapter, not a domain dependency. Shared state, use cases, API contracts, and room policy must not expose LiveKit classes or terminology. A future mediasoup implementation must be replaceable at composition roots without rewriting room features.

## Native Platform Integrations

| Area | Android | iOS |
| --- | --- | --- |
| Secure credentials | Android Keystore-backed storage | Keychain |
| Push | Firebase Cloud Messaging | APNs |
| Audio | `AudioManager`, audio focus, foreground service as required | `AVAudioSession`; CallKit only for qualifying call flows |
| Location | Fused Location Provider | CoreLocation |
| Maps | Google Maps | MapKit |
| Voice | LiveKit Android SDK | LiveKit Swift SDK |

PushKit is not part of the baseline. It requires a separate ADR proving the product behavior complies with Apple's VoIP requirements.

## Dependency Adoption Order

The detailed tasks live in [`ROADMAP.md`](./ROADMAP.md). The required order is:

1. Add serialization, coroutines test tooling, and shared contract tests.
2. Add scoped Arrow Core, Optics, and Fx Coroutines setup with cross-target compile tests.
3. Define project-owned failures before exposing `Either`, `Raise`, or `NonEmptyList` from domain APIs.
4. Add server PostgreSQL stack: Flyway, HikariCP, Exposed, PostgreSQL driver.
5. Add Ktor auth, WebSockets, status handling, and rate limiting.
6. Add shared Ktor Client engines and typed API error mapping.
7. Add Koin composition roots.
8. Add Decompose/Essenty and the first feature component.
9. Add SQLDelight and Multiplatform Settings only when their first persisted models exist.
10. Add Redis only with the first presence/session use case.
11. Add RabbitMQ only with the outbox publisher and first consumer in the same phase.
12. Define provider-neutral voice ports and contract tests before adding LiveKit adapters.
13. Add LiveKit server, Android, and iOS adapters behind those ports.
14. Add OpenTelemetry after stable route and room-flow spans can be named.

## Validation Matrix

```bash
./gradlew :core:allTests
./gradlew :app:sharedLogic:allTests
./gradlew :app:sharedUI:testAndroidHostTest
./gradlew :app:androidApp:assembleDebug
./gradlew :server:test
```

The iOS host must also be built from `app/iosApp/iosApp.xcodeproj` after any `SharedLogic` framework, Swift bridge, or native SDK change.
