# Hearcho Delivery Roadmap

This roadmap is organized as phases, workstreams, tasks, and atomic subtasks. Every phase separates Server, Shared Client, Android, and iOS work so ownership, sequencing, and completion are explicit.

## Checklist Rules

- Mark an item complete only when its implementation and focused tests pass.
- A `Task` groups one coherent deliverable; its nested checkboxes are atomic subtasks.
- Treat each lowest-level subtask as one reviewable change whenever practical.
- Mark a parent task complete only after all nested subtasks are complete.
- Do not start a dependent item before its prerequisite is complete.
- Update architecture, stack, structure, or ADR documents in the same change that alters them.
- A phase is complete only when every exit criterion is checked.

## Phase 0: Stabilize the Scaffold and Toolchain

### Shared/Foundation

- [x] Task 0.F1: Confirm architecture and decisions.
  - [x] Generate the split-host KMP scaffold.
  - [x] Create `core`, `app/sharedLogic`, `app/sharedUI`, `app/androidApp`, `app/iosApp`, and `server` boundaries.
  - [x] Document the active module graph.
  - [x] Finalize the target technology stack.
  - [x] Record the module-topology ADR.
  - [x] Record the application-stack ADR.
  - [x] Select LiveKit and supersede the deferred SFU ADR.

- [x] Task 0.F2: Install shared Kotlin foundations.
  - [x] Add kotlinx.serialization plugin version alias.
  - [x] Apply kotlinx.serialization to `core`.
  - [x] Add kotlinx.serialization JSON to `core/commonMain`.
  - [x] Add kotlinx.coroutines core to `app/sharedLogic/commonMain`.
  - [x] Add kotlinx-coroutines-test to shared test source sets.
  - [x] Add Turbine to `app/sharedLogic/commonTest`.

- [ ] Task 0.F3: Configure code quality.
  - [ ] Add ktlint or Spotless plugin alias.
  - [ ] Configure formatting for all Kotlin modules.
  - [ ] Add a formatting check command to `docs/PROJECT_CONTEXT.md`.
  - [ ] Add Detekt plugin alias.
  - [ ] Configure Detekt for production Kotlin source sets.

- [ ] Task 0.F4: Provision local infrastructure.
  - [ ] Add `.env.example` with non-secret server keys.
  - [ ] Add Docker Compose with PostgreSQL service.
  - [ ] Add Redis service to Docker Compose.
  - [ ] Add RabbitMQ service and management UI to Docker Compose.
  - [ ] Add health checks for all local services.
  - [ ] Document local service ports.

- [ ] Task 0.F5: Set up scoped Arrow foundations.
  - [ ] Select one Arrow version compatible with Kotlin 2.4.0 and all configured KMP targets.
  - [ ] Add the Arrow version and library aliases to `gradle/libs.versions.toml`.
  - [ ] Add `arrow-core` to `core/commonMain` for `Either`, `Raise`, and `NonEmptyList`.
  - [ ] Add `arrow-core` to `app/sharedLogic/commonMain` when shared client APIs consume Arrow types.
  - [ ] Add `arrow-fx-coroutines` to `app/sharedLogic/commonMain` for `parZip`.
  - [ ] Add Arrow dependencies to `server` only where Ktor application services consume shared Arrow outcomes.
  - [ ] Add the Arrow Optics plugin and runtime dependency to the modules that declare optic-enabled models.
  - [ ] Configure Optics code generation for KMP common source sets.
  - [ ] Add common compile-smoke tests for `Either`, `Raise`, `NonEmptyList`, Optics, and `parZip`.
  - [ ] Verify Android, iOS simulator, JVM/core, and server compilation with Arrow enabled.
  - [ ] Document approved Arrow usage and prohibit provider SDK types from entering Arrow-based domain APIs.

### Server

- [ ] Task 0.S1: Establish Ktor bootstrap and configuration.
  - [ ] Move Ktor bootstrap into an `Application.module` function.
  - [ ] Add a typed environment configuration loader.
  - [ ] Fail startup when required configuration is absent.
  - [ ] Keep secrets out of committed configuration files.

- [ ] Task 0.S2: Expose server liveness.
  - [ ] Add a `/health/live` route.
  - [ ] Add a Ktor test for `/health/live`.

### Shared Client

- [ ] Task 0.C1: Replace generated shared placeholders.
  - [ ] Delete generated greeting behavior from `app/sharedLogic` after replacement tests exist.
  - [ ] Add a shared `AppEnvironment` model.
  - [ ] Add a shared `Clock` interface.
  - [ ] Add Android and iOS `Clock` implementations only if the common implementation is insufficient.

- [ ] Task 0.C2: Verify shared target execution.
  - [ ] Add one common test proving shared logic executes on supported source sets.

### Android Client

- [ ] Task 0.A1: Establish the Android shell.
  - [ ] Replace generated greeting UI with an application shell placeholder.
  - [ ] Add debug and release build configuration fields for API base URL.

- [ ] Task 0.A2: Verify Android startup and build.
  - [ ] Add a smoke test that creates `MainActivity`.
  - [ ] Verify `:app:androidApp:assembleDebug`.

### iOS Client

- [ ] Task 0.I1: Establish the iOS shell and framework linkage.
  - [ ] Replace generated greeting UI with a SwiftUI application shell placeholder.
  - [ ] Add debug and release API base URL values to `Config.xcconfig`.
  - [ ] Link the generated `SharedLogic` framework in the Xcode build.

