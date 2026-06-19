# ADR 0006 - Defer SFU Choice Between LiveKit and mediasoup

Status: Superseded by [ADR 0009](./0009-select-livekit-for-voice-infrastructure.md)
Date: 2026-06-19

## Context

Hearcho requires realtime group voice. The architecture needs an SFU, but the project has not yet validated hosted vs. self-managed operations, client SDK fit, recording needs, moderation hooks, scaling model, and deployment cost.

## Decision

Defer the final SFU decision. Compare LiveKit and mediasoup before implementing production voice infrastructure.

## Consequences

- The project avoids committing too early to a voice infrastructure model.
- Early backend and client work should define abstractions around voice session creation, join tokens, and signaling state.
- MVP voice work cannot be completed until this ADR is resolved.

## Alternatives Considered

- Choose LiveKit now: faster path with batteries included, but may hide constraints until later.
- Choose mediasoup now: high control, but more operational and implementation burden.
- Peer-to-peer WebRTC only: simpler for tiny calls, but unsuitable for scalable rooms.
