package me.xaanit.artemis.entities

import me.xaanit.artemis.entities.presence.Game
import me.xaanit.artemis.entities.presence.Status
import me.xaanit.artemis.internal.Client

class Member(
        private val userId: Long,
        private val name: String,
        private val discrim: String,
        private val avatar: String,
        private val cli: Client,
        private val bt: Boolean,
        val roles: List<Role>,
        val nickname: String?,
        val guild: Guild
) : User(
        id = userId, username = name, discriminator = discrim, avatarUrl = avatar, bot = bt, client = cli
) {

    val voiceState: VoiceState
        get() = guild.voiceStates[this.id] ?: VoiceState(member = this)
    val game: Game
        get() = guild.games[this.id] ?: Game()

    val status: Status
        get() {
            return Status.ONLINE
        }

    override fun toString(): String {
        return "Member(userId=$userId, name='$name', discrim='$discrim', avatar='$avatar', cli=$cli, roles=${this.roles}, nickname=$nickname, voiceState=$voiceState, game=$game)"
    }
}