- [ ] Task 0.I2: Verify iOS integration and build.
  - [ ] Add one XCTest that imports `SharedLogic`.
  - [ ] Verify the iOS simulator build from Xcode.

### Exit Criteria

- [ ] Task 0.E1: Complete Phase 0 exit criteria.
  - [ ] All Gradle modules build from a clean checkout.
  - [ ] Android debug assembly passes.
  - [ ] iOS simulator build passes.
  - [ ] Server tests pass without external services.
  - [ ] Formatting and static analysis run in CI.

## Phase 1: Establish Contracts and Persistence Foundations

### Shared/Foundation

- [ ] Task 1.F1: Define typed identifiers.
  - [ ] Add `UserId` inline value class.
  - [ ] Add `RoomId` inline value class.
  - [ ] Add `CommunityId` inline value class.
  - [ ] Add `SessionId` inline value class.
  - [ ] Add validation for blank identifiers.
  - [ ] Add serialization round-trip tests for every identifier.

- [ ] Task 1.F2: Define core domain models.
  - [ ] Add `UserProfile` domain model.
  - [ ] Add `RoomSummary` domain model.
  - [ ] Add `RoomParticipant` domain model.
  - [ ] Add `RoomRole` sealed model.
  - [ ] Add `RoomLifecycle` sealed model.

- [ ] Task 1.F3: Define API conventions.
  - [ ] Add `ApiErrorCode` enum.
  - [ ] Add versioned `ApiError` response contract.
  - [ ] Add cursor pagination request contract.
  - [ ] Add cursor pagination response contract.
  - [ ] Add contract serialization golden tests.

- [ ] Task 1.F4: Establish typed domain outcomes and validation.
  - [ ] Define a sealed `DomainFailure` hierarchy independent of transport and UI concerns.
  - [ ] Define a project result alias based on `Either<DomainFailure, A>` where it improves public domain APIs.
  - [ ] Use `Raise<DomainFailure>` for short-circuiting multi-step domain workflows.
  - [ ] Use `NonEmptyList<ValidationFailure>` to guarantee at least one validation error.
  - [ ] Accumulate independent validation failures before constructing domain values.
  - [ ] Add Optics annotations only to immutable models that require repeated nested updates.
  - [ ] Add tests for `Either` success/left behavior and `Raise` short-circuit behavior.
  - [ ] Add tests proving `NonEmptyList` validation accumulation preserves deterministic error order.
  - [ ] Add generated Optics compile and nested-update tests.

### Server

- [ ] Task 1.S1: Install the PostgreSQL persistence stack.
  - [ ] Add PostgreSQL JDBC driver.
  - [ ] Add HikariCP.
  - [ ] Add Flyway.
  - [ ] Add Exposed DSL modules.

- [ ] Task 1.S2: Configure database startup.
  - [ ] Create a Hikari data-source factory.
  - [ ] Add a Flyway startup migration runner.

- [ ] Task 1.S3: Create initial database schema.
  - [ ] Create migration `V1__create_users.sql`.
  - [ ] Create migration `V2__create_sessions.sql`.
  - [ ] Create migration `V3__create_rooms.sql`.
  - [ ] Create migration `V4__create_room_participants.sql`.
  - [ ] Create migration `V5__create_outbox.sql`.

- [ ] Task 1.S4: Implement persistence primitives.
  - [ ] Add Exposed table mappings for each migration.
  - [ ] Add a transaction executor that runs on an IO dispatcher.

- [ ] Task 1.S5: Verify persistence integration.
  - [ ] Add PostgreSQL Testcontainers dependency.
  - [ ] Add a migration integration test against PostgreSQL.
  - [ ] Add a repository integration test that inserts and reads a user.

### Shared Client

- [ ] Task 1.C1: Install multiplatform networking.
  - [ ] Add Ktor Client core.
  - [ ] Add Ktor content negotiation.
  - [ ] Add Ktor kotlinx JSON serialization.
  - [ ] Add Ktor WebSocket client support.
  - [ ] Add OkHttp engine to `androidMain`.
  - [ ] Add Darwin engine to `iosMain`.

- [ ] Task 1.C2: Implement the shared HTTP boundary.
  - [ ] Add an `HttpClient` factory with explicit timeouts.
  - [ ] Add typed API response decoding.
  - [ ] Map transport failures to sealed client errors.

- [ ] Task 1.C3: Verify HTTP behavior.
  - [ ] Add fake-engine tests for success responses.
  - [ ] Add fake-engine tests for API error responses.
  - [ ] Add fake-engine tests for malformed responses.

- [ ] Task 1.C4: Establish safe parallel composition with `parZip`.
  - [ ] Define a rule that `parZip` is used only for independent suspend operations.
  - [ ] Combine parallel results into a typed `Either` or `Raise` workflow without throwing for expected failures.
  - [ ] Add a test proving sibling work is cancelled when one `parZip` branch fails unexpectedly.
  - [ ] Add a test proving expected typed failures are mapped deterministically.
  - [ ] Add a test proving parent-scope cancellation propagates through every `parZip` branch.

### Android Client

- [ ] Task 1.A1: Configure Android networking.
  - [ ] Supply Android engine dependencies through the app composition root.
  - [ ] Add debug-only network security configuration for local HTTP development.
  - [ ] Verify release builds reject cleartext traffic.

### iOS Client

- [ ] Task 1.I1: Configure iOS networking.
  - [ ] Supply Darwin engine dependencies through the Swift host bridge.
  - [ ] Configure development-only local networking exceptions if required.
  - [ ] Verify production App Transport Security remains restrictive.

