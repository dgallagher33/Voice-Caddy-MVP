# Architecture

## Modules
- **core** (pure Kotlin, no Android imports)
  - RoundEngine: state machine for round/hole/shot objects
  - NLU: regex-based parser (clubs, putts, score, distances)
  - Schema: JSON model for round persistence
- **app** (Android)
  - AudioInput (SpeechRecognizer wrapper)
  - LocationSource (FusedLocationProvider)
  - Storage (JSON/Room)
  - UI (Compose)

## Key Interfaces (core)
```kotlin
interface AudioInput { suspend fun getTranscriptOnce(): Transcript }
interface LocationSource { fun currentLatLng(): LatLng }
interface Storage {
  suspend fun saveRound(round: Round): Unit
  suspend fun loadRounds(): List<Round>
}
interface RoundEngine {
  fun startRound(course: Course)
  fun addHoleRecap(holeNumber: Int, recap: Recap) // parsed from transcript
  fun toJson(): String
}
