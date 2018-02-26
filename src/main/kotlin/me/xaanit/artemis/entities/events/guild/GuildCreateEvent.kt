package me.xaanit.artemis.entities.events.guild

import me.xaanit.artemis.entities.Guild

class GuildCreateEvent(private val gld: Guild) : GuildEvent(guild = gld)