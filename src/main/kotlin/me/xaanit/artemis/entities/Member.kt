package me.xaanit.artemis.entities

import me.xaanit.artemis.internal.Client

class Member(
        private val user_id: Long,
        private val name: String,
        private val discrim: String,
        private val avatar: String,
        private val cli: Client
) : User(
        id = user_id, username = name, discriminator = discrim, avatarUrl = avatar, client = cli
) {
}