### Exit Criteria

- [ ] Task 1.E1: Complete Phase 1 exit criteria.
  - [ ] Shared contracts serialize identically on JVM, Android, and iOS targets.
  - [ ] Server migrations apply to an empty PostgreSQL database.
  - [ ] Shared clients decode one server-owned contract through a fake engine.
  - [ ] No platform imports exist in `core/commonMain`.

## Phase 2: Deliver Authentication End to End

### Shared/Foundation

- [ ] Task 2.F1: Define authentication contracts.
  - [ ] Add registration request and response contracts.
  - [ ] Add sign-in request and response contracts.
  - [ ] Add refresh-session request and response contracts.
  - [ ] Add sign-out request contract.
  - [ ] Define access-token and refresh-token expiry fields.
  - [ ] Add auth contract serialization tests.

### Server

- [ ] Task 2.S1: Configure Ktor authentication infrastructure.
  - [ ] Add Ktor content negotiation plugin.
  - [ ] Add Ktor status pages plugin.
  - [ ] Add request validation plugin or route-level validators.
  - [ ] Add JWT authentication dependency.
  - [ ] Add Argon2id password hashing dependency.
  - [ ] Implement password hash service.
  - [ ] Test password hash and verify behavior.

- [ ] Task 2.S2: Implement auth persistence.
  - [ ] Implement user repository `create`.
  - [ ] Implement user repository `findByEmail`.
  - [ ] Implement session repository `create`.
  - [ ] Implement session repository `rotate`.
  - [ ] Implement session repository `revoke`.

- [ ] Task 2.S3: Implement authentication services.
  - [ ] Implement registration service.
  - [ ] Reject duplicate normalized email addresses.
  - [ ] Implement sign-in service.
  - [ ] Return a generic error for invalid credentials.
  - [ ] Implement refresh-token rotation.
  - [ ] Revoke the previous refresh token during rotation.
  - [ ] Implement sign-out session revocation.

- [ ] Task 2.S4: Expose authentication routes.
  - [ ] Add `/api/v1/auth/register`.
  - [ ] Add `/api/v1/auth/sign-in`.
  - [ ] Add `/api/v1/auth/refresh`.
  - [ ] Add `/api/v1/auth/sign-out`.

- [ ] Task 2.S5: Verify and protect authentication.
  - [ ] Add route tests for every success case.
  - [ ] Add route tests for every validation and authorization failure.
  - [ ] Add auth endpoint rate limits.

### Shared Client

- [ ] Task 2.C1: Establish auth composition and data ports.
  - [ ] Add Koin core.
  - [ ] Define a shared application module.
  - [ ] Add `AuthRemoteDataSource`.
  - [ ] Add `SessionStore` interface.

- [ ] Task 2.C2: Implement session persistence adapters.
  - [ ] Add Multiplatform Settings.
  - [ ] Persist non-secret session metadata.
  - [ ] Add secure-token-store expect/actual boundary.
  - [ ] Implement Android secure-token adapter contract.
  - [ ] Implement iOS secure-token adapter contract.

- [ ] Task 2.C3: Implement auth repository and use cases.
  - [ ] Add `AuthRepository`.
  - [ ] Add `Register` use case.
  - [ ] Add `SignIn` use case.
  - [ ] Add `RestoreSession` use case.
  - [ ] Add `RefreshSession` use case.
  - [ ] Add `SignOut` use case.

- [ ] Task 2.C4: Verify refresh concurrency and failure handling.
  - [ ] Serialize concurrent refresh attempts behind one operation.
  - [ ] Add repository tests with fake remote and token stores.
  - [ ] Add tests for refresh failure clearing local credentials.

### Android Client

- [ ] Task 2.A1: Implement secure Android credentials.
  - [ ] Add Android Keystore-backed token storage.

- [ ] Task 2.A2: Build Android authentication UI.
  - [ ] Add sign-in Compose screen.
  - [ ] Add registration Compose screen.
  - [ ] Add field validation messages.
  - [ ] Add submitting state.
  - [ ] Add recoverable API error state.
  - [ ] Add session-restore launch state.

- [ ] Task 2.A3: Verify Android authentication UI.
  - [ ] Add Compose tests for successful sign-in action emission.
  - [ ] Add Compose tests for validation errors.

### iOS Client

- [ ] Task 2.I1: Implement the iOS auth bridge.
  - [ ] Add Keychain-backed token storage.
  - [ ] Add Swift wrapper for shared auth state and actions.

- [ ] Task 2.I2: Build iOS authentication UI.
  - [ ] Add SwiftUI sign-in screen.
  - [ ] Add SwiftUI registration screen.
  - [ ] Add field validation messages.
  - [ ] Add submitting state.
  - [ ] Add recoverable API error state.
  - [ ] Add session-restore launch state.

- [ ] Task 2.I3: Verify iOS authentication UI.
  - [ ] Add XCTest coverage for the auth bridge.
  - [ ] Add UI tests for sign-in validation.

### Exit Criteria

- [ ] Task 2.E1: Complete Phase 2 exit criteria.
  - [ ] A new account can register from Android and iOS.
  - [ ] Both clients can restore and refresh a session after relaunch.
  - [ ] Signing out revokes the server session and clears local credentials.
  - [ ] Auth tests cover invalid input, duplicate account, invalid credentials, expiry, rotation, and revocation.

## Phase 3: Deliver Room Discovery and Room Lifecycle

### Shared/Foundation

