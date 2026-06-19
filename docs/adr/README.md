# Architecture Decision Records

This directory stores durable architecture decisions for Hearcho.

## ADR Index

- [0001 - Use Kotlin Multiplatform for Shared Business Logic](./0001-use-kotlin-multiplatform-for-shared-business-logic.md)
- [0002 - Use Native UI Shells with Shared Compose UI Where Practical](./0002-use-native-ui-shells-with-shared-compose-ui-where-practical.md)
- [0003 - Keep Voice, Location, Maps, and Push Integrations Native](./0003-keep-platform-integrations-native.md)
- [0004 - Use Ktor for Backend and Realtime Product State](./0004-use-ktor-for-backend-and-realtime-product-state.md)
- [0005 - Use RabbitMQ with the Outbox Pattern for Asynchronous Side Effects](./0005-use-rabbitmq-with-outbox-pattern.md)
- [0006 - Defer SFU Choice Between LiveKit and mediasoup](./0006-defer-sfu-choice.md)
- [0007 - Adopt the Current Split-Host KMP Module Topology](./0007-adopt-current-split-host-kmp-module-topology.md)
- [0008 - Finalize the Client and Server Application Stack](./0008-finalize-client-and-server-application-stack.md)
- [0009 - Select LiveKit for Voice Infrastructure](./0009-select-livekit-for-voice-infrastructure.md)
- [0010 - Adopt Arrow for Typed Outcomes, Validation, Optics, and Parallelism](./0010-adopt-arrow-for-typed-outcomes-validation-optics-and-parallelism.md)

## ADR Template

```text
# ADR NNNN - Title

Status: Proposed | Accepted | Superseded
Date: YYYY-MM-DD

## Context

What forces, constraints, or requirements make this decision necessary?

## Decision

What is the decision?

## Consequences

What becomes easier, harder, constrained, or enabled?

## Alternatives Considered

What alternatives were considered and why were they not chosen?
```
