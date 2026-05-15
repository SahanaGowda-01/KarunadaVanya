package com.example.karunada_vanya.ui.screens

import android.media.MediaPlayer
import android.media.ToneGenerator
import android.media.AudioManager
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.karunada_vanya.data.model.AudioTrack
import com.example.karunada_vanya.data.repository.AudioRepository
import com.example.karunada_vanya.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun ForestAudioScreen(modifier: Modifier = Modifier) {
    val allTracks = AudioRepository.getAllTracks()
    var playingTrackId by remember { mutableStateOf<String?>(null) }
    var progress by remember { mutableStateOf(0f) }
    var seconds by remember { mutableStateOf(0) }

    // Simulate playback progress
    LaunchedEffect(playingTrackId) {
        if (playingTrackId != null) {
            progress = 0f
            seconds = 0
            while (playingTrackId != null && progress < 1f) {
                delay(300)
                progress += 0.01f
                seconds = (progress * 180).toInt() // simulate up to 3 min
            }
            if (progress >= 1f) playingTrackId = null
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CreamWhite)
            .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)) {
            Text(
                text = "Forest",
                style = MaterialTheme.typography.displayMedium,
                color = TextDark
            )
            Text(
                text = "Symphony",
                style = MaterialTheme.typography.displayMedium.copy(fontStyle = FontStyle.Italic),
                color = TextDark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Immersive high-fidelity field recordings categorized by regional origin.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted
            )
        }

        val regions = listOf("KABINI", "BANDIPUR", "NAGARAHOLE")
        regions.forEach { region ->
            val regionTracks = allTracks.filter { it.region == region }
            if (regionTracks.isNotEmpty()) {
                // Region header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(color = DividerColor, modifier = Modifier.weight(1f))
                    Text(
                        text = "  $region REGION  ",
                        style = MaterialTheme.typography.labelLarge,
                        color = WarmOrange
                    )
                    Divider(color = DividerColor, modifier = Modifier.weight(1f))
                }

                regionTracks.forEach { track ->
                    val isPlaying = playingTrackId == track.id
                    AudioTrackCard(
                        track = track,
                        isPlaying = isPlaying,
                        progress = if (isPlaying) progress else 0f,
                        currentSeconds = if (isPlaying) seconds else 0,
                        onPlayPause = {
                            playingTrackId = if (isPlaying) null else track.id
                        },
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun AudioTrackCard(
    track: AudioTrack,
    isPlaying: Boolean,
    progress: Float,
    currentSeconds: Int,
    onPlayPause: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalSeconds = 180
    val currentFormatted = formatTime(currentSeconds)
    val totalFormatted = formatTime(totalSeconds)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CardWhite)
            .border(1.dp, DividerColor, RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Play icon circle
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(if (isPlaying) WarmOrange else ForestGreen)
                        .clickable(onClick = onPlayPause),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.VolumeUp else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = CardWhite,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = track.animalName,
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                        color = TextDark
                    )
                    Text(
                        text = "${track.type} • ${track.region}",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextMuted
                    )
                }

                if (isPlaying) {
                    Text(
                        text = "$currentFormatted / $totalFormatted",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextLight
                    )
                }
            }

            if (isPlaying) {
                Spacer(modifier = Modifier.height(16.dp))
                // Progress bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(DividerColor)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress.coerceIn(0f, 1f))
                            .fillMaxHeight()
                            .background(WarmOrange)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "LIVE PLAYBACK",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                    Text(
                        text = "PAUSE",
                        style = MaterialTheme.typography.labelSmall,
                        color = WarmOrange,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable(onClick = onPlayPause)
                    )
                }
            }
        }
    }
}

fun formatTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%d:%02d".format(m, s)
}