package me.xaanit.artemis.entities

import me.xaanit.artemis.entities.types.Mentionable
import me.xaanit.artemis.internal.Client

open class User(
        val id: Long,
        val username: String,
        val discriminator: String,
        val avatarUrl: String,
        val client: Client
) : Mentionable {
    override fun mention(): String = "<@!$id>"

    fun getMember(guild: Guild): Member? = guild.getMember(this)
}