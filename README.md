# Voice Caddie MVP (Android, watchless)

**Goal:** Play golf normally. After each hole, speak a short recap (e.g., “Driver, 7-iron, two-putt par”). App parses, pins shots with GPS, and builds your scorecard/stats. No watch or sensors required.

## Core MVP (scope)
- Start round, choose course (manual or quick-setup)
- Voice recap per hole (press-to-talk)
- Parse clubs/putts/score from speech
- GPS pin for shots
- Round summary: score, FIR/GIR, putts

## Non-goals (for now)
- Auto shot detection, group mode, iOS, watch

## Build
Android Studio | Kotlin + Jetpack Compose

## Folders
- `/app` – Android UI & platform adapters
- `/core` – Platform-agnostic logic (RoundEngine, NLU, schema)
- `/docs` – Product specs, roadmap

See `/docs/ARCHITECTURE.md` and `/docs/ROADMAP.md`.
