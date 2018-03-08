package me.xaanit.artemis.entities

import me.xaanit.artemis.builders.ChannelEditBuilder
import me.xaanit.artemis.entities.embed.EmbedObject
import me.xaanit.artemis.internal.Client
import me.xaanit.artemis.internal.requests.DiscordRequest


abstract class Channel(
        val id: Long,
        val name: String,
        val guild: Guild?,
        val position: Int?,
        val overwrites: List<PermissionOverwrite>,
        val topic: String,
        val nsfw: Boolean,
        val `private`: Boolean,
        val parent: Long?, // TODO: Category
        val client: Client,
        val shard: Shard = guild?.shard ?: client.shards[0]
) {
    override fun toString(): String {
        return "Channel(id=$id, name='$name', position=$position, overwrites=${this.overwrites}, nsfw=$nsfw, `private`=$`private`, parent=$parent)"
    }


    abstract fun sendMessage(
            content: Any = "",
            embed: EmbedObject? = null,
            tts: Boolean = false
    ): DiscordRequest<Message>

    abstract fun edit(request: ChannelEditBuilder): DiscordRequest<Unit>

    fun edit(): ChannelEditBuilder = ChannelEditBuilder(this)
}