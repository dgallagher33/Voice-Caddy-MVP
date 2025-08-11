package com.example.voicecaddy.core

data class Transcript(val text: String)

interface AudioInput {
    suspend fun getTranscriptOnce(): Transcript
}
