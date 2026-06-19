# ADR 0003 - Keep Voice, Location, Maps, and Push Integrations Native

Status: Accepted
Date: 2026-06-19

## Context

Hearcho depends on platform-sensitive features: realtime voice, microphone permissions, audio routing, background behavior, push notifications, maps, and location. These areas differ substantially between Android and iOS and are closely tied to OS policies.

## Decision

Keep voice media, audio routing, maps, location, push notifications, and permission flows native to each platform. Shared Kotlin models state and defines provider-neutral interfaces. Native modules own OS integration and implement the first `VoiceEngine` adapters with LiveKit; those adapters can later be replaced by mediasoup implementations.

## Consequences

- Platform behavior can follow OS best practices and review rules.
- Audio and background behavior can be tuned per platform.
- Shared Kotlin remains focused on state and business rules.
- More adapter code is required for Android and iOS.

## Alternatives Considered

- Use KMP wrappers for all platform services: appealing for reuse, but risky for audio, push, and permissions.
- Move platform integrations into shared UI: rejected because UI should not own OS service behavior.
