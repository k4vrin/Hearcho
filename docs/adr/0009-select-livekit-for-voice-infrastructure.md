# ADR 0009 - Select LiveKit for Voice Infrastructure

Status: Accepted
Date: 2026-06-19

Supersedes: [ADR 0006](./0006-defer-sfu-choice.md)

## Context

Hearcho needs a practical SFU path, maintained Android and iOS SDKs, secure server-issued access tokens, and room-scale WebRTC behavior. Continuing to defer the choice blocks native voice proof-of-concepts and signaling design.

## Decision

Use LiveKit cloud-first for the MVP behind Hearcho-owned interfaces.

- Shared client features depend on a provider-neutral `VoiceEngine` and provider-neutral voice state models.
- Android and iOS implement `VoiceEngine` with native LiveKit SDK adapters.
- Ktor room services depend on a `VoiceTokenProvider`; a LiveKit adapter signs provider credentials and maps Hearcho roles to LiveKit grants.
- Provider webhooks enter through a provider-neutral adapter before reaching application services.
- Public API and WebSocket contracts use Hearcho terminology and never expose LiveKit SDK types, grant objects, room objects, or event names.

The Ktor server owns authorization and issues short-lived media credentials. Ktor WebSockets remain the transport for product state and moderation events.

Keep self-hosted LiveKit as a later operational option; it is not required for MVP completion.

## Consequences

- Android and iOS can validate media behavior with maintained native SDKs.
- Hearcho avoids building and operating a custom SFU for the MVP.
- LiveKit becomes an external runtime dependency and cost center.
- Product room state must not be inferred solely from LiveKit participant state.
- Token issuance, grants, expiry, and revocation policy remain Hearcho responsibilities.
- Replacing LiveKit requires new native media adapters and a server provider adapter, but does not change feature use cases or product contracts.
- Provider-specific capabilities cannot enter domain behavior without first extending the provider-neutral ports and contract tests.

## Alternatives Considered

- mediasoup: offers more control but requires significantly more signaling and operations work.
- Peer-to-peer WebRTC: does not scale to the intended room model.
- Continue deferring: rejected because it prevents an executable voice roadmap.
