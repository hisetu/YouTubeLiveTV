package app.lucascoffee.youtubelivetv

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubeScreen(
    playListId: String,
    modifier: Modifier = Modifier
) {
    var player by remember<MutableState<YouTubePlayer?>> { mutableStateOf(null) }

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
                        .listType("playlist")
                        .list(playListId)
                        .build()
                )
            }
        },
        update = {
            player?.loadVideo(playListId, 0f)
        }
    )
}