- [ ] Task 3.F1: Define room API contracts.
  - [ ] Add create-room request contract.
  - [ ] Add room-details response contract.
  - [ ] Add active-room list contract.
  - [ ] Add join-room response contract.

- [ ] Task 3.F2: Define room transition rules.
  - [ ] Add room lifecycle transition rules.
  - [ ] Add room role transition rules.
  - [ ] Add tests for allowed transitions.
  - [ ] Add tests for rejected transitions.

### Server

- [ ] Task 3.S1: Implement room persistence.
  - [ ] Implement room repository `create`.
  - [ ] Implement room repository `findActive` with cursor pagination.
  - [ ] Implement room repository `findById`.
  - [ ] Implement participant repository `join` idempotently.
  - [ ] Implement participant repository `leave` idempotently.

- [ ] Task 3.S2: Implement room application services.
  - [ ] Implement room service authorization rules.
  - [ ] Implement create-room service.
  - [ ] Implement list-active-rooms service.
  - [ ] Implement get-room-details service.
  - [ ] Implement join-room service.
  - [ ] Implement leave-room service.
  - [ ] Implement end-room service.

- [ ] Task 3.S3: Expose room routes.
  - [ ] Add `POST /api/v1/rooms`.
  - [ ] Add `GET /api/v1/rooms`.
  - [ ] Add `GET /api/v1/rooms/{roomId}`.
  - [ ] Add `POST /api/v1/rooms/{roomId}/join`.
  - [ ] Add `POST /api/v1/rooms/{roomId}/leave`.
  - [ ] Add `POST /api/v1/rooms/{roomId}/end`.

- [ ] Task 3.S4: Verify and protect room operations.
  - [ ] Add route tests for owner and participant permissions.
  - [ ] Add concurrent-join integration test.
  - [ ] Add room-creation rate limit.

### Shared Client

- [ ] Task 3.C1: Establish navigation and lifecycle.
  - [ ] Add Decompose.
  - [ ] Add Essenty lifecycle support.
  - [ ] Define root component navigation configuration.
  - [ ] Define authenticated component navigation configuration.

- [ ] Task 3.C2: Model discovery state.
  - [ ] Add immutable `DiscoveryState`.
  - [ ] Add `DiscoveryAction` sealed interface.
  - [ ] Add pure discovery reducer tests.

- [ ] Task 3.C3: Implement room data and use cases.
  - [ ] Add room remote data source.
  - [ ] Add room repository.
  - [ ] Add `ObserveActiveRooms` use case.
  - [ ] Add `CreateRoom` use case.
  - [ ] Add `LoadRoomDetails` use case.
  - [ ] Add `JoinRoom` use case.
  - [ ] Add `LeaveRoom` use case.

- [ ] Task 3.C4: Implement feature components.
  - [ ] Add `DiscoveryComponent` exposing `StateFlow`.
  - [ ] Add `RoomComponent` exposing `StateFlow`.

- [ ] Task 3.C5: Verify discovery behavior.
  - [ ] Add loading, empty, content, and error state tests.
  - [ ] Add cursor pagination tests.

### Android Client

- [ ] Task 3.A1: Connect the Android application shell.
  - [ ] Connect `MainActivity` to the root component.
  - [ ] Add Material 3 theme tokens.

- [ ] Task 3.A2: Build Android discovery and room flows.
  - [ ] Add discovery screen.
  - [ ] Add room list item.
  - [ ] Add pull-to-refresh behavior.
  - [ ] Add pagination trigger.
  - [ ] Add create-room form.
  - [ ] Add room details screen.
  - [ ] Add join and leave actions.

- [ ] Task 3.A3: Verify Android room navigation.
  - [ ] Add Compose tests for all discovery states.
  - [ ] Add navigation test from discovery to room.

### iOS Client

- [ ] Task 3.I1: Bridge shared navigation and state.
  - [ ] Add Swift wrapper for root component navigation.
  - [ ] Add Swift wrapper for discovery state and actions.

- [ ] Task 3.I2: Build iOS discovery and room flows.
  - [ ] Add SwiftUI discovery screen.
  - [ ] Add room list row.
  - [ ] Add refresh behavior.
  - [ ] Add pagination trigger.
  - [ ] Add create-room form.
  - [ ] Add room details screen.
  - [ ] Add join and leave actions.

- [ ] Task 3.I3: Verify iOS room navigation.
  - [ ] Add XCTest coverage for state conversion.
  - [ ] Add UI navigation test from discovery to room.

### Exit Criteria

- [ ] Task 3.E1: Complete Phase 3 exit criteria.
  - [ ] An authenticated user can create, list, inspect, join, leave, and end a room on both clients.
  - [ ] Room authorization is enforced server-side.
  - [ ] Discovery handles empty, retry, refresh, and pagination states.

## Phase 4: Add Realtime Product State and Local Cache

### Shared/Foundation

- [ ] Task 4.F1: Define the realtime envelope.
  - [ ] Add event envelope with event ID, room ID, sequence, type, and timestamp.

- [ ] Task 4.F2: Define room realtime payloads.
  - [ ] Add participant-joined event.
  - [ ] Add participant-left event.
  - [ ] Add hand-raised event.
  - [ ] Add hand-lowered event.
  - [ ] Add reaction event.
  - [ ] Add role-changed event.
  - [ ] Add speaking-state event.
  - [ ] Add room-ended event.
  - [ ] Add room snapshot contract.

- [ ] Task 4.F3: Verify realtime contracts and reduction.
  - [ ] Add event serialization tests.
  - [ ] Add room-event reducer tests including duplicate events.

