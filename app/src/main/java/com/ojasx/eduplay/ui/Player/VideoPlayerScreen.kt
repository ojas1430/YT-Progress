package com.ojasx.eduplay.ui.Player

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

// ─── Network State Flow ───────────────────────────────────────────────────────

fun networkStateFlow(context: Context): Flow<Boolean> = callbackFlow {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Emit current state immediately
    trySend(isInternetAvailable(connectivityManager))

    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            trySend(true) // Internet came back
        }
        override fun onLost(network: Network) {
            trySend(false) // Internet lost
        }
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            val hasInternet = networkCapabilities
                .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            trySend(hasInternet)
        }
    }

    val request = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(request, callback)

    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}.distinctUntilChanged() // Only emit when state actually changes

fun isInternetAvailable(connectivityManager: ConnectivityManager): Boolean {
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

// ─── oEmbed Pre-Check ─────────────────────────────────────────────────────────

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
            responseCode == 200
        } catch (e: Exception) {
            false
        }
    }
}

// ─── Open in YouTube ─────────────────────────────────────────────────────────

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

// ─── Main Composable ──────────────────────────────────────────────────────────

@Composable
fun YouTubePlayerScreen(videoId: String) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    // Track internet state
    val isOnline by networkStateFlow(context)
        .collectAsState(initial = false)

    // null = checking, true = embeddable, false = restricted
    var isEmbeddable by remember { mutableStateOf<Boolean?>(null) }

    // Re-run the embeddability check every time internet comes back
    LaunchedEffect(videoId, isOnline) {
        if (isOnline) {
            isEmbeddable = null // Show loader while re-checking
            val result = isVideoEmbeddable(videoId)
            if (!result) {
                openInYouTube(context, videoId)
            }
            isEmbeddable = result
        } else {
            isEmbeddable = null // Lost internet — reset state
        }
    }

    when {
        !isOnline && isEmbeddable == null -> {
            // No internet — show offline message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                NoInternetView() // 👇 see below
            }
        }
        isEmbeddable == null -> {
            // Checking embeddability
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        isEmbeddable == false -> {
            // Restricted — already opened YouTube
            Box(modifier = Modifier.fillMaxSize())
        }
        else -> {
            // Play in app
            key(isOnline) { // 🔑 Forces full recomposition when internet toggles
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
                                openInYouTube(context, videoId)
                            }
                        })
                        playerView
                    }
                )
            }
        }
    }
}

// ─── No Internet UI ───────────────────────────────────────────────────────────

@Composable
fun NoInternetView() {
    androidx.compose.material3.Text(
        text = "📶 No internet connection.\nVideo will reload automatically when connected.",
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
    )
}