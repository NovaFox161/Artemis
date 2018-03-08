package me.xaanit.artemis.internal.pojo.events

import me.xaanit.artemis.entities.User
import me.xaanit.artemis.internal.DiscordConstant
import me.xaanit.artemis.internal.pojo.Handleable

class ReadyPojo(
        val v: Int,
        val userSettings: UserSettings,
        val user: UserPojoExtended,
        val shard: List<Int>,
        val session_id: String,
        val relationships: List<String>,
        val private_channels: List<String>,
        val presences: List<String>,
        val guilds: List<UnavaliableGuild>,
        val _trace: List<String>
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