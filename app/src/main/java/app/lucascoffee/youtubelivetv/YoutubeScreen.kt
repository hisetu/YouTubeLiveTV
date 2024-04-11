package app.lucascoffee.youtubelivetv

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import timber.log.Timber

@Composable
fun YoutubeScreen(
    youtubeId: String,
    modifier: Modifier = Modifier
) {
    Timber.d(youtubeId)
    var player by remember<MutableState<YouTubePlayer?>> { mutableStateOf(null) }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(Unit) {
        onDispose { player = null }
    }

    AndroidView(
        factory = {
            YouTubePlayerView(it).apply {
                enableAutomaticInitialization = false
                initialize(
                    youTubePlayerListener = object : AbstractYouTubePlayerListener(),
                        YouTubePlayerListener {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            player = youTubePlayer
                        }
                    },
                    handleNetworkEvents = true,
                    playerOptions = IFramePlayerOptions.Builder()
                        .controls(0)
                        .rel(0)
                        .ivLoadPolicy(1)
                        .ccLoadPolicy(1)
                        .build()
                )
                lifecycle.addObserver(this)
            }
        },
        update = {
            player?.loadVideo(youtubeId, 0f)
        },
        modifier = modifier
    )
}