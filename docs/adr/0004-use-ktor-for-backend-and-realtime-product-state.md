# ADR 0004 - Use Ktor for Backend and Realtime Product State

Status: Accepted
Date: 2026-06-19

## Context

The backend must provide REST APIs, authenticated realtime state, and future voice signaling. The project is Kotlin-first, and the current scaffold already contains a Ktor JVM server.

## Decision

Use Ktor for the backend HTTP API and WebSocket gateway. Use WebSockets for product state such as presence, room events, speaking state, hand raises, reactions, and moderation updates. Voice media remains handled by native WebRTC clients and an SFU.

## Consequences

- Backend and shared contracts can remain Kotlin-aligned.
- Ktor route tests can validate API behavior quickly.
- Ktor WebSockets can support room-level realtime state without introducing a separate Node/Socket.io stack.
- The team must design reconnect and state reconciliation explicitly.

## Alternatives Considered

- Node.js with Socket.io: mature realtime ecosystem, but inconsistent with the Kotlin-first backend.
- Managed realtime service: faster initial setup, but less control over room state, auth, and moderation semantics.
