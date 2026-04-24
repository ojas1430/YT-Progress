package com.ojasx.eduplay.ui.Player

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

suspend fun isVideoEmbeddable(videoId: String): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL("https://www.youtube.com/oembed?url=https://www.youtube.com/watch?v=$videoId&format=json")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            val responseCode = connection.responseCode
            connection.disconnect()
            // 200 = embeddable, 401/403 = restricted
            responseCode == 200
        } catch (e: Exception) {
            false // Treat any error as restricted
        }
    }
}

fun openInYouTube(context: Context, videoId: String) {
    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId")).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    val webIntent = Intent(Intent.ACTION_VIEW,
        Uri.parse("https://www.youtube.com/watch?v=$videoId")).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    try {
        context.startActivity(appIntent)
    } catch (e: Exception) {
        context.startActivity(webIntent)
    }
}

@Composable
fun YouTubePlayerScreen(videoId: String) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    // null = still checking, true = embeddable, false = restricted
    var isEmbeddable by remember { mutableStateOf<Boolean?>(null) }

    // Pre-check BEFORE rendering the player
    LaunchedEffect(videoId) {
        val result = isVideoEmbeddable(videoId)
        if (!result) {
            // Restricted — open YouTube immediately, don't load player at all
            openInYouTube(context, videoId)
        }
        isEmbeddable = result
    }

    when (isEmbeddable) {
        null -> {
            // Still checking — show loader
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        false -> {
            // Restricted — already opened YouTube above, show nothing
            Box(modifier = Modifier.fillMaxSize())
        }
        true -> {
            // Safe to play in-app
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    val playerView = YouTubePlayerView(ctx)
                    lifecycleOwner.lifecycle.addObserver(playerView)
                    playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.cueVideo(videoId, 0f)
                        }
                        override fun onError(
                            youTubePlayer: YouTubePlayer,
                            error: PlayerConstants.PlayerError
                        ) {
                            // Safety net in case something slips through
                            openInYouTube(context, videoId)
                        }
                    })
                    playerView
                }
            )
        }
    }
}