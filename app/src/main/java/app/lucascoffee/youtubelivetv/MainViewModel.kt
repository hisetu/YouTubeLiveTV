package app.lucascoffee.youtubelivetv

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.lucascoffee.youtubelivetv.data.model.Channel
import app.lucascoffee.youtubelivetv.data.remote.model.ChannelResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json
import timber.log.Timber

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val client by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = false
                })
            }
        }
    }

    init {
        loadChannels()
    }

    fun loadChannels() {
        flow { emit(client.get("https://yttv.lucascoffee.app/channels").body() as ChannelResponse) }
            .onEach { res ->
                _uiState.update { state ->
                    state.copy(channels = res.channels
                        .map { Channel(it.name, it.id) })
                }
            }
            .catch {
                Timber.e(it.message)
            }
            .launchIn(viewModelScope)
    }
}

data class MainUiState(val channels: List<Channel> = listOf())