package me.xaanit.artemis.entities

import me.xaanit.artemis.entities.presence.Game
import me.xaanit.artemis.entities.presence.Status
import me.xaanit.artemis.internal.Client

class Member(
        private val user_id: Long,
        private val name: String,
        private val discrim: String,
        private val avatar: String,
        private val cli: Client,
        val roles: List<Role>,
        val nickname: String?,
        val voiceState: VoiceState,
        val game: Game,
        val status: Status,
        val guild: Guild
) : User(
        id = user_id, username = name, discriminator = discrim, avatarUrl = avatar, client = cli
)