# ADR 0001 - Use Kotlin Multiplatform for Shared Business Logic

Status: Accepted
Date: 2026-06-19

## Context

Hearcho targets Android, iOS, and backend-adjacent shared contracts. The product requires consistent domain rules for room state, participants, permissions, realtime events, validation, and API contracts. Duplicating this behavior independently in Android, iOS, and backend code would increase drift.

## Decision

Use Kotlin Multiplatform for platform-independent business logic and shared contracts. Keep the stable shared primitives in `core`, and keep shared client behavior in `app/sharedLogic`.

## Consequences

- Domain rules can be tested once and reused across clients.
- API/event contracts can remain consistent across clients and server.
- Platform-specific APIs must be isolated behind `expect`/`actual` declarations or native app code.
- Contributors must be careful not to leak Android, iOS, or server implementation details into `commonMain`.

## Alternatives Considered

- Fully native clients with duplicated logic: simpler platform builds, but high drift risk.
- Shared TypeScript contracts: useful for web-heavy stacks, but this project is Kotlin-first.
- Generated OpenAPI-only contracts: helpful for transport shape, but insufficient for client-side domain state machines.
