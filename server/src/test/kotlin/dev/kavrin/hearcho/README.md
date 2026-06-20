# Server Test Levels

Use three test levels for server code:

- Use-case unit tests live beside feature `application` code and run pure Kotlin logic with
  hand-written fakes from `testfixture`.
- Adapter integration tests live beside specific adapters and verify route, persistence, or provider
  translation behavior without mocking framework internals.
- Transport contract tests exercise public Ktor endpoints with `testApplication` through bootstrap
  wiring to verify HTTP status codes and payloads.