### Server

- [ ] Task 4.S1: Establish the WebSocket gateway.
  - [ ] Add Ktor WebSockets plugin.
  - [ ] Authenticate WebSocket upgrade requests.
  - [ ] Reject subscription without room membership.

- [ ] Task 4.S2: Synchronize room state.
  - [ ] Add per-room connection registry.
  - [ ] Add monotonic room sequence allocation.
  - [ ] Send a room snapshot after subscription.
  - [ ] Broadcast participant changes.
  - [ ] Broadcast hand-raise changes.
  - [ ] Broadcast reactions.
  - [ ] Broadcast role changes.
  - [ ] Broadcast room-ended event.

- [ ] Task 4.S3: Implement reconnect and verify gateway behavior.
  - [ ] Add heartbeat and idle timeout policy.
  - [ ] Add reconnect-from-sequence handling.
  - [ ] Fall back to a fresh snapshot when replay is unavailable.
  - [ ] Add WebSocket integration tests for authorization.
  - [ ] Add WebSocket integration tests for ordering.
  - [ ] Add WebSocket integration tests for reconnect.

- [ ] Task 4.S4: Persist ephemeral presence in Redis.
  - [ ] Add Redis and Lettuce dependencies.
  - [ ] Add Redis connection factory.
  - [ ] Store ephemeral room presence with TTL.
  - [ ] Remove stale presence on disconnect or expiry.
  - [ ] Add Redis Testcontainer tests.

### Shared Client

- [ ] Task 4.C1: Establish the realtime connection.
  - [ ] Add WebSocket connection state model.
  - [ ] Add reconnect backoff policy with jitter.
  - [ ] Add authenticated WebSocket connector.

- [ ] Task 4.C2: Process and reconcile room events.
  - [ ] Decode event envelopes by type.
  - [ ] Ignore duplicate event IDs.
  - [ ] Detect sequence gaps.
  - [ ] Request reconciliation after a sequence gap.
  - [ ] Reduce events into immutable room state.
  - [ ] Expose connection quality state to feature components.

- [ ] Task 4.C3: Establish SQLDelight persistence.
  - [ ] Add SQLDelight plugin and runtime.
  - [ ] Add Android SQLDelight driver.
  - [ ] Add iOS SQLDelight native driver.
  - [ ] Create recent-room schema.
  - [ ] Create current-profile cache schema.
  - [ ] Add cache migrations test.

- [ ] Task 4.C4: Integrate cache and verify recovery.
  - [ ] Cache successful discovery results.
  - [ ] Render cached discovery while refreshing.
  - [ ] Add reconnect and gap-reconciliation tests.

### Android Client

- [ ] Task 4.A1: Render Android realtime state.
  - [ ] Render live participant changes.
  - [ ] Add request-to-speak action.
  - [ ] Add reaction picker.
  - [ ] Add connection-state indicator.
  - [ ] Add offline cached-discovery state.

- [ ] Task 4.A2: Verify Android reconnect recovery.
  - [ ] Test UI recovery after reconnect.

### iOS Client

- [ ] Task 4.I1: Bridge realtime state to Swift.
  - [ ] Bridge realtime room `StateFlow` to Swift observation.

- [ ] Task 4.I2: Render iOS realtime state.
  - [ ] Render live participant changes.
  - [ ] Add request-to-speak action.
  - [ ] Add reaction picker.
  - [ ] Add connection-state indicator.
  - [ ] Add offline cached-discovery state.

- [ ] Task 4.I3: Verify iOS lifecycle cancellation.
  - [ ] Test bridge cancellation when a view disappears.

### Exit Criteria

- [ ] Task 4.E1: Complete Phase 4 exit criteria.
  - [ ] Two clients observe ordered participant and room events.
  - [ ] Reconnect either resumes events or applies a fresh snapshot.
  - [ ] Cached discovery is usable during a temporary network failure.
  - [ ] Redis loss degrades presence but does not corrupt PostgreSQL data.

## Phase 5: Integrate Replaceable Voice Infrastructure with LiveKit

### Shared/Foundation

- [ ] Task 5.F1: Define provider-neutral media contracts.
  - [ ] Add provider-neutral media credential request contract.
  - [ ] Add provider-neutral media credential response with URL, credential, and expiry.

- [ ] Task 5.F2: Define shared voice state.
  - [ ] Add shared voice session state model.
  - [ ] Add shared microphone state model.
  - [ ] Add shared connection quality model.

- [ ] Task 5.F3: Enforce and verify provider neutrality.
  - [ ] Keep provider names and SDK types out of public contracts.
  - [ ] Add voice state transition tests.

### Server

- [ ] Task 5.S1: Define the server voice provider port.
  - [ ] Define `VoiceTokenProvider` server port.
  - [ ] Define provider-neutral token request input.
  - [ ] Define provider-neutral token result.
  - [ ] Add port contract tests for identity and expiry.

- [ ] Task 5.S2: Implement the LiveKit token adapter.
  - [ ] Implement `LiveKitVoiceTokenProvider` adapter.
  - [ ] Add LiveKit server token dependency only to the adapter package.
  - [ ] Add LiveKit URL, key, and secret configuration only to adapter configuration.
  - [ ] Prevent provider credentials from appearing in logs.
  - [ ] Map Hearcho room roles to LiveKit grants inside the adapter.
  - [ ] Issue short-lived participant credentials through `VoiceTokenProvider`.

