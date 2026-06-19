# ADR 0008 - Finalize the Client and Server Application Stack

Status: Accepted
Date: 2026-06-19

Partially superseded by: [ADR 0010](./0010-adopt-arrow-for-typed-outcomes-validation-optics-and-parallelism.md) for the Arrow exclusion only.

## Context

The initial technology document listed multiple overlapping client abstractions and left server persistence unresolved. Implementation needs one bounded baseline with a clear dependency adoption order.

## Decision

Use Decompose and Essenty for shared client navigation/lifecycle, Koin for composition, and immutable state with pure reducers exposed through `StateFlow`. Use Ktor Client, kotlinx.serialization, SQLDelight, and Multiplatform Settings for client data concerns.

Use PostgreSQL with Exposed DSL, HikariCP, and Flyway on the server. Use Lettuce for Redis and the RabbitMQ Java client with a transactional outbox. Use Ktor JWT authentication, Argon2id password hashing, OpenTelemetry, and Testcontainers.

Do not adopt Orbit MVI in the baseline. The original Arrow exclusion in this decision is superseded by ADR 0010, which approves a bounded set of Arrow patterns.

## Consequences

- Each architectural concern has one primary owner.
- The team maintains project-owned state and error models.
- Dependencies are introduced with their first production use case, not as empty infrastructure.
- Server persistence and migration work can proceed without another technology-selection phase.

## Alternatives Considered

- Orbit MVI plus Decompose: rejected because it introduces a second state/container model before feature complexity requires it.
- Arrow for all errors: originally rejected because sealed domain errors are sufficient for product semantics; ADR 0010 later approves scoped Arrow composition around those project-owned errors.
- SQLDelight on the server: rejected in favor of Exposed's JVM-focused SQL DSL and mature server transaction model.
- Raw JDBC: rejected because it adds mapping and transaction boilerplate without a demonstrated benefit.
