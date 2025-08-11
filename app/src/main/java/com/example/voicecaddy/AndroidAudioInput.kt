package com.example.voicecaddy

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.core.content.ContextCompat
import com.example.voicecaddy.core.AudioInput
import com.example.voicecaddy.core.Transcript
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

class AndroidAudioInput(val context: Context) : AudioInput {
    private val recognizer = SpeechRecognizer.createSpeechRecognizer(context)

    override suspend fun getTranscriptOnce(): Transcript = suspendCancellableCoroutine { cont ->
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            cont.resumeWithException(SecurityException("RECORD_AUDIO permission not granted"))
            return@suspendCancellableCoroutine
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }

        val listener = object : RecognitionListener {
            override fun onResults(results: Bundle) {
                val text = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull()
                cont.resume(Transcript(text.orEmpty()))
                recognizer.setRecognitionListener(null)
            }

            override fun onError(error: Int) {
                cont.resumeWithException(RuntimeException("Speech recognition error: $error"))
                recognizer.setRecognitionListener(null)
            }

            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        }

        recognizer.setRecognitionListener(listener)
        recognizer.startListening(intent)

        cont.invokeOnCancellation {
            recognizer.cancel()
            recognizer.setRecognitionListener(null)
        }
    }
}
