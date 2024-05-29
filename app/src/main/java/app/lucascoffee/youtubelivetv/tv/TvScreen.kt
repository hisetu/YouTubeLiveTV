package app.lucascoffee.youtubelivetv.tv

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import app.lucascoffee.youtubelivetv.MainViewModel
import app.lucascoffee.youtubelivetv.YoutubeScreen
import timber.log.Timber

@Composable
@OptIn(ExperimentalTvMaterial3Api::class)
fun TvScreen(viewModel: MainViewModel) {
    var index by remember { mutableIntStateOf(0) }
    val state by viewModel.uiState.collectAsState()

    Box(modifier = Modifier
        .clickable { }
        .onKeyEvent {
            Timber.d(it.toString())
            if (it.type == KeyEventType.KeyUp) {
                when (it.key) {
                    Key.DirectionUp -> {
                        if (index < state.channels.lastIndex)
                            index += 1
                        else
                            index = 0
                        true
                    }

                    Key.DirectionDown -> {
                        if (index > 0)
                            index -= 1
                        else
                            index = state.channels.lastIndex
                        true
                    }

                    else -> false
                }
            } else
                false
        }) {
        YoutubeScreen(
            youtubeId = state.channels.getOrNull(index)?.youtubeId ?: "",
            modifier = Modifier
                .background(Color.Red)
        )

        Text(
            text = index.toString(),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-30).dp),
            style = TextStyle(fontSize = 50.sp, color = Color.Red)
        )
    }
}