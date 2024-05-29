package app.lucascoffee.youtubelivetv.data.remote.model

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class ChannelResponse(
    @SerialName("channels")
    val channels: List<Channel>
) {
    @Serializable
    data class Channel(
        @SerialName("id")
        val id: String,
        @SerialName("name")
        val name: String
    )
}