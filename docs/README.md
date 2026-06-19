# Hearcho Documentation

Hearcho is a Kotlin Multiplatform-first realtime voice product. The project combines shared Kotlin business logic, an Android Compose host, a native SwiftUI host, and a Ktor backend for room discovery, presence, realtime product state, and provider-neutral media authorization.

This documentation set is organized for both human contributors and AI agents. Start here, then move into the document that matches the task.

## Documentation Map

- [PRD](./PRD.md): Product vision, user types, core concepts, and functional requirements.
- [Architecture](./ARCHITECTURE.md): System boundaries, module responsibilities, data flow, and extension rules.
- [Technology Stack](./TECH_STACK.md): Active versions, finalized libraries, exclusions, and dependency adoption order.
- [Project Structure](./PROJECT_STRUCTURE.md): Top-down map of the repository and where new code should go.
- [Project Context](./PROJECT_CONTEXT.md): Compact AI-agent briefing with current state, rules, and next likely work.
- [Roadmap](./ROADMAP.md): Phase and workstream plan organized into named tasks and atomic subtasks.
- [ADRs](./adr/README.md): Architectural decision records and decision history.

## Current Implementation State

The repository currently contains the initial scaffold:

- Standalone Android application consuming an Android-targeted Compose KMP library.
- Native SwiftUI Xcode application consuming the generated `SharedLogic` framework.
- Android/iOS shared client logic plus Android/iOS/JVM shared core contracts.
- Ktor server module depending on shared `core`.
- Version catalog-based Gradle setup.

The product architecture describes the target direction. Not every target module or dependency has been implemented yet.

## Core Product Direction

Hearcho should make live voice rooms fast to discover, join, and host. The product prioritizes:

- Voice-first interaction.
- Nearby and interest-based discovery.
- Low-friction room entry.
- Reliable realtime state synchronization.
- Safety, moderation, and trust from early releases.

## Engineering Principles

- Keep business rules in shared Kotlin when they are platform-independent.
- Keep platform integrations native when they depend on OS behavior, media, permissions, push, maps, or audio routing.
- Keep LiveKit behind provider-neutral client and Ktor server ports so mediasoup can replace it without changing feature contracts.
- Use `StateFlow` and explicit state models for realtime client state.
- Prefer typed domain errors and sealed state where it improves clarity.
- Document irreversible architecture choices with ADRs before large implementation work.
