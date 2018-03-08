package me.xaanit.artemis.entities

import me.xaanit.artemis.builders.ChannelEditBuilder
import me.xaanit.artemis.entities.embed.EmbedObject
import me.xaanit.artemis.internal.requests.DiscordRequest
import me.xaanit.artemis.internal.requests.MethodType
import me.xaanit.artemis.util.Extensions

class VoiceChannel(
        private val channelId: Long,
        private val channelName: String,
        private val channelGuild: Guild,
        private val channelPosition: Int?,
        private val channelOverwrites: List<PermissionOverwrite>,
        private val channelParent: Long?, // TODO: Category
        val bitrate: Int,
        val userLimit: Int
) : Channel(id = channelId, name = channelName, guild = channelGuild, position = channelPosition, overwrites = channelOverwrites, nsfw = false, private = false, parent = channelParent, client = channelGuild.client, topic = "") {

    override fun sendMessage(content: Any, embed: EmbedObject?, tts: Boolean): DiscordRequest<Message> {
        throw UnsupportedOperationException()
    }

    override fun edit(request: ChannelEditBuilder): DiscordRequest<Unit> {
        return DiscordRequest<Unit>(
                url = "https://discordapp.com/api/v6/channels/%s",
                method = MethodType.PATCH,
                client = client,
                body = Extensions.noNulls.toJsonTree(request).asJsonObject,
                formatter = arrayOf(id),
                make = {}
        )
    }

}