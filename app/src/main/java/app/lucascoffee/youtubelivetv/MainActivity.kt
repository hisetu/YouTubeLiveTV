package app.lucascoffee.youtubelivetv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import app.lucascoffee.youtubelivetv.data.CHANNELS
import app.lucascoffee.youtubelivetv.ui.theme.YoutubeLiveTVTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.getInsetsController(window, window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.systemBars())
        }

        setContent {
            YoutubeLiveTVTheme {
                YoutubeScreen(CHANNELS.firstOrNull()?.youtubeId ?: "")
            }
        }
    }
}