- [ ] Task 5.S3: Authorize and expose media credentials.
  - [ ] Reject token requests for non-members.
  - [ ] Reject publishing grants for listeners.
  - [ ] Add provider-neutral `POST /api/v1/rooms/{roomId}/media-credentials`.
  - [ ] Add token claim tests.
  - [ ] Add token-expiry tests.
  - [ ] Add route authorization tests.

- [ ] Task 5.S4: Adapt provider webhooks.
  - [ ] Define provider-neutral voice webhook port.
  - [ ] Implement LiveKit webhook signature verification in its adapter.
  - [ ] Map LiveKit webhook payloads to provider-neutral events.
  - [ ] Reconcile provider disconnect events without treating them as product-state authority.

- [ ] Task 5.S5: Verify provider replaceability.
  - [ ] Add a fake `VoiceTokenProvider` server test double.
  - [ ] Prove room services pass with the fake provider and no LiveKit runtime.

### Shared Client

- [ ] Task 5.C1: Define the client voice provider port.
  - [ ] Add provider-neutral `VoiceEngine` interface.

- [ ] Task 5.C2: Implement shared voice orchestration.
  - [ ] Add `JoinVoiceSession` use case.
  - [ ] Add `LeaveVoiceSession` use case.
  - [ ] Add `SetMicrophoneEnabled` use case.
  - [ ] Add native voice event input contract.
  - [ ] Merge native voice state into `RoomComponent` state.

- [ ] Task 5.C3: Verify voice lifecycle behavior.
  - [ ] Add fake voice engine contract tests.
  - [ ] Add token-refresh-before-expiry behavior.
  - [ ] Add cleanup-on-component-destroy tests.

### Android Client

- [ ] Task 5.A1: Install LiveKit and request microphone access.
  - [ ] Add LiveKit Android SDK.
  - [ ] Add microphone permission declaration.
  - [ ] Add runtime microphone permission flow.

- [ ] Task 5.A2: Implement the Android LiveKit adapter.
  - [ ] Implement `LiveKitVoiceEngine` Android adapter.
  - [ ] Keep LiveKit types inside the Android adapter package.
  - [ ] Bind `VoiceEngine` to the LiveKit adapter in Android Koin wiring.

- [ ] Task 5.A3: Integrate Android audio and room UI.
  - [ ] Configure `AudioManager` communication mode.
  - [ ] Request and abandon audio focus correctly.
  - [ ] Render mute/unmute control.
  - [ ] Render speaking indicators.
  - [ ] Render network quality indicator.

- [ ] Task 5.A4: Handle Android routes and lifecycle.
  - [ ] Handle Bluetooth and wired route changes.
  - [ ] Handle process foreground/background transitions.
  - [ ] Add foreground service only if active-room behavior requires it.

- [ ] Task 5.A5: Verify Android voice cleanup.
  - [ ] Add device test for permission denial.
  - [ ] Add device test for leave-room cleanup.

### iOS Client

- [ ] Task 5.I1: Install LiveKit and request microphone access.
  - [ ] Add LiveKit Swift SDK through Swift Package Manager.
  - [ ] Add microphone privacy string.
  - [ ] Add microphone permission flow.

- [ ] Task 5.I2: Implement the iOS LiveKit adapter.
  - [ ] Implement `LiveKitVoiceEngine` Swift adapter.
  - [ ] Keep LiveKit types inside the iOS adapter target.
  - [ ] Inject the adapter into the shared feature bridge at iOS startup.

- [ ] Task 5.I3: Integrate iOS audio and room UI.
  - [ ] Configure `AVAudioSession` for voice chat.
  - [ ] Restore the prior audio session on leave.
  - [ ] Render mute/unmute control.
  - [ ] Render speaking indicators.
  - [ ] Render network quality indicator.

- [ ] Task 5.I4: Handle iOS routes and lifecycle.
  - [ ] Handle route changes.
  - [ ] Handle interruptions and media-services reset.
  - [ ] Review background behavior against product requirements.

- [ ] Task 5.I5: Verify iOS voice cleanup.
  - [ ] Add device test for permission denial.
  - [ ] Add XCTest for leave-room cleanup.

### Exit Criteria

- [ ] Task 5.E1: Complete Phase 5 exit criteria.
  - [ ] Android and iOS users can join the same voice room.
  - [ ] Listener and speaker publish permissions match server roles.
  - [ ] Mute state, speaking state, route changes, and interruptions behave correctly.
  - [ ] Tokens are short-lived and issued only to authorized participants.
  - [ ] Shared client and Ktor room-service tests pass with fake voice providers and no LiveKit runtime.
  - [ ] No LiveKit type is exposed by `core`, shared feature APIs, or public server contracts.

## Phase 6: Add Moderation, Reliable Jobs, and Notifications

### Shared/Foundation

- [ ] Task 6.F1: Define moderation contracts.
  - [ ] Add moderation command contracts.
  - [ ] Add report-user contract.
  - [ ] Add report-room contract.
  - [ ] Add block-user contract.

- [ ] Task 6.F2: Define reliable job contracts.
  - [ ] Add notification job contract.
  - [ ] Add idempotency key to asynchronous job contracts.

- [ ] Task 6.F3: Verify safety and job contracts.
  - [ ] Add contract tests.

### Server

- [ ] Task 6.S1: Implement moderator commands.
  - [ ] Add moderator role assignment rules.
  - [ ] Add mute-participant command.
  - [ ] Add remove-participant command.
  - [ ] Add promote-speaker command.
  - [ ] Add revoke-speaker command.

