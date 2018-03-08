package me.xaanit.artemis.internal.pojo.events

import me.xaanit.artemis.internal.pojo.Handleable
import me.xaanit.artemis.internal.pojo.game.GamePojo
import me.xaanit.artemis.internal.pojo.game.UserIdHolderPojo

class PresenceUpdatePojo(
        val user: UserIdHolderPojo,
        val status: String,
        val roles: List<String>,
        val nick: String?,
        val guild_id: String,
        val game: GamePojo
) : Handleable() {
    override fun handle() {
        val guild = clientObj.getGuildById(guild_id.toLong()) ?: return
      //  guild.presences +=
    }
}