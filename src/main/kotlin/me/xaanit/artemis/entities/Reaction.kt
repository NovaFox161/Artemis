package me.xaanit.artemis.entities

import me.xaanit.artemis.internal.Client

class Reaction(
        val message: Message,
        val client: Client = message.client,
        val name: String,
        val id: Long?,
        val animated: Boolean,
        val managed: Boolean,
        val roles: List<Role>,
        val requiresColons: Boolean,
        val count: Int = 0
)