- [ ] Task 6.S2: Persist and enforce safety decisions.
  - [ ] Add report persistence migration.
  - [ ] Add block persistence migration.
  - [ ] Enforce blocks during room listing and joining.
  - [ ] Write moderation actions to an audit table.
  - [ ] Add route and authorization tests for each moderation action.

- [ ] Task 6.S3: Implement reliable RabbitMQ delivery.
  - [ ] Add RabbitMQ Java client.
  - [ ] Add broker configuration and connection factory.
  - [ ] Add outbox repository claim operation.
  - [ ] Add outbox publisher loop.
  - [ ] Mark published events atomically.
  - [ ] Add exponential retry with bounded attempts.
  - [ ] Add dead-letter exchange and queue.

- [ ] Task 6.S4: Implement notification consumers.
  - [ ] Add idempotent notification consumer.
  - [ ] Add FCM sender adapter.
  - [ ] Add APNs sender adapter.

- [ ] Task 6.S5: Verify asynchronous delivery.
  - [ ] Add integration test from database outbox to consumer.
  - [ ] Add failure test proving retry does not duplicate delivery effects.

### Shared Client

- [ ] Task 6.C1: Implement moderation operations.
  - [ ] Add moderation repository.
  - [ ] Add report-user use case.
  - [ ] Add report-room use case.
  - [ ] Add block-user use case.
  - [ ] Add moderator action use cases.

- [ ] Task 6.C2: Reconcile moderation state.
  - [ ] Reduce moderation events into room state.
  - [ ] Add optimistic-state rollback tests.

- [ ] Task 6.C3: Persist notification preferences.
  - [ ] Add notification preference model.
  - [ ] Persist notification preferences with Multiplatform Settings.

### Android Client

- [ ] Task 6.A1: Build Android safety flows.
  - [ ] Add moderator action menu.
  - [ ] Add report flow.
  - [ ] Add block confirmation flow.
  - [ ] Remove blocked content from active views.

- [ ] Task 6.A2: Integrate Android notifications.
  - [ ] Add FCM dependency and service.
  - [ ] Register and rotate push token.
  - [ ] Add Android 13+ notification permission flow.
  - [ ] Route room notifications into the correct screen.

- [ ] Task 6.A3: Verify Android role-based UI.
  - [ ] Add UI tests for authorization-hidden moderator actions.

### iOS Client

- [ ] Task 6.I1: Build iOS safety flows.
  - [ ] Add moderator action menu.
  - [ ] Add report flow.
  - [ ] Add block confirmation flow.
  - [ ] Remove blocked content from active views.

- [ ] Task 6.I2: Integrate iOS notifications.
  - [ ] Register for APNs.
  - [ ] Register and rotate push token.
  - [ ] Add notification permission flow.
  - [ ] Route room notifications into the correct screen.
  - [ ] Keep PushKit excluded unless a new ADR approves it.

- [ ] Task 6.I3: Verify iOS role-based UI.
  - [ ] Add UI tests for authorization-hidden moderator actions.

### Exit Criteria

- [ ] Task 6.E1: Complete Phase 6 exit criteria.
  - [ ] Moderator actions are authorized, audited, and reflected in realtime state.
  - [ ] Blocking is enforced by the server, not only hidden by clients.
  - [ ] Notification jobs survive transient broker and provider failures.
  - [ ] Consumers are idempotent under redelivery.

## Phase 7: Harden Observability, Security, and Resilience

### Shared/Foundation

- [ ] Task 7.F1: Define diagnostic contracts.
  - [ ] Define stable correlation-ID contract.
  - [ ] Define client diagnostic event names without personal data.

- [ ] Task 7.F2: Define security and retention governance.
  - [ ] Document data retention classes.
  - [ ] Complete a threat model for auth, rooms, WebSockets, voice tokens, and moderation.

### Server

- [ ] Task 7.S1: Implement secure structured logging.
  - [ ] Add structured JSON logging.
  - [ ] Add request correlation IDs.
  - [ ] Redact authorization headers and secrets.

- [ ] Task 7.S2: Instrument distributed traces.
  - [ ] Add OpenTelemetry SDK and Ktor instrumentation.
  - [ ] Add spans for auth, room commands, WebSocket sessions, outbox publishing, and token issuance.

- [ ] Task 7.S3: Expose operational metrics.
  - [ ] Add HTTP latency and error metrics.
  - [ ] Add active WebSocket gauge.
  - [ ] Add outbox age and retry metrics.

- [ ] Task 7.S4: Implement dependency readiness.
  - [ ] Add Redis and RabbitMQ health indicators.
  - [ ] Add `/health/ready` with dependency checks.

- [ ] Task 7.S5: Harden server runtime behavior.
  - [ ] Add bounded database and external-call timeouts.
  - [ ] Add graceful shutdown for HTTP, WebSockets, publisher, and consumers.
  - [ ] Add per-user realtime event rate limits.
  - [ ] Add security headers.
  - [ ] Run dependency vulnerability scanning in CI.

- [ ] Task 7.S6: Validate server capacity.
  - [ ] Run load test for room listing.
  - [ ] Run load test for WebSocket fan-out.
  - [ ] Run soak test for outbox publishing.

### Shared Client

- [ ] Task 7.C1: Harden lifecycle and client logging.
  - [ ] Add cancellation ownership to every long-lived component scope.
  - [ ] Add structured client logger abstraction.
  - [ ] Redact tokens and user content from logs.

- [ ] Task 7.C2: Implement resilient request behavior.
  - [ ] Add request correlation ID propagation.
  - [ ] Add bounded retry policies by operation type.
  - [ ] Add offline/online transition handling.

