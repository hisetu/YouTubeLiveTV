package app.lucascoffee.youtubelivetv.tv

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.lifecycle.Lifecycle
import androidx.tv.material3.Text
import app.lucascoffee.youtubelivetv.ComposableLifecycle
import app.lucascoffee.youtubelivetv.MainUiState
import app.lucascoffee.youtubelivetv.MainViewModel
import app.lucascoffee.youtubelivetv.YoutubeScreen
import kotlinx.coroutines.delay
import timber.log.Timber

@Composable
fun TvScreen(viewModel: MainViewModel) {
    var index by remember { mutableIntStateOf(0) }
    val state by viewModel.uiState.collectAsState()

    ComposableLifecycle { source, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            viewModel.loadChannels()
        }
    }

    TvScreenContent(
        onDirectionUp = {
            if (index < state.channels.lastIndex)
                index += 1
            else
                index = 0
            true
        },
        onDirectionDown = {
            if (index > 0)
                index -= 1
            else
                index = state.channels.lastIndex
            true
        },
        state = state,
        index = index
    )
}

@Composable
private fun TvScreenContent(
    onDirectionUp: () -> Boolean,
    onDirectionDown: () -> Boolean,
    state: MainUiState,
    index: Int
) {
    var showInfo by remember { mutableStateOf(true) }

    LaunchedEffect(index) {
        showInfo = true
        delay(5_000)
        showInfo = false
    }

    Box(modifier = Modifier
        .clickable { }
        .onKeyEvent {
            Timber.d(it.toString())
            if (it.type == KeyEventType.KeyUp) {
                when (it.key) {
                    Key.DirectionUp -> onDirectionUp()

                    Key.DirectionDown -> onDirectionDown()

                    else -> false
                }
            } else
                false
        }) {
        val channel = state.channels.getOrNull(index)

        YoutubeScreen(
            youtubeId = channel?.youtubeId ?: "",
            modifier = Modifier
                .background(Color.Red)
        )

        if (showInfo)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray.copy(alpha = 0.7f))
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = channel?.name ?: "",
                    style = TextStyle(fontSize = 50.sp, color = Color.Black)
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = index.toString(),
                    style = TextStyle(fontSize = 50.sp, color = Color.Black)
                )
            }
    }
}