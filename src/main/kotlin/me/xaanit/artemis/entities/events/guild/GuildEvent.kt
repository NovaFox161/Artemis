package me.xaanit.artemis.entities.events.guild

import me.xaanit.artemis.entities.Guild
import me.xaanit.artemis.entities.events.Event

open class GuildEvent(val guild: Guild) : Event(client = guild.client)