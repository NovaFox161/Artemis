package me.xaanit.artemis.entities

import com.github.salomonbrys.kotson.jsonObject
import me.xaanit.artemis.entities.embed.EmbedObject
import me.xaanit.artemis.internal.pojo.message.MessagePojo
import me.xaanit.artemis.internal.requests.DiscordRequest
import me.xaanit.artemis.internal.requests.MethodType
import me.xaanit.artemis.util.Extensions

class TextChannel(
        channelId: Long,
        channelName: String,
        channelGuild: Guild,
        channelPosition: Int?,
        channelOverwrites: Array<PermissionOverwrite>,
        isNsfw: Boolean,
        channelParent: Long? // TODO: Category
) : Channel(id = channelId, name = channelName, guild = channelGuild, position = channelPosition, overwrites = channelOverwrites, nsfw = isNsfw, private = false, parent = channelParent, client = channelGuild.client) {
    override fun sendMessage(
            content: Any,
            embed: EmbedObject?,
            tts: Boolean
    ): DiscordRequest<Message> {
        return DiscordRequest<Message>(
                url = "https://discordapp.com/api/v6/channels/%s/messages",
                method = MethodType.POST,
                client = client,
                body = jsonObject(
                        "content" to content.toString(),
                         "embed" to embed?.json(),
                        "tts" to tts
                ),
                make = {
                    val message = Extensions.gson.fromJson(it.jsonObject.toString(), MessagePojo::class.java)
                    message.shardObj = guild?.client?.shards!![((guild.id shr 22) % client.shardCount).toInt()]
                    message.make()
                },
                formatter = arrayOf(id)
        )
    }

}