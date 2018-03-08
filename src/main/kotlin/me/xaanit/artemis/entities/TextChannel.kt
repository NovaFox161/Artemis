package me.xaanit.artemis.entities

import com.github.salomonbrys.kotson.jsonObject
import me.xaanit.artemis.builders.ChannelEditBuilder
import me.xaanit.artemis.entities.embed.EmbedObject
import me.xaanit.artemis.entities.types.Mentionable
import me.xaanit.artemis.internal.Endpoints
import me.xaanit.artemis.internal.pojo.message.MessagePojo
import me.xaanit.artemis.internal.requests.DiscordRequest
import me.xaanit.artemis.internal.requests.MethodType
import me.xaanit.artemis.util.Extensions

class TextChannel(
        channelId: Long,
        channelName: String,
        channelGuild: Guild,
        channelPosition: Int?,
        channelOverwrites: List<PermissionOverwrite>,
        isNsfw: Boolean,
        channelParent: Long?, // TODO: Category
        topic: String
) : Channel(id = channelId, name = channelName, guild = channelGuild, position = channelPosition, overwrites = channelOverwrites, nsfw = isNsfw, private = false, parent = channelParent, client = channelGuild.client, topic = topic), Mentionable {
    override fun delete(): DiscordRequest<Unit> {
        return DiscordRequest<Unit>(
                url = Endpoints.DELETE_CHANNEL,
                method = MethodType.DELETE,
                client = client,
                make = {},
                formatter = arrayOf(id)
        )
    }

    override val mention: String = "<#$id>"

    override fun sendMessage(
            content: Any,
            embed: EmbedObject?,
            tts: Boolean
    ): DiscordRequest<Message> {
        return DiscordRequest<Message>(
                url = Endpoints.CREATE_MESSAGE,
                method = MethodType.POST,
                client = client,
                body = jsonObject(
                        "content" to content.toString(),
                        "embed" to embed?.json(),
                        "tts" to tts
                ),
                make = {
                    val message = Extensions.seraliseNulls.fromJson(it.jsonObject.toString(), MessagePojo::class.java)
                    message.shardObj = guild?.client?.shards!![((guild.id shr 22) % client.shardCount).toInt()]
                    message.make()!!
                },
                formatter = listOf(id)
        )
    }

    override fun edit(request: ChannelEditBuilder): DiscordRequest<Unit> {
        return DiscordRequest<Unit>(
                url = Endpoints.CHANNEL,
                method = MethodType.PATCH,
                client = client,
                body = Extensions.noNulls.toJsonTree(request).asJsonObject,
                make = {},
                formatter = listOf(id)
        )
    }

}