- [ ] Task 7.C3: Verify cancellation and failure recovery.
  - [ ] Add tests for cancellation during network requests.
  - [ ] Add tests for repeated reconnect failures.
  - [ ] Add tests for token expiry during an active room.

### Android Client

- [ ] Task 7.A1: Measure Android startup performance.
  - [ ] Add baseline profile or startup performance measurement.

- [ ] Task 7.A2: Verify Android lifecycle and audio resilience.
  - [ ] Test process recreation during discovery.
  - [ ] Test process recreation during active room state.
  - [ ] Test audio focus loss from another app.

- [ ] Task 7.A3: Audit Android accessibility and log safety.
  - [ ] Run accessibility scanner on auth, discovery, and room screens.
  - [ ] Verify TalkBack labels for voice controls.
  - [ ] Verify no secrets appear in Logcat.

### iOS Client

- [ ] Task 7.I1: Measure iOS runtime performance.
  - [ ] Measure cold launch and room-screen rendering.

- [ ] Task 7.I2: Verify iOS lifecycle and audio resilience.
  - [ ] Test Swift bridge cancellation and deallocation.
  - [ ] Test app background/foreground transitions.
  - [ ] Test phone-call and Siri audio interruptions.

- [ ] Task 7.I3: Audit iOS accessibility and log safety.
  - [ ] Run VoiceOver audit on auth, discovery, and room screens.
  - [ ] Verify Dynamic Type at accessibility sizes.
  - [ ] Verify no secrets appear in unified logs.

### Exit Criteria

- [ ] Task 7.E1: Complete Phase 7 exit criteria.
  - [ ] Critical requests and room sessions are traceable end to end.
  - [ ] Graceful shutdown does not lose claimed outbox work.
  - [ ] Resilience tests cover network loss, dependency loss, process lifecycle, and audio interruption.
  - [ ] Threat-model findings rated high or critical are resolved.

## Phase 8: Prepare Releases and Controlled Launch

### Shared/Foundation

- [ ] Task 8.F1: Freeze and govern v1 contracts.
  - [ ] Freeze v1 API and realtime event schemas.
  - [ ] Document compatibility policy.
  - [ ] Add schema compatibility checks to CI.

- [ ] Task 8.F2: Prepare privacy and operational runbooks.
  - [ ] Complete privacy-policy data inventory.
  - [ ] Complete incident-response runbook.
  - [ ] Complete moderation operations runbook.

### Server

- [ ] Task 8.S1: Build a hardened server image.
  - [ ] Build a reproducible server container image.
  - [ ] Run container as a non-root user.

- [ ] Task 8.S2: Configure production migrations and secrets.
  - [ ] Add production Flyway migration job.
  - [ ] Add secret-manager integration.

- [ ] Task 8.S3: Prepare production data services.
  - [ ] Configure production PostgreSQL backups.
  - [ ] Test database restore procedure.
  - [ ] Configure Redis persistence expectations explicitly.
  - [ ] Configure RabbitMQ durable queues and policies.

- [ ] Task 8.S4: Configure the production voice provider.
  - [ ] Configure LiveKit production project and webhook endpoint.

- [ ] Task 8.S5: Prepare staged deployment and rollback.
  - [ ] Add staged rollout flags.
  - [ ] Add rollback procedure.
  - [ ] Run production-like smoke test.

### Shared Client

- [ ] Task 8.C1: Implement resilient feature flags.
  - [ ] Add remote feature-flag repository.
  - [ ] Cache last-known feature flags.

- [ ] Task 8.C2: Preserve client compatibility.
  - [ ] Add backward-compatible handling for unknown event types.
  - [ ] Add migration tests from the oldest supported local schema.

- [ ] Task 8.C3: Remove release-only hazards.
  - [ ] Remove debug endpoints and test credentials from release builds.

### Android Client

- [ ] Task 8.A1: Prepare the Android release artifact.
  - [ ] Configure release signing outside the repository.
  - [ ] Enable release shrinking after rules are verified.
  - [ ] Generate signed Android App Bundle.

- [ ] Task 8.A2: Complete Android privacy declarations.
  - [ ] Complete Play Store data safety form.
  - [ ] Complete microphone, notification, and location permission disclosures.

- [ ] Task 8.A3: Validate and stage Android release.
  - [ ] Run pre-launch report.
  - [ ] Publish internal test build.
  - [ ] Promote through closed testing after acceptance checks.

### iOS Client

- [ ] Task 8.I1: Prepare the iOS release artifact.
  - [ ] Configure App Store signing and capabilities.
  - [ ] Archive a release build.

- [ ] Task 8.I2: Complete iOS privacy declarations.
  - [ ] Complete App Privacy details.
  - [ ] Complete microphone, notification, and location purpose descriptions.
  - [ ] Validate third-party SDK privacy manifests.

- [ ] Task 8.I3: Validate and stage iOS release.
  - [ ] Upload TestFlight build.
  - [ ] Complete internal TestFlight acceptance checks.
  - [ ] Submit phased-release build.

### Exit Criteria

- [ ] Task 8.E1: Complete Phase 8 exit criteria.
  - [ ] Production deployment, rollback, backup, and restore procedures are rehearsed.
  - [ ] Android and iOS release candidates pass acceptance, accessibility, privacy, and network-resilience checks.
  - [ ] Monitoring and moderation ownership is assigned for launch.
  - [ ] Launch can be disabled by feature flag without shipping new clients.
