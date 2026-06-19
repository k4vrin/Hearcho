# Hearcho Product Requirements Document

## 1. Product Overview

Hearcho is a cross-platform realtime voice communication platform focused on helping people discover and participate in live conversations. It combines low-latency voice rooms, lightweight social features, community tools, and location-aware discovery.

The primary product goal is simple: users should be able to discover, join, and participate in a meaningful voice conversation within seconds.

## 2. Vision

Hearcho exists to make live voice conversations as easy to discover and join as opening a music streaming app. Instead of requiring users to know exactly who they want to talk to, Hearcho introduces people to active conversations, communities, and hosts around shared interests, languages, places, and moments.

Vision statement:

> Create the easiest and fastest way for people to discover, join, and host meaningful live voice conversations from anywhere.

## 3. Product Principles

### Voice First

Voice is the primary interaction model. Text, images, and metadata should support conversations without becoming the main product.

### Join Within Seconds

The path from opening the app to entering a room should be short, obvious, and reliable.

### Discovery Before Following

The product should help users find interesting live rooms even before they have a social graph.

### Community Over Audience

Hearcho should help communities form around recurring conversations, shared identities, and trust, not only one-way broadcasting.

### Lightweight by Design

Users should immediately understand what is happening in a room, who is speaking, and how to participate.

### Reliability Builds Trust

Voice products fail quickly when joining, reconnecting, speaking state, or participant state is unreliable. Reliability is a product requirement.

### Safety by Default

Moderation, reporting, blocking, roles, and abuse prevention are part of the core product, not late-stage extras.

## 4. Target Users

### Casual Listeners

Users who mostly browse and listen. They need fast discovery, clear room previews, and frictionless joining.

### Speakers

Users who participate in conversations. They need clear microphone controls, speaker permissions, connection status, and speaking indicators.

### Hosts

Users who create and moderate rooms. They need fast room creation, role controls, participant management, and moderation tools.

### Communities

Groups that host recurring conversations. They need identity, recurring events, moderation teams, discovery surfaces, and member trust tools.

## 5. Core Concepts

| Concept | Description |
| --- | --- |
| User | Account identity for a person using Hearcho. |
| Profile | Public-facing identity, interests, language, location preferences, and trust signals. |
| Room | Live voice conversation users can join. |
| Stage | Active speaking area inside a room. |
| Listener | Participant who can hear but is not currently speaking. |
| Speaker | Participant with microphone permission. |
| Host | Room owner or primary moderator. |
| Moderator | User with room safety and participant controls. |
| Community | Persistent group around recurring rooms and shared interests. |
| Presence | Realtime online, room, and participant state. |
| Discovery | Ranking and filtering of active rooms. |

## 6. Core Requirements

### Authentication

- Users can sign up and sign in.
- Sessions persist securely.
- Users can recover from expired sessions gracefully.
- Future social login should not reshape domain identity.

### Room Discovery

- Users can view active rooms.
- Users can filter or rank rooms by interest, language, proximity, popularity, and freshness.
- Users can see enough room metadata to decide whether to join.
- Users can discover nearby rooms when location permission is granted.

### Room Participation

- Users can join a room quickly.
- Users can leave a room clearly.
- Users can mute and unmute.
- Users can request to speak.
- Hosts and moderators can approve or revoke speaker access.
- Users can see who is speaking.
- Users can recover from transient network interruptions.

### Hosting

- Users can create a room in under one minute.
- Hosts can define title, topic, language, visibility, and basic room rules.
- Hosts can manage speakers and listeners.
- Hosts can end a room.

### Realtime State

- Participant lists update live.
- Speaking indicators update live.
- Presence updates live.
- Room lifecycle changes update live.
- Reconnect should resync state instead of relying only on missed events.

### Safety and Moderation

- Users can report a room or participant.
- Users can block another user.
- Hosts and moderators can mute, remove, or restrict participants.
- Moderation actions should be auditable.
- Abuse-prone actions should be rate limited.

### Notifications

- Users can receive relevant room, community, and moderation notifications.
- Notification preferences should be user-controllable.
- Push notification behavior must respect platform rules.

## 7. Non-Functional Requirements

### Performance

- App startup should prioritize discovery and active room entry.
- Joining a room should feel immediate.
- Room state updates should remain responsive under normal mobile network conditions.

### Reliability

- Voice connections should handle common mobile network changes.
- Realtime state should recover on reconnect.
- Backend side effects should not be lost during process failure.

### Privacy

- Location must be permission-based and purpose-limited.
- Sensitive tokens must use secure storage.
- Users should control visibility of location-derived discovery signals.

### Accessibility

- Core flows should support screen readers.
- UI controls should have semantic labels.
- Text should support dynamic sizing on iOS and Android accessibility settings.

## 8. MVP Scope

The MVP should prove:

- Users can authenticate.
- Users can discover active rooms.
- Users can create and join a room.
- Users can listen and speak.
- Room presence and speaking state update in realtime.
- Hosts can moderate basic participant behavior.
- The system can recover from reconnects.

## 9. Out of Scope for MVP

- Paid subscriptions.
- Full community management suite.
- Desktop app.
- Advanced recommendation engine.
- Event scheduling marketplace.
- Creator monetization.
- Complex analytics dashboards.

## 10. Success Metrics

- New users can understand the first screen without explanation.
- Users can join a room in under 10 seconds after opening the app.
- Reconnects restore room state without manual refresh.
- Hosts can create a room in under one minute.
- Moderation tools are present before public launch.
- Users return because conversations are meaningful, not because of dark-pattern engagement loops.
