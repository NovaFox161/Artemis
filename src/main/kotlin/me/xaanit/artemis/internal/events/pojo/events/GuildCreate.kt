package me.xaanit.artemis.internal.events.pojo.events

import me.xaanit.artemis.entities.Guild
import me.xaanit.artemis.entities.User
import me.xaanit.artemis.internal.DiscordConstant
import me.xaanit.artemis.internal.events.pojo.Emoji
import me.xaanit.artemis.internal.events.pojo.Handleable
import me.xaanit.artemis.internal.events.pojo.VoiceState
import me.xaanit.artemis.internal.events.pojo.channels.Channel
import me.xaanit.artemis.internal.events.pojo.game.Game
import me.xaanit.artemis.internal.events.pojo.user.Member

data class GuildCreate(
        val voice_states: List<VoiceState>,
        val verification_level: Int,
        val unavaliable: Boolean,
        val system_channel_id: String,
        val splash: Boolean,
        val region: String,
        val owner_id: String,
        val name: String,
        val mfa_level: Int,
        val members: List<Member>,
        val presences: List<Game>,
        val member_count: Int,
        val large: Boolean,
        val joined_at: String,
        val id: String,
        val icon: String,
        val features: List<String>,
        val explicit_content_filter: Int,
        val emojis: List<Emoji>,
        val default_message_notifications: Int,
        val channels: List<Channel>,
        val application_id: String?,
        val afk_timeout: Int,
        val afk_channel_id: String?
) : Handleable() {

    override fun handle() {
        var createdUsers: Array<User> = arrayOf()
        var createdMembers: Array<me.xaanit.artemis.entities.Member> = arrayOf()
        var createdChannels: Array<Channel>
        val guild = Guild(
            id = id.toLong(),
                name = name,
                avatarUrl = DiscordConstant.GUILD_ICON.format(id, icon),
                afkChannel = afk_channel_id?.toLong(),
                afkTimeout = afk_timeout,
              //  channels = cratedChannels
        )
        members.forEach {
           createdMembers += me.xaanit.artemis.entities.Member(

            )
        }

        createdMembers.forEach {
            createdUsers += User(
                    id = it.id,
                    avatarUrl = it.avatarUrl,
                    client = client,
                    discriminator = it.discriminator,
                    username = it.username
            )
        }
    }
}