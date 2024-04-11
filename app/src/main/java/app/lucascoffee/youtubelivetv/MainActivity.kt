package app.lucascoffee.youtubelivetv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import app.lucascoffee.youtubelivetv.data.CHANNELS
import app.lucascoffee.youtubelivetv.ui.theme.YoutubeLiveTVTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.getInsetsController(window, window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.systemBars())
        }

        setContent {
            YoutubeLiveTVTheme {
                var index by remember { mutableIntStateOf(0) }

                Box {
                    YoutubeScreen(
                        youtubeId = CHANNELS.getOrNull(index)?.youtubeId ?: "",
                        modifier = Modifier.background(Color.Red)
                    )

                    Text(
                        text = index.toString(),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = (-30).dp),
                        style = TextStyle(fontSize = 50.sp, color = Color.Red)
                    )
                    Row(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(onClick = { }, modifier = Modifier.clickable {
                            if (index > 0)
                                index -= 1
                            else
                                index = CHANNELS.lastIndex
                        }) {
                            Text("上一台")
                        }
                        Button(onClick = { }, modifier = Modifier.clickable {
                            if (index < CHANNELS.lastIndex)
                                index += 1
                            else
                                index = 0
                        }) {
                            Text("下一台")
                        }
                    }
                }
            }
        }
    }
}