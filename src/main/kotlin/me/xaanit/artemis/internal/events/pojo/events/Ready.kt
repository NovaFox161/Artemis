package me.xaanit.artemis.internal.events.pojo.events

import me.xaanit.artemis.entities.User
import me.xaanit.artemis.internal.DiscordConstant
import me.xaanit.artemis.internal.events.pojo.Handleable

class Ready(
        val v: Int,
        val userSettings: UserSettings,
        val user: UserPojoExtended,
        val shard: Array<Int>,
        val session_id: String,
        val relationships: Array<String>,
        val private_channels: Array<String>,
        val presences: Array<String>,
        val guilds: Array<UnavaliableGuild>,
        val _trace: Array<String>
) : Handleable() {
    override fun handle() {
        val user = User(
                id = user.id.toLong(),
                client = clientObj,
                avatarUrl = DiscordConstant.USER_ICON.format(user.id, user.avatar, "png"),
                discriminator = user.discriminator,
                username = user.username,
                bot = true
        )
        clientObj.usTracked = user
        websocketObj.guildSize = guilds.size
    }

    class UnavaliableGuild(val unavailable: Boolean, val id: String)

    class UserSettings

    data class UserPojoExtended(
            val verified: Boolean,
            val mfa_enabled: Boolean,
            val email: String?,
            val bot: Boolean,
            val username: String,
            val id: String,
            val discriminator: String,
            val avatar: String?
    )
}