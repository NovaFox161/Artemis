package me.xaanit.artemis.entities

import me.xaanit.artemis.entities.embed.EmbedObject
import me.xaanit.artemis.internal.requests.DiscordRequest

class VoiceChannel(
        private val channelId: Long,
        private val channelName: String,
        private val channelGuild: Guild,
        private val channelPosition: Int?,
        private val channelOverwrites: Array<PermissionOverwrite>,
        private val channelParent: Long? // TODO: Category
) : Channel(id = channelId, name = channelName, guild = channelGuild, position = channelPosition, overwrites = channelOverwrites, nsfw = false, private = false, parent = channelParent, client = channelGuild.client) {
    override fun sendMessage(
            content: Any,
            embed: EmbedObject?,
            tts: Boolean
    ): DiscordRequest<Message> {
        throw UnsupportedOperationException("Can't send messages in a voice channel")
    }
}