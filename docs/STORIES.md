# Epics & User Stories

## EPIC-01 Voice Recap (M1)
**US-01** As a golfer, I can tap a mic button and speak a recap so the app captures my hole quickly.  
**Acceptance:**
- One tap starts listening; transcript appears within 3s
- Error states: permission denied, no network (still save raw audio? No for MVP)

**US-02** As a golfer, the app parses clubs/putts/score from my recap.  
**Acceptance:**
- Recognize clubs: D, 3W, 5W, 3–9i, PW, GW, SW, LW, putter
- Understand: “two-putt”, “3-putt”, “tap-in”
- Scores: par, bogey, double, birdie, eagle
- Examples pass → see `/docs/TEST_UTTERANCES.md`

## EPIC-02 GPS & Course (M2)
**US-03** App stores a GPS point per shot group on the hole.  
**Acceptance:**
- Stores lat/lng at recap time (and optionally one pin per mentioned shot)

**US-04** Minimal course setup.  
**Acceptance:**
- Quick Round mode (no DB) OR manual tee/green pin entry
- Back-nine start supported

## EPIC-03 Stats (M3)
**US-05** See score, FIR, GIR, and putts per hole + totals.  
**Acceptance:**
- FIR logic: par-4/5 tee shot landing in fairway
- GIR logic: on green with ≥2 strokes left

## EPIC-04 Privacy & Perf (M4)
**US-06** Clear permission prompts & privacy toggle.  
**Acceptance:**
- Microphone/location prompts with explanatory copy
- No continuous audio storage; transcripts saved locally

**US-07** Battery budget.  
**Acceptance:**
- Log battery % at hole 1/9/18
- GPS at 1 Hz while moving; lower when idle
