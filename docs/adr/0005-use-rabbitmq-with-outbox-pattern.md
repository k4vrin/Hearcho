# ADR 0005 - Use RabbitMQ with the Outbox Pattern for Asynchronous Side Effects

Status: Accepted
Date: 2026-06-19

## Context

Hearcho needs reliable asynchronous work for push notification dispatch, future email delivery, background jobs, retry handling, and event fan-out. These side effects should not be lost if the backend crashes after writing domain data.

## Decision

Use RabbitMQ for asynchronous task queues and fan-out. Use the outbox pattern for durable side effects: write domain changes and outbox events in the same database transaction, then publish pending outbox records to RabbitMQ from a background publisher.

## Consequences

- Side effects become more reliable under process failure.
- RabbitMQ fits task queues and retry workflows well.
- The backend needs outbox storage, publisher jobs, retry policies, and idempotent consumers.
- RabbitMQ is not treated as an event-sourcing log or analytics stream.

## Alternatives Considered

- Direct publish inside request transaction: simpler, but can lose events or publish events for rolled-back work.
- Kafka: useful for high-throughput event streams and replay, but heavier than needed for MVP task queues.
- Database polling only: simpler initially, but less flexible for worker fan-out and retry routing.
