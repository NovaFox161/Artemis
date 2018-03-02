package me.xaanit.artemis.internal.pojo.user

import me.xaanit.artemis.entities.User
import me.xaanit.artemis.internal.Client
import me.xaanit.artemis.internal.DiscordConstant

data class UserPojo(
        val username: String,
        val id: String,
        val discriminator: String,
        val avatar: String?,
        val bot: Boolean = false
) {

    fun make(client: Client): User {
        val user = User(
                client = client,
                username = username,
                discriminator = discriminator,
                avatarUrl = if (avatar == null) DiscordConstant.USER_DEFAULT_ICON.format(discriminator.toInt() % 5) else DiscordConstant.USER_ICON.format(id, avatar, if (avatar.startsWith("a_")) "gif" else "png"),
                bot = bot,
                id = id.toLong()
        )
        client.shards[0].userCache += user.id to user
        return user
    }
}