# ADR 0010 - Adopt Arrow for Typed Outcomes, Validation, Optics, and Parallelism

Status: Accepted
Date: 2026-06-19

Supersedes: The Arrow exclusion in [ADR 0008](./0008-finalize-client-and-server-application-stack.md)

## Context

Hearcho needs explicit typed failures across shared Kotlin and selected Ktor application services, validation that can guarantee and accumulate one or more failures, maintainable updates to deeply nested immutable state, and structured parallel execution for independent suspend operations.

## Decision

Adopt Arrow with a constrained scope:

- Use `Either<ProjectFailure, A>` for explicit success/failure values at domain and application boundaries.
- Use the Raise DSL to compose typed-failure workflows without nested `flatMap` chains.
- Use `NonEmptyList<ValidationFailure>` when validation must return at least one failure and can accumulate independent failures.
- Use Arrow Optics selectively for immutable models with repeated nested updates; do not annotate every data class.
- Use Arrow Fx Coroutines `parZip` only for independent suspend operations, preserving structured-concurrency cancellation.
- Keep failure hierarchies owned by Hearcho. Arrow provides representation and composition, not product semantics.

Do not expose Arrow through HTTP/WebSocket wire formats, SwiftUI/Compose UI state, or voice-provider SDK boundaries.

## Consequences

- Expected failures become explicit and composable across KMP and Ktor code.
- Validation can accumulate deterministic, non-empty failure collections.
- Optics code generation adds build configuration and generated code.
- `parZip` can reduce latency for independent work but requires cancellation and failure-order tests.
- Contributors must avoid mixing exceptions, nullable failures, and typed failures inconsistently at the same boundary.
- Arrow upgrades must be validated against Kotlin, Android, iOS, JVM, and the Optics plugin.

## Alternatives Considered

- Project-owned result containers: fewer dependencies, but duplicate established typed-composition behavior.
- Exceptions for expected failures: rejected because expected domain failures should remain visible in function signatures.
- Manual `copy` chains: acceptable for shallow models, but noisy for repeatedly updated nested state.
- Raw `async`/`await`: remains valid for dynamic concurrency; `parZip` is preferred only for a fixed set of independent operations.
