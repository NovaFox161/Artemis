package me.xaanit.artemis.internal.pojo.events

import com.github.salomonbrys.kotson.jsonObject
import me.xaanit.artemis.entities.Guild
import me.xaanit.artemis.entities.User
import me.xaanit.artemis.entities.events.ReadyEvent
import me.xaanit.artemis.entities.events.guild.GuildCreateEvent
import me.xaanit.artemis.entities.events.shard.ShardReadyEvent
import me.xaanit.artemis.internal.DiscordConstant
import me.xaanit.artemis.internal.pojo.EmojiPojo
import me.xaanit.artemis.internal.pojo.Handleable
import me.xaanit.artemis.internal.pojo.RolePojo
import me.xaanit.artemis.internal.pojo.VoiceStatePojo
import me.xaanit.artemis.internal.pojo.channels.ChannelPojo
import me.xaanit.artemis.internal.pojo.game.GamePojo
import me.xaanit.artemis.internal.pojo.user.MemberPojo
import me.xaanit.artemis.util.Extensions.send

data class GuildCreatePojo(
        val voice_states: List<VoiceStatePojo>,
        val verification_level: Int,
        val unavaliable: Boolean,
        val system_channel_id: String?,
        val splash: Boolean,
        val region: String,
        val owner_id: String,
        val name: String,
        val mfa_level: Int,
        val members: List<MemberPojo>,
        val presences: List<GamePojo>,
        val member_count: Int,
        val large: Boolean,
        val joined_at: String,
        val id: String,
        val icon: String,
        val features: List<String>,
        val explicit_content_filter: Int,
        val emojis: List<EmojiPojo>,
        val default_message_notifications: Int,
        val channels: List<ChannelPojo>,
        val application_id: String?,
        val afk_timeout: Int,
        val afk_channel_id: String?,
        val roles: List<RolePojo>
) : Handleable() {

    override fun handle() {
        var createdUsers: Array<User> = arrayOf()
        val guild = Guild(
                id = id.toLong(),
                name = name,
                avatarUrl = DiscordConstant.GUILD_ICON.format(id, icon),
                afkChannel = afk_channel_id?.toLong(),
                afkTimeout = afk_timeout,
                channelData = channels.toTypedArray(),
                roleData = roles.toTypedArray(),
                memberData = members.toTypedArray(),
                explicitContentFilterLevel = explicit_content_filter,
                large = large,
                client = clientObj,
                systemChannelId = system_channel_id?.toLong() ?: 0,
                voiceRegion = region,
                welcomeMessagesEnabled = default_message_notifications == 1,
                ownerId = owner_id.toLong()
        )


        shardObj.guildCache += guild.id to guild

        if (members.size < member_count) {
            websocketObj.send(
                    jsonObject(
                            "op" to 8,
                            "d" to jsonObject(
                                    "guild_id" to id,
                                    "query" to "",
                                    "limit" to 0
                            ))
            )
        }

        guild.members.forEach {
            createdUsers += User(
                    id = it.id,
                    avatarUrl = it.avatarUrl,
                    client = clientObj,
                    discriminator = it.discriminator,
                    username = it.username,
                    bot = it.bot
            )
        }
        createdUsers.forEach { shardObj.userCache += Pair(it.id, it) }
        clientObj.dispatcher.dispatch(GuildCreateEvent(guild))

        if (websocketObj.counter.incrementAndGet() >= websocketObj.guildSize && websocketObj.guildSize != -1) {
            clientObj.dispatcher.dispatch(ShardReadyEvent(shardObj))
            if (websocketObj.manager.counter.incrementAndGet() == clientObj.shardCount) {
                clientObj.dispatcher.dispatch(ReadyEvent(clientObj))
            }
        }
    }


}