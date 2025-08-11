package com.example.voicecaddy

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.voicecaddy.core.Transcript
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var audioInput: AndroidAudioInput

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        audioInput = AndroidAudioInput(this)
        setContent {
            MaterialTheme {
                MicScreen(audioInput)
            }
        }
    }
}

@Composable
fun MicScreen(audioInput: AndroidAudioInput) {
    var transcript by remember { mutableStateOf<Transcript?>(null) }
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(audioInput.context, Manifest.permission.RECORD_AUDIO) ==
                android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        hasPermission = it
    }

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            transcript?.let { Text(it.text) }
            IconButton(onClick = {
                if (hasPermission) {
                    (audioInput.context as ComponentActivity).lifecycleScope.launch {
                        try {
                            transcript = audioInput.getTranscriptOnce()
                        } catch (e: Exception) {
                            transcript = Transcript(e.message ?: "Error")
                        }
                    }
                } else {
                    launcher.launch(Manifest.permission.RECORD_AUDIO)
                }
            }) {
                Icon(Icons.Default.Mic, contentDescription = "Mic")
            }
        }
    }
}
